package ar.edu.huergo.rata_gamer.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RataGamerWebControler {

    @GetMapping({"/", "/web", "/web/"})
    public String index() {
        // Busca src/main/resources/templates/index.html
        return "index";
    }

    @GetMapping("/web/registro")
    public String registro() {
         return "registro"; 
    }

    @GetMapping("/web/login")
    public String login() {
         return "login"; 
    }

    @GetMapping("/web/acerca")
    public String acerca() { return "acerca"; }
}

