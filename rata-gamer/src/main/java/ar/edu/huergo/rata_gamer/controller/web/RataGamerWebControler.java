package ar.edu.huergo.rata_gamer.controller.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.huergo.rata_gamer.entity.security.Usuario;
import ar.edu.huergo.rata_gamer.service.publicaciones.JuegoApiService;
import ar.edu.huergo.rata_gamer.service.security.UsuarioService;
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class RataGamerWebControler {

    private final UsuarioService usuarioService;
    
    private final JuegoApiService juegoApiService;

    // Mostrar formulario de registro
    @GetMapping("/web/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; 
    }

    // Procesar registro del usuario 
    @PostMapping("/web/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   RedirectAttributes ra,
                                   Model model) {

        if (result.hasErrors()) {
            return "registro";
        }
        try {
            // password y verificación de UsuarioService
            usuarioService.registrar(usuario, usuario.getPassword(), usuario.getPassword());
            ra.addFlashAttribute("success", "Usuario registrado exitosamente. Ahora podés iniciar sesión.");
            return "redirect:/web/login"; // Redirije a web/login si se registro exitosamente
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo registrar: " + e.getMessage());
            return "registro"; // si hubo un error devulve templates/registro.html
        }
    }

    // Mostrar login
    @GetMapping("/web/login")
        public String mostrarLogin() {
        return "login"; // templates/login.html
    }

    // Mostrar sobre nostros
    @GetMapping("/web/nosotros")
        public String nosotros() {
        return "nosotros"; // templates/nosotros.html
    }

    
    @GetMapping("/web/miCuenta")
        public String miCuenta(Model model, Principal principal) {
        // Llama al usuario logueado
        String email = principal.getName(); // usa el username como email

        // pasa el email a la vista
        model.addAttribute("email", email);

            return "miCuenta"; // templates/miCuenta.html
    }

    // Página de inicio web
    @GetMapping("/")
    public String index(@RequestParam(required = false) String buscar, Model model) {

        if (buscar != null && !buscar.isBlank()) {
            var juegos = juegoApiService.buscar(buscar);
            model.addAttribute("juegos", juegos);
            model.addAttribute("buscado", buscar);
        }

        return "index";
    }
}