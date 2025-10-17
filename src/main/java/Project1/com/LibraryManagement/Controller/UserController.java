package Project1.com.LibraryManagement.Controller;

import Project1.com.LibraryManagement.Entity.Roles;
import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UserRepos;
import Project1.com.LibraryManagement.Service.UserService;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserRepos usersRepos;
    @Autowired
    public UserService userService;
    @Autowired
    public PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("home")
    public String Home(){
        return "user/home";
    }
    @GetMapping("/announcement")
    public String annoucement(){
        return "user/announcement";
    }
    @GetMapping("/documents")
    public String documents(){
        return "user/documents";
    }
    @GetMapping("/feedback")
    public String feedback(){
        return "user/feedback";
    }
    @GetMapping("/introduce")
    public String introduce(){
        return "user/introduce";
    }
    @GetMapping("/lookup")
    public String lookup(){
        return "user/lookup";
    }
    @GetMapping("/support")
    public String support(){
        return "user/support";
    }

    @GetMapping("/login")
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/user/home";  //return home page when login success
        }
        return "user/login"; //when fail return login page
    }



    @GetMapping("/register")
    public String signup(){
        return "user/register";
    }

    @PostMapping("/saveUsers")
    public String saveUsers(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("dayofBirth") Integer dayofBirth,
            @RequestParam("monthofBirth") Integer monthofBirth,
            @RequestParam("yearofBirth") Integer yearofBirth,
            @RequestParam("address") String address,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,

            RedirectAttributes redirectAttributes,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("ErrorPass", "Passwords do not match");
            return "user/register";
        }
        if (!phoneNumber.matches("\\d{10}")) {
            model.addAttribute("ErrorPhone", "Phone number must be 10 digits");
            return "user/register";
        }

        try {
            if (usersRepos.existsByEmail(email)) {
                model.addAttribute("ErrorExists", "This Account Already Exists");
                return "user/register";
            }
            LocalDate dateOfBirth;
            try {
                dateOfBirth = LocalDate.of(yearofBirth, monthofBirth, dayofBirth);
            } catch (DateTimeException ex) {
                model.addAttribute("ErrorDateTime", "Invalid Date Of Birth");
                return "user/register";
            }
            Users users = new Users();
            users.setEmail(email);
            users.setPassword(passwordEncoder.encode(password));
            users.setFullName(fullName);
            users.setPhoneNumber(phoneNumber);
            users.setAddress(address);
            users.setDateOfBirth(dateOfBirth);
            users.setRoles(Roles.USERS);
            Users saved = userService.saveUser(users);

            if (saved == null || saved.getId() == null) {
                model.addAttribute("ErrorLogin", "Failed to save user. Try again.");
                return "user/register";
            }
            redirectAttributes.addFlashAttribute("Success", "Create Account Success! Please log in.");
            return "redirect:/user/login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ErrorLogin", "Error creating account: " + e.getMessage());
            return "user/register";
        }

    }


}
