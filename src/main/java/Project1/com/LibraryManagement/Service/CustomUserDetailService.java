package Project1.com.LibraryManagement.Service;

import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    public UserRepos usersRepos;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    Users users = usersRepos.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Email Is Not Found"));
        return new org.springframework.security.core.userdetails.User(
                users.getEmail(),
                users.getPassword(),
                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + users.getRoles().name()))
        );
    }
}
