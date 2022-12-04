package main.project.server.security.filter;

import lombok.RequiredArgsConstructor;
import main.project.server.exception.auth.handler.AuthExceptionResolver;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthExceptionHandlerFilter extends OncePerRequestFilter {

    private final AuthExceptionResolver authExceptionResolver; //실질적인 리졸버 역할

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            authExceptionResolver.handleException(e, response);
        }
    }

}
