package crud.service;

import crud.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User findUserById(Long id);

    User findUserByLogin(String name);

    void saveUpdateUser(User user, String[] rolesStrArray);

    User deleteUser(Long id);
}
