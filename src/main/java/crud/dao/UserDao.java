package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getUsers();

    User findUserById(Long id);

    User findUserByLogin(String name);

    void saveUser(User user);

    void updateUser(User user);

    User deleteUser(Long id);
}
