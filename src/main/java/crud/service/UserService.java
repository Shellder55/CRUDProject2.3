package crud.service;

import crud.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getUsers();

    User findUserById(Long id);

    void saveUpdateUser(User user);
//    void updateUser(User user);

    User deleteUser(Long id);
}
