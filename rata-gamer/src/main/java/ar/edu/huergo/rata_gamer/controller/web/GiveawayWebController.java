package ar.edu.huergo.rata_gamer.controller.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.huergo.rata_gamer.dto.publicaciones.GiveawayDTO;
import ar.edu.huergo.rata_gamer.service.publicaciones.GiveawayService;

@Controller
@RequestMapping("/web")
public class GiveawayWebController {

    private final GiveawayService giveawayService;

    public GiveawayWebController(GiveawayService giveawayService) {
        this.giveawayService = giveawayService;
    }

    @GetMapping("/giveaways")
    public String giveaways(@RequestParam(value = "q", required = false) String q,
                            Model model) {

        List<GiveawayDTO> giveaways = (q == null || q.isBlank())
                ? giveawayService.obtenerGiveaways()
                : giveawayService.buscarPorTitulo(q);

        model.addAttribute("giveaways", giveaways);
        model.addAttribute("q", q == null ? "" : q);

        return "index";
    }
}
