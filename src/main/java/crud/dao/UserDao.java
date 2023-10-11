package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getUsers();

    List<User> getMyProfile(Long id);

    User findUserById(Long id);

    User findUserByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    User deleteUser(Long id);


}
