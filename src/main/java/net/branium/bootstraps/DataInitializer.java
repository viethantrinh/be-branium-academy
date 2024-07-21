package net.branium.bootstraps;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.branium.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepo;
    @Override
    public void run(String... args) throws Exception {

    }
}
