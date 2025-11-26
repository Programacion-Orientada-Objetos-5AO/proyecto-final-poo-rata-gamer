package ar.edu.huergo.rata_gamer.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.rata_gamer.repository.security.UsuarioRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
            // Habilitar CSRF solo para rutas web, deshabilitar para API
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/**", "/juegos/**", "/plataformas/**", "/publicaciones/**", "/ofertas/**", "/precios-historicos/**", "/prestamos-libros/**")
            )

            // Sesión requerida solo si hace falta (web); API trabajará sin sesión (stateless) por JWT
            .sessionManagement(sm -> sm
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            )

            .authorizeHttpRequests(auth -> auth
                // Estáticos y H2
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                //  ZONA ADMIN (protegida)
                .requestMatchers("/web/admin/**").hasRole("ADMIN")

                //  Vistas públicas
                .requestMatchers("/", "/web", "/web/", "/web/nosotros", "/web/login", "/web/registro", "/web/miCuenta").permitAll()

                // API pública mínima
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()

                // API protegida
                .requestMatchers(HttpMethod.POST,   "/api/pedidos").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET,    "/api/pedidos/reporte").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/platos/**").hasAnyRole("ADMIN", "CLIENTE")
                .requestMatchers(                   "/api/ingredientes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/platos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/platos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/platos/**").hasRole("ADMIN")

                .requestMatchers("/prestamos-libros/**").permitAll()
                // Permitir todas las rutas de publicaciones para pruebas
                .requestMatchers("/publicaciones/**").permitAll()
                .requestMatchers("/juegos/**").permitAll()
                .requestMatchers("/plataformas/**").permitAll()
                .requestMatchers("/ofertas/**").permitAll()
                .requestMatchers("/precios-historicos/**").permitAll()

                .anyRequest().authenticated()
            )

            // Login web
            .formLogin(form -> form
                .loginPage("/web/login")
                .loginProcessingUrl("/web/login")
                .successHandler((request, response, authentication) -> {
                    var isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                    if (isAdmin) {
                        response.sendRedirect("/web/admin/dashboard");
                    } else {
                        response.sendRedirect("/web/"); // clientes u otros roles
                    }
                })
                .failureUrl("/web/login?error")
                .permitAll()
            )


            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            // Manejo de errores JSON para API
           .exceptionHandling(
                        exceptions -> exceptions.accessDeniedHandler(accessDeniedHandler())
                                .authenticationEntryPoint(authenticationEntryPoint()))

            // H2 (frames)
            .headers(h -> h.frameOptions(f -> f.disable()));

        // El filtro JWT ya ignora rutas NO /api/** (early-return)
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(403);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            String json = new ObjectMapper().writeValueAsString(
                java.util.Map.of(
                    "type", "https://http.dev/problems/access-denied",
                    "title", "Acceso denegado",
                    "status", 403,
                    "detail", "No tienes permisos para acceder a este recurso"
                )
            );
            response.getWriter().write(json);
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            String json = new ObjectMapper().writeValueAsString(
                java.util.Map.of(
                    "type", "https://http.dev/problems/unauthorized",
                    "title", "No autorizado",
                    "status", 401,
                    "detail", "Credenciales inválidas o faltantes"
                )
            );
            response.getWriter().write(json);
        };
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> usuarioRepository.findByUsername(username)
            .map(usuario -> org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(r -> r.getNombre()).toArray(String[]::new))
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}