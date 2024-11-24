package com.nullware.ms_auth.security;

import com.nullware.ms_auth.dtos.UserAuthDTO;
import com.nullware.ms_auth.models.Plans;
import com.nullware.ms_auth.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    final TokenService tokenService;
    final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);
        log.info("Login: {}", login);
        if (login != null) {
            UserAuthDTO user = userRepository.findUserDTOByEmail(login)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            log.info("User found: {}", user);
            var authorities = Plans.getAuthoritiesByPlan(user.plan() != null ? user.plan().getName() : "free");
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            log.info("Authentication: {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
