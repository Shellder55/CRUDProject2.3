package crud.service;

import crud.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    List<User> getUsers();

    List<User> getMyProfile(String login);

    User findUserById(Long id);

    User findUserByName(String name);

    void saveUpdateUser(User user);

    User deleteUser(Long id);
}
