package crud.service;

import crud.dao.UserDao;
import crud.model.Role;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    public User findUserByLogin(String name) {
        return userDao.findUserByLogin(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByLogin(username);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public void saveUpdateUser(User user, String[] rolesStrArray) {
        Set<Role> role = Arrays.stream(rolesStrArray).map(Role::valueOf).collect(Collectors.toSet());
        if (user.getId() == null) {
            user.setRoles(role);
            userDao.saveUser(user);
        } else {
            user.setRoles(role);
            userDao.updateUser(user);
        }
    }

    public User deleteUser(Long id) {
        return userDao.deleteUser(id);
    }
}
