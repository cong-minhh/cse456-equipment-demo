package vn.edu.eiu.testlab.fecse456studentiddemo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.User;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.UserService;

@Controller
public class AuthController {
    // Tiêm qua field
    @Autowired
    UserService userServ;

    // Hàm xử lý khi người dùng bấm login
    @PostMapping("/auth")
    public String doLogin(@ModelAttribute User user, HttpSession ses, RedirectAttributes redAttr) {
        User loginUser = userServ.findByUsername(user.getUsername());
        // So username với db, nếu ko có thì quay lại login
        if (loginUser == null) {
            redAttr.addFlashAttribute("errLogin", "Username or password incorrect");
            redAttr.addFlashAttribute("username", user.getUsername());
            return "redirect:/login";
            // kèm lỗi sai username
        }
        // Xuống được đây là đúng username
        if (!loginUser.getPassword().equals(user.getPassword())) {
            redAttr.addFlashAttribute("username", user.getUsername());
            redAttr.addFlashAttribute("errLogin", "Username or password incorrect");
            return "redirect:/login";
            // Kèm lỗi sai pass
        }
        // Nếu login thành công thì hiển thị trang equipments.
        // Lưu user vào session để xử lý phân quyền
        // session dùng chung cho toàn bộ app
        ses.setAttribute("user", loginUser);
        return "redirect:/equipments"; // Redirect to equipment list after login
    }

    // Xử lý khi click vào link/nút logout
    @GetMapping("/logout")
    public String doLogout(HttpSession ses) {
        // ses.setAttribute("user",null);
        // ses.removeAttribute("user");
        ses.invalidate();
        return "redirect:/login";
    }

    // Hiển thị trang login
    @GetMapping({ "/", "/login" })
    public String showLoginPage() {
        return "login"; // login.html
    }
}
