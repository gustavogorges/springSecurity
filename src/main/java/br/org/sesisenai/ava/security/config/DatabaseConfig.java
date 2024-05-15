package br.org.sesisenai.ava.security.config;

import br.org.sesisenai.ava.entity.Usuario;
import br.org.sesisenai.ava.repository.UsuarioRepository;
import br.org.sesisenai.ava.security.model.USerDetails;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DatabaseConfig {
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init(){
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setUserDetailsEntity(USerDetails.builder()
                .usuario(usuario).enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .usuario(usuario)
                .authorities(new GrantedAuthority().getAuthority())
                .password(new BCryptPasswordEncoder().encode("123")).build());
        usuarioRepository.save(usuario);
    }
}
