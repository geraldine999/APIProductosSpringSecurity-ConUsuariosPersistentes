package com.example.APIProductosSpringSecurityConUsuariosPersistentes.security.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "authorities")
public class AppGrantedAuthority implements GrantedAuthority {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authority_id;
    @Column(nullable = false)
    private String role;
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<AppUser> usuarios;

    public AppGrantedAuthority(String role) {
        this.role = role;
    }

    public AppGrantedAuthority() {
    }


    @Override
    public String getAuthority() {
        return null;
    }
}
