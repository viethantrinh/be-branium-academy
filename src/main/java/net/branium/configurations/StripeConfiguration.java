package net.branium.configurations;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void initializeApiKey() {
        Stripe.apiKey = this.secretKey;

    }
}
