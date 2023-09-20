package crud.dao;

import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class UserDaoImpl implements UserDao {


//    public EntityManager entityManager = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();
    @Override
    public List<User> getUsers(List<User> list) {
        return list.stream().collect(Collectors.toList());
    }

    public User save(User user){
//        entityManager.getTransaction().begin();
//        User userFromDB = entityManager.merge(user);
//        entityManager.getTransaction().commit();
//        return userFromDB;
        return null;
    }
}
