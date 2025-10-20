package ar.edu.huergo.rata_gamer.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminWebController {

    @GetMapping("/web/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // templates/admin/dashboard.html
    }
}
