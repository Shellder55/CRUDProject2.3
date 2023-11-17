package crud.service;

import crud.dao.UserDao;
import crud.model.Role;
import crud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
public class UserServiceImpl implements UserService, UserDetailsService{
    private final UserDao userDao;
    private static User user;
    private final PasswordEncoder passwordEncoder;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserById(Long id) {
        try {
            user = userDao.findUserById(id);
            logger.info("Пользователь по ID: '" + user.getId() + "' с ролью: " + user.getRoles() + " найден!");
            return user;
        } catch (Exception exception) {
            logger.error("Пользователь по ID не найден. ID пользователя: {}", id);
            throw new NoResultException("User not found by ID");
        }
    }

    @Override
    public User findUserByLogin(String name) {
        try {
            user = userDao.findUserByLogin(name);
            logger.info("Пользователь по логину '" + user.getLogin() + "' с ролью " + user.getRoles() + " найден!");
            return user;
        } catch (Exception exception) {
            logger.error("Пользователь по логину не найден. Логин пользователя: {} ", name);
            throw new NoResultException("User not found by login");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            user = userDao.findUserByLogin(username);
            logger.info("Пользователь '" + user.getLogin() + "' с ролью " + user.getRoles() + " найден!");
            return user;
        } catch (EmptyResultDataAccessException exp) {
            logger.error("Введен неверно логин или пароль: username: {}", username);
            throw new InternalAuthenticationServiceException("User not found");
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            List<User> user = userDao.getUsers();
            logger.info("Загрузка всех пользователей прошла успешно!");
            return user;
        } catch (Exception exception) {
            logger.info("Не удалось загрузить всех пользователей");
            throw new NoResultException("Failed to load all users");
        }
    }

    @Override
    public User getProfileUser(Long id) {
        try {
            user = userDao.getProfileUser(id);
            logger.info("Получение профиля пользователя по ID: '" + id + "' прошло удачно");
            return user;
        } catch (Exception exception) {
            logger.info("Не удалось получить профиль пользователя по ID: '" + id + "'");
            throw new NoResultException("Failed to retrieve user profile by ID");
        }
    }

    @Override
    public void saveUpdateUser(User user, String[] rolesStrArray, Principal principal) {
        try {
            Set<Role> role = Arrays.stream(rolesStrArray).map(Role::valueOf).collect(Collectors.toSet());
            user.setRoles(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getId() == null) {
                try {
                    userDao.saveUser(user);
                    logger.info("Администатор '" + principal.getName() + "' добавил пользователя. ID: " + user.getId() + ". Роль: " + Arrays.toString(rolesStrArray));
                } catch (Exception exception) {
                    logger.error("Администатор '" + principal.getName() + "' не смог добавить пользователя");
                }
            } else {
                try {
                    userDao.updateUser(user);
                    logger.info("Администатор '" + principal.getName() + "' изменил данные у пользователя. ID: " + user.getId());
                } catch (Exception exception) {
                    logger.error("Администатор '" + principal.getName() + "' не смог изменить данные у пользователя. ID: " + user.getId());
                }
            }
        } catch (Exception exception) {
            logger.error("Что-то пошло не так");
        }
    }

    @Override
    public void deleteUser(Long id, Principal principal) {
        try {
            userDao.deleteUser(id, principal);
            logger.info("Администатор '" + principal.getName() + "' удалил пользователя. ID: {}", id);
        } catch (Exception exception) {
            logger.error("Администатор '" + principal.getName() + "' не смог удалил пользователя. ID: {}", id);
        }
    }
}