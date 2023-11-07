package crud.service;

import crud.model.Role;
import crud.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User findUserById(Long id);

    User findUserByLogin(String name);

    User getProfileUser(Long id);

    User findRoles(Set<Role> roles);

    void saveUpdateUser(User user, String[] rolesStrArray);

    User deleteUser(Long id);
}
