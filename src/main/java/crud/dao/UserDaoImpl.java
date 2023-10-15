package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public List<User> getMyProfile(String login){
//        return entityManager.createQuery("select u from User u where u.id = : id", User.class).getResultList();

        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.login = : login", User.class);
        query.setParameter("login", login);
        return query.getResultList();

//        return entityManager.createQuery("select u from User u where u.name = :name", User.class).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByName(String login) {
//        return entityManager.find(User.class, name);
//         return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class).getResultList().stream().findFirst().orElse(null);
//        return entityManager.createQuery("select u from User u where u.name = :name", User.class).getResultList().stream().findFirst().orElse(null);

        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.login = : login", User.class);
        query.setParameter("login", login);
        return query.getSingleResult();

    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user){
        entityManager.merge(user);
    }

    @Override
    public User deleteUser(Long id) {
        User user = findUserById(id);
        entityManager.remove(user);
        return user;
    }
}
