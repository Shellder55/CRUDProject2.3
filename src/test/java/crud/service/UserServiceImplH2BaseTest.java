package crud.service;

import config.PrincipalForTest;
import crud.dao.UserRepository;
import crud.model.Role;
import crud.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceImplH2BaseTest extends PrincipalForTest {
    PrincipalForTest principal = new PrincipalForTest();
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplH2BaseTest(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User firstUser() {
        User user3 = new User();
        user3.setLogin("Login4");
        user3.setPassword("Password4");
        user3.setName("Name4");
        user3.setSurname("Surname4");
        user3.setGender("Gender4");
        user3.setAge(24);

        return user3;
    }

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

        User expectedUser3 = new User();
        expectedUser3.setId(3L);
        expectedUser3.setLogin("Login3");
        expectedUser3.setPassword("Password3");
        expectedUser3.setName("Name3");
        expectedUser3.setSurname("Surname3");
        expectedUser3.setGender("Gender3");
        expectedUser3.setAge(23);
        expectedUser3.setRoles(new HashSet<>(Collections.singleton(Role.ADMIN)));

        User expectedUser4 = new User();
        expectedUser4.setId(4L);
        expectedUser4.setLogin("Login4");
        expectedUser4.setPassword("Password4");
        expectedUser4.setName("Name4");
        expectedUser4.setSurname("Surname4");
        expectedUser4.setGender("Gender4");
        expectedUser4.setAge(24);
        expectedUser4.setRoles(new HashSet<>(Collections.singleton(Role.ADMIN)));

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(expectedUser1);
        expectedUsers.add(expectedUser2);
        expectedUsers.add(expectedUser3);
        expectedUsers.add(expectedUser4);

        return expectedUsers;
    }

    @Test
    void findUserById() {
        // given
        Long expectedUserId = expectedUsersMethod().get(0).getId();
        // when
        Long userId = userService.findUserById(1L).getId();
        // then
        assertEquals(expectedUserId, userId);
    }

    @Test
    void findUserByLogin() {
        // given
        String expectedUserLogin = expectedUsersMethod().get(0).getLogin();
        // when
        String userLogin = userService.findUserByLogin("Login1").getLogin();
        // then
        assertEquals(expectedUserLogin, userLogin);
    }

    @Test
    void getUsers() {
        // given
        List<User> expectedUsers = expectedUsersMethod().stream().limit(2).toList();
        // when
        List<User> allUsers = userService.getUsers().stream().limit(2).toList();
        // then
        assertEquals(expectedUsers, allUsers);
    }

    @Test
    void saveUser() throws Exception{
        // given
        User user = firstUser();
        User expectedUser = expectedUsersMethod().get(3);
        // when
        userService.saveUpdateUser(user, roleArraysAdmin(), principal);
        User result = userRepository.findById(4L).get();
        result.setPassword("Password4");
        // then
        assertEquals(expectedUser, result);
    }

    @Test
    void updateUser() throws Exception {
        // given
        User user = firstUser();
        user.setId(3L);
        User expectedUser = expectedUsersMethod().get(3);
        expectedUser.setId(3L);
        // when
        userService.saveUpdateUser(user, roleArraysAdmin(), principal);
        user.setPassword("Password4");
        //then
        assertEquals(expectedUser, user);
    }

    @Test
    void deleteUser() {
        //given
        List<User> expectedListUsers = new ArrayList<>();
        // when
        userService.deleteUser(1L, principal);
        //then
        assertEquals(expectedListUsers, userRepository.findById(1L).stream().toList());
    }
}