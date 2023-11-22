package crud;

import crud.model.Role;
import crud.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class Stabs {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void createUsersToDB(){
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
//        user1.setId(1L);
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setGender("gender1");
        user1.setAge(11);
        user1.setRoles(Collections.singleton(Role.ADMIN));

        System.out.println("gergergerg");
        entityManager.persist(user1);

        List<User> list = entityManager.createQuery("select distinct u from User u left join fetch u.roles ", User.class).getResultList();
        System.out.println(list);
    }
}
