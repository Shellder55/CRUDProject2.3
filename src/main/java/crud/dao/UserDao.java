package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getUsers();

    User findUserById(Long id);

    void save(User user);

    void update(User user);

    User deleteUser(Long id);
}
