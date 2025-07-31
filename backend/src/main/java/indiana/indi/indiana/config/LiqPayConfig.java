package indiana.indi.indiana.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "liqpay")
public class LiqPayConfig {
    private String publicKey;
    private String privateKey;
}
