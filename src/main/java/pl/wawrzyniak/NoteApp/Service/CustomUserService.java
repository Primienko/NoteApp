package pl.wawrzyniak.NoteApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wawrzyniak.NoteApp.Repository.Entities.User;
import pl.wawrzyniak.NoteApp.Repository.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    CustomUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
