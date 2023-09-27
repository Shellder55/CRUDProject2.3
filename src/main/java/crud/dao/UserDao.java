package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getUsers();

    User findUser(Long id);

    void saveAndUpdate(User user);

    User deleteUser(Long id);
}
