package com.portfolio.expense_tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException
    {

        // Gets the "Authorization" from request
        final String authHeader = request.getHeader("Authorization");

        // Verify if the header "Authorization" is null or starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // There's no token, so the filter chain continues
        }

        // Extracts the token without the prefix "Bearer "
        String token = authHeader.substring(7);

        // Gets the username from the token
        String username = jwtService.extractUsername(token);

        // If it gets the username and there's not in the security context, it creates it
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details from the DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Verifies if the token is valid
            if (jwtService.isTokenValid(token, userDetails)) {

                // Creates the Spring authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Load additional info to the request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Stablishes the authenticated user in the Spring context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continues the security filter chain
        filterChain.doFilter(request, response);
    }
}

