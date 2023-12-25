package crud.service;

import crud.dao.UserDao;
import crud.model.Response;
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
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.NoResultException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private static User user;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, WebClient webClient) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.webClient = webClient;
    }

    public Response getUserForStatistic() {
        return webClient.get().retrieve().bodyToMono(Response.class).block();
    }

    @Override
    public User findUserById(Long id) {
        try {
            user = userDao.findUserById(id);
            logger.info("Пользователь по ID: '{}' с ролью: {} найден!", user.getId(), user.getRoles());
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
            logger.info("Пользователь по логину '{}' с ролью '{}' найден!", user.getLogin(), user.getRoles());
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
            logger.info("Пользователь '{}' с ролью '{}' найден!", user.getLogin(), user.getRoles());
            return user;
        } catch (EmptyResultDataAccessException exp) {
            logger.error("Введен неверно логин или пароль: username: {}", username);
            throw new InternalAuthenticationServiceException("User not found");
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            List<User> user = userDao.getUsers().stream().sorted(Comparator.comparing(User::getId)).toList();
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
            user = userDao.findUserById(id);
            logger.info("Получение профиля пользователя по ID: '{}', прошло удачно", id);
            return user;
        } catch (Exception exception) {
            logger.info("Не удалось получить профиль пользователя по ID: '{}'", id);
            throw new NoResultException("Failed to retrieve user profile by ID");
        }
    }

    @Override
    public void saveUpdateUser(User user, String[] rolesStrArray, Principal principal) throws Exception {
        try {
            Set<Role> role = Arrays.stream(rolesStrArray).map(Role::valueOf).collect(Collectors.toSet());
            user.setRoles(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getId() == null) {
                try {
                    userDao.saveUser(user);
                    logger.info("Администатор '{}' добавил пользователя. ID: {}. Роль: {}", principal.getName(), user.getId(), Arrays.toString(rolesStrArray));
                } catch (Exception exception) {
                    logger.error("Администатор '{}' не смог добавить пользователя", principal.getName());
                }
            } else {
                try {
                    userDao.updateUser(user);
                    logger.info("Администатор '{}' изменил данные у пользователя. ID: {}", principal.getName(), user.getId());
                } catch (Exception exception) {
                    logger.error("Администатор '{}' не смог изменить данные у пользователя. ID: {}", principal.getName(), user.getId());
                }
            }
        } catch (Exception exception) {
            logger.error("Что-то пошло не так при добавление/изменение пользователя");
            throw new Exception("Something went wrong when adding/modifying a user");
        }
    }

    @Override
    public void deleteUser(Long id, Principal principal) {
        try {
            userDao.deleteUser(id);
            logger.info("Администатор '{}' удалил пользователя. ID: {}", principal.getName(), id);
        } catch (Exception exception) {
            logger.error("Администатор '{}' не смог удалить пользователя. ID: {}", principal.getName(), id);
        }
    }
}