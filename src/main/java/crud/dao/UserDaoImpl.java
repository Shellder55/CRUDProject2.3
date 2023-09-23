package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        if (entityManager == null) {
            return null;
        } else {
            return entityManager.createQuery("from User", User.class).getResultList();
        }
    }

    public User save(User user) {
        entityManager.getTransaction().begin();
        User userFromDB = entityManager.merge(user);
        entityManager.getTransaction().commit();
        return userFromDB;
//        return null;
    }
}
