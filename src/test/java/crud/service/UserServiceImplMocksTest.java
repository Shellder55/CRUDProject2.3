package crud.service;

import config.PrincipalForTest;
import crud.dao.UserDao;
import crud.model.Role;
import crud.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceImplMocksTest {

    PrincipalForTest principal = new PrincipalForTest();
    UserDao userDao = Mockito.mock(UserDao.class);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserService userService = new UserServiceImpl(userDao, passwordEncoder, null);

    public String[] roleArraysAdmin() {
        return new String[]{"ADMIN"};
    }

    private List<User> expectedUsersMethod() {
        User expectedUser1 = new User();
        expectedUser1.setId(1L);
        expectedUser1.setPassword("Password1");
        expectedUser1.setLogin("Login1");
        expectedUser1.setName("Vitaliy");
        expectedUser1.setSurname("Surname1");
        expectedUser1.setGender("Male");
        expectedUser1.setAge(21);
        expectedUser1.setRoles(new HashSet<>(Collections.singleton(Role.ADMIN)));

        User expectedUser2 = new User();
        expectedUser2.setId(2L);
        expectedUser2.setPassword("Password2");
        expectedUser2.setLogin("Login2");
        expectedUser2.setName("Name2");
        expectedUser2.setSurname("Surname2");
        expectedUser2.setGender("Male");
        expectedUser2.setAge(22);
        expectedUser2.setRoles(new HashSet<>(Collections.singleton(Role.USER)));

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(expectedUser1);
        expectedUsers.add(expectedUser2);

        return expectedUsers;
    }

    @Test
    void findUserById() {
        // given
        Long expectedUserId = expectedUsersMethod().get(0).getId();
        User actualUser = new User(1L, "Login1", "Password1", "Vitaliy", "Surname1",
                "FEMALE", 21, Collections.singleton(Role.ADMIN));
        Mockito.when(userDao.findUserById(1L)).thenReturn(actualUser);
        // when
        Long resultUserId = userService.findUserById(1L).getId();
        // then
        assertEquals(expectedUserId, resultUserId);
        Mockito.verify(userDao).findUserById(1L);
    }

    @Test
    void findUserByLogin() {
        // given
        String expectedUserLogin = expectedUsersMethod().get(0).getLogin();
        User actualUser = new User("Login1", "ActualPassword", "ActualName", "ActualSurname",
                "ActualMale", 21, Collections.singleton(Role.ADMIN));
        Mockito.lenient().when(userDao.findUserByLogin("Login1")).thenReturn(actualUser);
        // when
        String actualUserLogin = userService.findUserByLogin("Login1").getLogin();
        // then
        assertEquals(expectedUserLogin, actualUserLogin);
    }

    @Test
    void getUsers() {
        // given
        List<User> expectedUsers = expectedUsersMethod().stream().limit(2).toList();
        User actualUser1 = new User(1L, "Login1", "Password1", "Vitaliy", "Surname1",
                "Male", 21, Collections.singleton(Role.ADMIN));
        User actualUser2 = new User(2L, "Login2", "Password2", "Name2", "Surname2",
                "Male", 22, Collections.singleton(Role.USER));
        Mockito.lenient().when(userDao.getUsers()).thenReturn(List.of(actualUser1, actualUser2));
        // when
        List<User> actualAllUsers = userService.getUsers().stream().limit(2).toList();
        // then
        assertEquals(expectedUsers, actualAllUsers);
    }

    @Test
    void saveUser() {
        // given
        User actualUser = new User("Login4", "Password4", "Name4", "Surname4",
                "Gender4", 24, Collections.singleton(Role.ADMIN));
        // when
        Mockito.lenient().doNothing().when(userDao).saveUser(actualUser);
        // then
        assertDoesNotThrow(() -> userService.saveUpdateUser(actualUser, roleArraysAdmin(), principal));
        Mockito.verify(userDao).saveUser(actualUser);
    }

    @Test
    void saveUserThrowException() {
        User actualUser = new User("Login4", null, "Name4", "Surname4",
                "Gender4", 24, Collections.singleton(Role.ADMIN));
        // then
        assertThrows(Exception.class, () -> userService.saveUpdateUser(actualUser, roleArraysAdmin(), principal));
        Mockito.verifyNoInteractions(userDao);
    }

    @Test
    void updateUser() {
        // given
        User actualUser = new User(3L, "Login3", "Password3", "Name3", "Surname3",
                "Male", 23, Collections.singleton(Role.ADMIN));
        // when
        Mockito.lenient().doNothing().when(userDao).updateUser(actualUser);
        //then
        assertDoesNotThrow(() -> userService.saveUpdateUser(actualUser, roleArraysAdmin(), principal));
        Mockito.verify(userDao).updateUser(actualUser);
    }

    @Test
    void deleteUser() {
        // when
        Mockito.lenient().doNothing().when(userDao).deleteUser(1L);
        //then
        assertDoesNotThrow(() -> userService.deleteUser(1L, principal));
        Mockito.verify(userDao).deleteUser(1L);
    }
}