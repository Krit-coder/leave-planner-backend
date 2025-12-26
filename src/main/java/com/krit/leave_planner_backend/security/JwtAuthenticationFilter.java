package com.krit.leave_planner_backend.security;

import com.krit.leave_planner_backend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        System.out.println("JWT FILTER HIT: " + request.getRequestURI());


        String authHeader = request.getHeader("Authorization");

        // No token → continue
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.extractClaims(token);

            Integer userIdNumber = claims.get("userId", Integer.class);
            Long userId = userIdNumber.longValue();
            String email = claims.getSubject();
            Boolean manager = claims.get("manager", Boolean.class);

            UserPrincipal principal =
                    new UserPrincipal(userId, email, manager);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            principal.getAuthorities()
                    );

            auth.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );
//            System.out.println("AUTH SET FOR: " + email);
//            System.out.println("AUTHORITIES: " + principal.getAuthorities());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);

        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
