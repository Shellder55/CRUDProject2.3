package crud.service;

import crud.dao.UserDao;
import crud.dto.RestStatisticsResponse;
import crud.model.Role;
import crud.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private static User user;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, WebClient webClient) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.webClient = webClient;
    }

    @SneakyThrows
    @Override
    public RestStatisticsResponse getUserStatistics() {
        try {
            RestStatisticsResponse restStatisticsResponse = webClient.get().uri("rest/v1/statistics").retrieve().bodyToMono(RestStatisticsResponse.class).block();
            log.info("Загрузка статистики по всем пользователям прошла успешна");
            return restStatisticsResponse;
        } catch (Exception exception) {
            log.error("Не удалось загрузить статистику по всем пользователям");
            throw new Exception("Failed to load statistics for all users");
        }
    }

    @Override
    public User findUserById(Long id) {
        try {
            user = userDao.findUserById(id);
            log.info("Пользователь по ID: '{}' с ролью: {} найден!", user.getId(), user.getRoles());
            return user;
        } catch (Exception exception) {
            log.error("Пользователь по ID не найден. ID пользователя: {}", id);
            throw new NoResultException("User not found by ID");
        }
    }

    @Override
    public User findUserByLogin(String name) {
        try {
            user = userDao.findUserByLogin(name);
            log.info("Пользователь по логину '{}' с ролью '{}' найден!", user.getLogin(), user.getRoles());
            return user;
        } catch (Exception exception) {
            log.error("Пользователь по логину не найден. Логин пользователя: {} ", name);
            throw new NoResultException("User not found by login");
        }
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username){
        try {
            user = userDao.findUserByLogin(username);
            log.info("Пользователь '{}' с ролью '{}' найден!", user.getLogin(), user.getRoles());
            return user;
        } catch (EmptyResultDataAccessException exp) {
            log.error("Введен неверно логин или пароль: username: {}", username);
            throw new InternalAuthenticationServiceException("User not found");
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            List<User> user = userDao.getUsers().stream().sorted(Comparator.comparing(User::getId)).toList();
            log.info("Загрузка всех пользователей прошла успешно!");
            return user;
        } catch (Exception exception) {
            log.info("Не удалось загрузить всех пользователей");
            throw new NoResultException("Failed to load all users");
        }
    }

    @Override
    public User getProfileUser(Long id) {
        try {
            user = userDao.findUserById(id);
            log.info("Получение профиля пользователя по ID: '{}', прошло удачно", id);
            return user;
        } catch (Exception exception) {
            log.info("Не удалось получить профиль пользователя по ID: '{}'", id);
            throw new NoResultException("Failed to retrieve user profile by ID");
        }
    }

    @SneakyThrows
    @Override
    public void saveUpdateUser(User user, String[] rolesStrArray, Principal principal) {
        try {
            Set<Role> role = Arrays.stream(rolesStrArray).map(Role::valueOf).collect(Collectors.toSet());
            user.setRoles(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getId() == null) {
                try {
                    userDao.saveUser(user);
                    log.info("Администатор '{}' добавил пользователя. ID: {}. Роль: {}", principal.getName(), user.getId(), Arrays.toString(rolesStrArray));
                } catch (Exception exception) {
                    log.error("Администатор '{}' не смог добавить пользователя", principal.getName());
                }
            } else {
                try {
                    userDao.updateUser(user);
                    log.info("Администатор '{}' изменил данные у пользователя. ID: {}", principal.getName(), user.getId());
                } catch (Exception exception) {
                    log.error("Администатор '{}' не смог изменить данные у пользователя. ID: {}", principal.getName(), user.getId());
                }
            }
        } catch (Exception exception) {
            log.error("Что-то пошло не так при добавление/изменение пользователя");
            throw new Exception("Something went wrong when adding/modifying a user");
        }
    }

    @Override
    public void deleteUser(Long id, Principal principal) {
        try {
            userDao.deleteUser(id);
            log.info("Администатор '{}' удалил пользователя. ID: {}", principal.getName(), id);
        } catch (Exception exception) {
            log.error("Администатор '{}' не смог удалить пользователя. ID: {}", principal.getName(), id);
        }
    }
}