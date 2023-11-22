package crud.service;

import crud.Stabs;
import crud.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
class UserServiceImplTest{

    @PersistenceContext
    private EntityManager entityManager;

//    @BeforeEach
//    public void create() {
//
//
////        String[] rolesStrArray = new String[1];
////        rolesStrArray[0] = "ADMIN";
//
////        Principal principal =
//    }

    private final UserService userService;
    private final Stabs stabs;

    @Autowired
    public UserServiceImplTest(UserService userService, Stabs stabs) {
        this.userService = userService;
        this.stabs = stabs;
    }

    @Test
    void findUserByIdTest() {
//        userService.findUserById(id);
    }

    @Test
    void findUserByLogin() {
    }

    @ParameterizedTest()
    @ValueSource(strings = "login1")
    void loadUserByUsername(String name) {
        userService.loadUserByUsername(name);
    }

    @Test
    void getUsers() {
        stabs.createUsersToDB();
       List<User> list = userService.getUsers();
       System.out.println(list);
    }

    @Test
    void getProfileUser() {
    }

    @Test
    void saveUpdateUser() {

    }

    @Test
    void deleteUser() {
    }
}