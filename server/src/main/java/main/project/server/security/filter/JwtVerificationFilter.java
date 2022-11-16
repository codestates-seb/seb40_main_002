package main.project.server.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import main.project.server.exception.AuthException;
import main.project.server.exception.ExceptionCode;
import main.project.server.jwt.JwtTokenizer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jws = request.getHeader("Authorization");
        Map<String, Object> claims = verifyJws(jws);
        verifyLogoutToken(jws);
        setAuthenticationToContext(claims);
        filterChain.doFilter(request, response);
    }

    private void verifyLogoutToken(String jws) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        String memberId = (String)valueOperations.get("logout@" + jws);

        if (memberId != null) {
            throw new AuthException(ExceptionCode.ALREADY_LOGOUT_MEMBER);
        }
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null){
            return true;
        }

        return false;
    }

    // 서명(Signature)된 jwt(jsw)를 검증
    private Map<String, Object> verifyJws(String jws) {
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JWT 서명(Signature)을 검증하기 위한 Secret Key
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody(); // 파싱에 성공하면 서명 검증에 성공한 것
        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String id = (String) claims.get("memberId");
        List<String> roles = (List<String>) claims.get("memberRoles");
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
