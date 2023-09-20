package crud.service;

import crud.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    List<User> getUsers(List<User> list);

    User save(User user);
}
