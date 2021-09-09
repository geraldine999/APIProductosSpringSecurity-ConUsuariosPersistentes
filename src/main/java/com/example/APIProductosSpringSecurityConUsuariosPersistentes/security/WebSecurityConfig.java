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

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails usuario1 = User.builder()
                .username("pablo")
                .password(passwordEncoder().encode("unafacil"))
                //.roles(UserRole.ADMIN.name())
                .authorities(UserRole.ADMIN.getGrantedAuthorities()) /*quiero saber que es lo que puede hacer ese rol*/
                .build();

        UserDetails usuario2= User.builder()
                .username("geraldine")
                .password(passwordEncoder().encode("unafacil"))
                //.roles(UserRole.ADMIN.name())
                .authorities(UserRole.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails usuario3= User.builder()
                .username("jazmin")
                .password(passwordEncoder().encode("unafacil"))
                //.roles(UserRole.CLIENTE.name())
                .authorities(UserRole.CLIENTE.getGrantedAuthorities())
                .build();
        UserDetails usuario4= User.builder()
                .username("natalia")
                .password(passwordEncoder().encode("unafacil"))
                //.roles(UserRole.CLIENTE.name())
                .authorities(UserRole.CLIENTE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(usuario1, usuario2, usuario3, usuario4);
        //con InMemoryUserDetailsManager no hace falta usar BD, funciona con un mapa
        //mientras este corriendo el sistema, los usuarios existen
        //es la implementacion de la interfaz UserDetailsService
    }
}
