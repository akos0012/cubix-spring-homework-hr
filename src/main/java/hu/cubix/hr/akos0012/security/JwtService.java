package hu.cubix.hr.akos0012.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final Algorithm algorithm = Algorithm.HMAC256("mysecret");
    private static final String ISSUER = "HrApp";

    public String createJwt(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim("auth", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                .withIssuer(ISSUER)
                .sign(algorithm);
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(jwtToken);

        return new User(decodedJWT.getSubject(), "dummy", decodedJWT.getClaim("auth").asList(String.class).stream()
                .map(SimpleGrantedAuthority::new).toList());
    }
}
