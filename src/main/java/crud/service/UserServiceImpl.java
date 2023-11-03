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

    public User findRoles(Set<Role> roles) {
        return userDao.findRoles(roles);
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
        user.setRoles(role);
        if (user.getId() == null) {
            userDao.saveUser(user);
        } else {
            userDao.updateUser(user);
        }
    }

    public User deleteUser(Long id) {
        return userDao.deleteUser(id);
    }
}
