package indiana.indi.indiana.config;

import indiana.indi.indiana.service.user.customUser.CustomUserDetails;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private Key key(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private JwtParser parser() {
        return Jwts.parserBuilder().setSigningKey(key()).build();
    }

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        Long id = userDetails.getId();
        String role = userDetails.getUser().getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User must have at least one role"))
                .getTitle();

        return Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .claim("id", id)
                        .claim("role", role)
                        .setIssuedAt(now)
                        .setExpiration(expiryDate)
                        .signWith(key()).compact();
    }

    public String extractUsername(String token) {
        return parser().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        var claims = parser().parseClaimsJws(token).getBody();
        String usernameFromToken = claims.getSubject();
        Date expiration = claims.getExpiration();

        return usernameFromToken.equals(userDetails.getUsername())
                && !expiration.before(new Date());
    }

}
