package br.org.sesisenai.ava.entity;

import br.org.sesisenai.ava.dto.abstraction.ResponseConversorDTO;
import br.org.sesisenai.ava.dto.implementation.usuario.UsuarioResponseDTO;
import br.org.sesisenai.ava.security.model.USerDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Usuario implements ResponseConversorDTO<UsuarioResponseDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "usuario")
    private Set<Certificado> certificados;

    @OneToMany(mappedBy = "usuario")
    private Set<Inscricao> inscricoes;

    @OneToOne(cascade = CascadeType.ALL)
    private USerDetails userDetailsEntity;

    public UsuarioResponseDTO toDTO() {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(this.id);
        usuarioResponseDTO.setNome(this.nome);
        usuarioResponseDTO.setEmail(this.email);
        usuarioResponseDTO.setDataCadastro(this.dataCadastro);
        return usuarioResponseDTO;
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(){
        setUserDetailsEntity();
    }

    public void setSenha(String senha) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.senha = passwordEncoder.encode(senha);
    }

    public void setUserDetailsEntity() {
        this.userDetailsEntity = USerDetails.builder()
                .usuario(this).enabled(true).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).build();
    }
}










