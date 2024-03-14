package com.example.enquetebackend.security;

import com.example.enquetebackend.exceptions.ErroPadrao;
import com.example.enquetebackend.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.io.IOException;
import java.util.Map;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            var token = this.recuperarToken(request);
            if (token != null) {
                var login = tokenService.validarToken(token);
                UserDetails user = usuarioRepository.findByLogin(login);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (ErroPadrao e){
            response.setStatus(e.getStatusCode().value());
            response.setCharacterEncoding("UTF-8");
            String jsonErrorMessage = "{\"mensagem\":\"" + e.getMessage() + "\"}";
            response.getWriter().write(jsonErrorMessage);
        }
    }

    private String recuperarToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
