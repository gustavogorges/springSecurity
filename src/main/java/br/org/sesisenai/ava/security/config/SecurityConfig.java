package br.org.sesisenai.ava.security.config;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //CSRF -> cross-site request forgery (falsificação de solicitação entre sites)
        //neste caso a csrf esta desativada (nao há proteção para esse ataque)
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/api/cursos").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/cursos/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                                .anyRequest().authenticated()
        );

        http.securityContext((context) -> context.securityContextRepository(securityContextRepository));

        http.logout(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());

        //stateless -> não há persistencia de sessão, assim que a API envia a response a sessão é terminada
        http.sessionManagement(config -> config.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        return http.build();
    }
}