/* =========================================================
   HEADER.JS – INIT HEADER BEHAVIOR (NO FETCH)
   ========================================================= */
document.addEventListener("DOMContentLoaded", () => {
    setupUserMenu();
    highlightActiveLink();
    applyDropdownModeOnResize();
});

/* =========================================================
   AUTH UTILS
   ========================================================= */
function getAuth() {
    if (localStorage.getItem("loggedIn") === "true") {
        return { store: localStorage, username: localStorage.getItem("username") };
    }
    if (sessionStorage.getItem("loggedIn") === "true") {
        return { store: sessionStorage, username: sessionStorage.getItem("username") };
    }
    return null;
}

/* =========================================================
   SETUP USER MENU + LOGIN FORM
   ========================================================= */
function setupUserMenu() {
    const loginBtn = document.getElementById("login-btn");
    const userBox  = document.getElementById("user-menu");
    const auth = getAuth();

    if (auth?.username) {
        loginBtn?.classList.add("d-none");
        userBox?.classList.remove("d-none");

        const nameSpan = document.querySelector("#userMenu .user-name");
        if (nameSpan) nameSpan.textContent = auth.username;

        const avatar = document.querySelector("#userMenu .user-avatar");
        if (avatar) avatar.textContent = auth.username[0].toUpperCase();

        const bellBadge = document.getElementById("bell-badge");
        const chatBadge = document.getElementById("chat-badge");
        if (bellBadge) bellBadge.textContent = "3";
        if (chatBadge) chatBadge.textContent = "2";
    } else {
        userBox?.classList.add("d-none");
        loginBtn?.classList.remove("d-none");
    }

    // Đăng xuất → điều hướng về route Thymeleaf
    document.getElementById("logout-btn")?.addEventListener("click", (e) => {
        e.preventDefault();
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "/user/login"; // dùng route thay vì .html
    });

    // Xử lý form login nếu đang ở trang login (do bạn render server-side)
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const u = document.getElementById("username")?.value.trim();
            const p = document.getElementById("password")?.value.trim();
            const remember = document.getElementById("rememberMe")?.checked;
            const err = document.getElementById("error-msg");

            const demoUser = "phuoc";
            const demoPass = "123456";

            if (u === demoUser && p === demoPass) {
                const store = remember ? localStorage : sessionStorage;
                store.setItem("loggedIn", "true");
                store.setItem("username", u);
                window.location.href = "/user/home"; // quay lại trang chủ (route)
            } else {
                if (err) {
                    err.textContent = "❌ Tên đăng nhập hoặc mật khẩu không đúng!";
                    err.classList.remove("d-none");
                } else {
                    alert("Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }
        });
    }
}

/* =========================================================
   HIGHLIGHT ACTIVE NAV LINK
   ========================================================= */
function highlightActiveLink() {
    const links = document.querySelectorAll(".navbar-nav .nav-link, .dropdown-item");
    if (!links.length) return;

    // Lấy segment cuối của URL: /user/lookup -> "lookup"
    const path = window.location.pathname.replace(/\/+$/, "");
    const seg = path.split("/").filter(Boolean).pop() || "home"; // mặc định home

    links.forEach(link => {
        const href = link.getAttribute("href") || "";
        // So khớp theo segment cuối của href
        const hrefSeg = href.split("/").filter(Boolean).pop() || "";
        const hrefClean = hrefSeg.replace(/\.html$/i, "");
        const isHomeLink = href === "/" || /\/user(\/(home|index))?$/.test(href);

        if ((hrefClean && hrefClean === seg) || (isHomeLink && (seg === "home" || seg === "index"))) {
            link.classList.add("active");
            const parentDropdown = link.closest(".dropdown");
            if (parentDropdown) parentDropdown.querySelector(".nav-link")?.classList.add("active");
        }
    });
}

/* =========================================================
   HEADER NAV — Dropdown: Desktop=Hover, Mobile=Click (Bootstrap)
   ========================================================= */
const DESKTOP_MIN = 992;

function applyDropdownMode() {
    const toggles = document.querySelectorAll('.navbar .dropdown-toggle');
    const isMobile = window.innerWidth < DESKTOP_MIN;

    toggles.forEach(t => {
        if (isMobile) {
            t.setAttribute('data-bs-toggle', 'dropdown');
            t.setAttribute('data-bs-auto-close', 'outside');
        } else {
            t.removeAttribute('data-bs-toggle');
            t.removeAttribute('data-bs-auto-close');

            const menu = t.parentElement && t.parentElement.querySelector('.dropdown-menu');
            if (menu && menu.classList.contains('show') && window.bootstrap) {
                const inst = bootstrap.Dropdown.getInstance(t) || new bootstrap.Dropdown(t);
                inst.hide();
                t.setAttribute('aria-expanded', 'false');
            }
        }
    });
}

function applyDropdownModeOnResize() {
    applyDropdownMode();
    let tmr;
    window.addEventListener('resize', () => {
        clearTimeout(tmr);
        tmr = setTimeout(applyDropdownMode, 150);
    });

    // Không nhảy trang khi desktop và href="#" (chỉ để hover)
    document.addEventListener('click', (e) => {
        const link = e.target.closest('.navbar .dropdown-toggle');
        if (!link) return;
        const isMobile = window.innerWidth < DESKTOP_MIN;
        if (!isMobile && link.getAttribute('href') === '#') e.preventDefault();
    });

    // Mobile: click item thì đóng dropdown cha
    document.addEventListener('click', (e) => {
        const item = e.target.closest('.dropdown-menu .dropdown-item');
        if (!item) return;
        const isMobile = window.innerWidth < DESKTOP_MIN;
        if (!isMobile) return;
        const parentDropdown = item.closest('.nav-item.dropdown');
        const toggle = parentDropdown?.querySelector('.dropdown-toggle');
        if (toggle && window.bootstrap) {
            const inst = bootstrap.Dropdown.getInstance(toggle) || new bootstrap.Dropdown(toggle);
            inst.hide();
            toggle.setAttribute('aria-expanded', 'false');
        }
    });
}
