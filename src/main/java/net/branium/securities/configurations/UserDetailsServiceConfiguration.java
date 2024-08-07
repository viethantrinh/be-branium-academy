package net.branium.securities.configurations;

import lombok.RequiredArgsConstructor;
import net.branium.domains.User;
import net.branium.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceConfiguration implements UserDetailsService {
    private final UserRepository userRepo;

    /**
     * Load the user from database by the user's email
     *
     * @param username the user's email
     * @return user details with @{@link UserDetailsConfiguration}
     * @throws UsernameNotFoundException if user not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can not found user with email: " + username));
        return new UserDetailsConfiguration(user);
    }
}
