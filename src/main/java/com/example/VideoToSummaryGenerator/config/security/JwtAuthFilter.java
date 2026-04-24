package com.example.SpringSecurityDemo.config.security;

import com.example.SpringSecurityDemo.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader=request.getHeader("Authorization");
        String jwt=null;
        String email=null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);


            if(jwt == null || jwt.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT token");
                return;
            }

            try {
                email=jwtUtil.extractEmail(jwt);
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid jwt token");
            }

        }

        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           try{
               UserDetails userDetails=userService.loadUserByUsername(email);

               if(jwtUtil.validateToken(jwt,userDetails)){

                   UsernamePasswordAuthenticationToken authenticationToken=
                           new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                   authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);

               }else{
                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                   response.getWriter().write("Invalid jwt token");
                   return;
               }

           } catch (Exception e) {
               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               response.getWriter().write("Invalid jwt token");
               return;
           }
        }
        filterChain.doFilter(request,response);
}
    }
