package crud.service;

import crud.dao.UserDao;
import crud.dao.UserDaoImpl;
import crud.model.Role;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
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

    public User getProfileUser(Long id){
        return userDao.getProfileUser(id);
    }

    public void saveUpdateUser(User user, String[] rolesStrArray) {
        Set<Role> role = Arrays.stream(rolesStrArray).map(Role::valueOf).collect(Collectors.toSet());
        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
