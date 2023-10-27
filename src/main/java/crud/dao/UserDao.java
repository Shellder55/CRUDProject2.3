package crud.dao;

import crud.model.Role;
import crud.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserDao {
    List<User> getUsers();

    User findUserById(Long id);

    User findUserByLogin(String name);
    User findRoles(Set<Role> roles);

    void saveUser(User user);

    void updateUser(User user);

    User deleteUser(Long id);
}
