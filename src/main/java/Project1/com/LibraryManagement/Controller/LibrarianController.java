package Project1.com.LibraryManagement.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    // Trang chính thư viện
    @GetMapping( "/dashboard")
    public String libraryDashboard() {
        return "librarian/dashboard";
    }

    // Tùy chọn: map "/" vào thư viện (nếu bạn muốn)
    @GetMapping("/")
    public String home() {
        return "librarian/dashboard";
    }
    // Độc giả
    @GetMapping("/readers")
    public String reader() {
        return "librarian/readers";
    }

    // Sách
    @GetMapping("/books")
    public String books() {
        return "librarian/books";
    }

    // Lưu thông / Mượn - Trả
    @GetMapping("/circulation")
    public String circulation() {
        return "librarian/circulation";
    }

    // Thu phí phạt
    @GetMapping("/fees")
    public String fees() {
        return "librarian/fees";
    }

    // Reports
    @GetMapping("/fee-reports")
    public String feeReports() {
        return "librarian/fee-reports";
    }

    @GetMapping("/member-reports")
    public String memberReports() {
        return "librarian/member-reports";
    }

    @GetMapping("/circulation-reports")
    public String circulationReports() {
        return "librarian/circulation-reports";
    }

    @GetMapping("/overdue-reports")
    public String overdueReports() {
        return "librarian/overdue-reports";
    }
}
