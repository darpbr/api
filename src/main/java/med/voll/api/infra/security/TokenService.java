package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll med")
                    .withSubject(usuario.getLogin()) // Serve para identificar o usuário ao qual o login pertence
//                    .withClaim("id", usuario.getId()) //-> Método chamado para gravar qualquer informação que desejemos passar no Token, opdem ter várias chamadas ao método .withClaim
                    .withExpiresAt(dataExpiracao()) // configura uma prazo de expiração
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Não foi possível gerar Token awt", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
