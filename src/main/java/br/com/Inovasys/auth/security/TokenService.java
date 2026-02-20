package br.com.Inovasys.auth.security;

import br.com.Inovasys.auth.entity.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private long expirationMillis;

    public String gerarToken(Users usuario){

        System.out.println("SECRET USADA: [" + secret + "]");

        return JWT.create()
                .withIssuer("InovaSys")
                .withSubject(usuario.getCpf())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + expirationMillis))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("InovaSys")
                .build()
                .verify(token)
                .getSubject();
    }
}