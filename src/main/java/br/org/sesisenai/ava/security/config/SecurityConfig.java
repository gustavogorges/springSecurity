package br.org.sesisenai.ava.security.config;
//import br.org.sesisenai.ava.security.auths.IsEnrollmentOfUser;
import br.org.sesisenai.ava.security.auths.IsOnlyUserCourse;
import br.org.sesisenai.ava.security.auths.IsUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private final IsUser isUser;
    private final IsOnlyUserCourse isOnlyUserCourse;
//    private final IsEnrollmentOfUser isEnrollmentOfUser;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //CSRF -> cross-site request forgery (falsificação de solicitação entre sites)
        //neste caso a csrf esta desativada (nao há proteção para esse ataque)
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                authorizeRequests ->
                        authorizeRequests
                                // All
                                .requestMatchers(HttpMethod.GET, "/api/cursos").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/cursos/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                                .requestMatchers(HttpMethod.POST,"api/instrutor").permitAll()
                                // Usuario
                                .requestMatchers(HttpMethod.GET,"api/usuarios/{id}").access(isUser)
                                .requestMatchers(HttpMethod.PUT,"api/usuarios/{id}").access(isUser)
                                .requestMatchers(HttpMethod.DELETE,"api/usuarios/{id}").access(isUser)
                                .requestMatchers(HttpMethod.PATCH, "api/usuarios/{id}/senha").access(isUser)
                                // Aula
                                .requestMatchers(HttpMethod.GET,"/api/cursos/{cursoId}/aulas/{aulaId}").access(isOnlyUserCourse)
                                .requestMatchers(HttpMethod.GET,"/api/cursos/{cursoId}/aulas").access(isOnlyUserCourse)
                                // Inscricao
                                .requestMatchers(HttpMethod.POST,"/api/cursos/{cursoId}/incricoes").hasAuthority("POST")
                                .anyRequest().authenticated()
        );

        http.securityContext((context) -> context.securityContextRepository(securityContextRepository));

        http.logout(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());

        //stateless -> não há persistencia de sessão, assim que a API envia a response a sessão é terminada
//        http.sessionManagement(config -> config.sessionCreationPolicy(
//                SessionCreationPolicy.STATELESS));

        return http.build();
    }
}