package Project1.com.LibraryManagement.Service;

import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserImpl implements UserService{
    @Autowired
    public UserRepos usersRepos;

    @Override
    public boolean existsUser(String email) {
        return usersRepos.existsByEmail(email) ;
    }

    @Override
    public Users saveUser(Users users) {
        return usersRepos.save(users);
    }

    @Override
    public Users findById(Long Id) {
        return null;
    }

    @Override
    public Users findByEmai(String email) {
        return null;
    }

    @Override
    public Optional<Users> deleteById(Long Id) {
        return Optional.empty();
    }

    @Override
    public List<Users> getAllUsers() {
        return List.of();
    }

}
