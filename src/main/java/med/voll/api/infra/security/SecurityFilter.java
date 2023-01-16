package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        var subject = tokenService.getSubject(tokenJWT);


//        FilterChain representa a cadeia de filtros da aplicação. Com o comando a seguir ele vai continuar o fluxo
//        da requisição. Devem ser passados os parâmetros request e response.
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null){
            throw new RuntimeException("Token JWT inválido no cabeçalho!");
        }
        return authorizationHeader.replace("Bearer ", "");
    }
}
