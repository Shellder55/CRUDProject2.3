package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;

@Repository
public interface UserDao {
    List<User> getUsers();
    User getProfileUser(Long id);

    User findUserById(Long id);

    User findUserByLogin(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(Long id, Principal principal);
}
