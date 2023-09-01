package am.itspace.eduquizrest.sequrity;
import com.example.eduquizcommon.entity.User;
import com.example.eduquizcommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //Implementation of the loadUserByUsername method from the UserDetailsService interface.
    //This method is used for user authentication by Spring Security when a user attempts to log in.
    //It retrieves the user details based on the provided username (email) from the database.
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        if (user.isEmpty()) {
            log.warn("User with email: {} not found.", s);
            throw new UsernameNotFoundException("Username not found");
        }
        return new CurrentUser(user.get());
    }
}