package ar.edu.huergo.rata_gamer.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.rata_gamer.entity.security.Rol;
import ar.edu.huergo.rata_gamer.entity.security.Usuario;
import ar.edu.huergo.rata_gamer.util.PasswordValidator;
import ar.edu.huergo.rata_gamer.repository.security.RolRepository;
import ar.edu.huergo.rata_gamer.repository.security.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            Rol admin = rolRepository.findByNombre("ADMIN").orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol cliente = rolRepository.findByNombre("CLIENTE").orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

            if (usuarioRepository.findByUsername("admin@rata_gamer.edu.ar").isEmpty()) {
                String adminPassword = "AdminSuperSegura@123";
                PasswordValidator.validate(adminPassword);
                Usuario u = new Usuario("admin@rata_gamer.edu.ar", encoder.encode(adminPassword));
                u.setRoles(Set.of(admin));
                usuarioRepository.save(u);
            }

            if (usuarioRepository.findByUsername("cliente@rata_gamer.edu.ar").isEmpty()) {
                String clientePassword = "ClienteSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Usuario u = new Usuario("cliente@rata_gamer.edu.ar", encoder.encode(clientePassword));
                u.setRoles(Set.of(cliente));
                usuarioRepository.save(u);
            }
        };
    }
}
