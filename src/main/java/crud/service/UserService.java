package crud.service;

import crud.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getUsers();

    User findUser(Long id);

    void saveAndUpdate(User user);

    User deleteUser(Long id);
}
