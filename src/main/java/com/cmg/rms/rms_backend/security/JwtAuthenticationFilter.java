package com.cmg.rms.rms_backend.security;

import com.cmg.rms.rms_backend.service.RmsUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtTokenUtil jwtTokenUtil;
  private final RmsUserDetailsService rmsUserDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String authHeader = request.getHeader("Authorization");
    String jwtToken = null;
    String username = null;

    // verify header starts with "Bearer " and grabs actual token and extracts username
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwtToken = authHeader.substring(7);
      username = jwtTokenUtil.extractUsername(jwtToken);
    }

    // get user details by extracted username and check if token is valid for the user
    if (username != null && authentication == null) {
      UserDetails userDetails = rmsUserDetailsService.loadUserByUsername(username);

      if (jwtTokenUtil.isTokenValid(username, jwtToken, userDetails)) {

        List<String> roles = jwtTokenUtil.extractRoles(jwtToken);
        List<GrantedAuthority> authorities =
            roles.stream()
                .filter(role -> role != null && !role.trim().isEmpty())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
