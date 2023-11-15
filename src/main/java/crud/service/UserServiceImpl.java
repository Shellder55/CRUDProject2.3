package crud.service;

import crud.dao.UserDao;
import crud.model.Role;
import crud.model.User;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userDao.findUserByLogin(username);
        } catch (EmptyResultDataAccessException exp){
            logger.error("Введен неверно логин или пароль");
            throw new NoResultException("Incorrect username or password.");
        }
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getProfileUser(Long id){
        return userDao.getProfileUser(id);
    }

    public void saveUpdateUser(User user, String[] rolesStrArray, Principal principal) {
        Set<Role> role = Arrays.stream(rolesStrArray).map(Role::valueOf).collect(Collectors.toSet());
        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getId() == null) {
            userDao.saveUser(user);
            logger.info("'" + principal.getName() + "' добавил пользователя. ID: " + user.getId() + ". Роль: " + Arrays.toString(rolesStrArray));
        } else {
            userDao.updateUser(user);
            logger.info("'" + principal.getName() + "' изменил данные у пользователя. ID: " + user.getId());
        }
    }

    public User deleteUser(Long id) {
        return userDao.deleteUser(id);
    }
}
