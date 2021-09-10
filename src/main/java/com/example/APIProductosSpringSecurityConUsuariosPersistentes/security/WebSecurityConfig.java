package com.example.APIProductosSpringSecurityConUsuariosPersistentes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration //le dice a Spring que antes de correr la aplicación tiene que leer esto
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //habilita el @PreAuthorize y el @PostAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    //deshabilita el csrf porque es una api
    //autoriza a todos los usuarios en las urls: "/","/index.html"
    //en las urls: "/api/productos/detalles" solo pueden entrar los admins luego de loguearse
    //cualquier otro request debe ser autenticado con el formLogin.
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.html").permitAll()
                //.antMatchers("/api/productos").hasRole(UserRole.ADMIN.getRole())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        //o formLogin(); ->.failureForwardUrl(), successForwardUrl()
    }     // o httpBasic(); -> para hacer pruebas en Postman
          // O OAUTH2-> aplicacion de terceros

    //esto suele ir en otra clase
    @Bean //ejecutar esto una vez e instanciarla
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5); //hashea y agrega salt and pepper
        //cuanto mas alto sea el strength, mas va a tardar
    }


}
