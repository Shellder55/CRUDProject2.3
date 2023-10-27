package crud.dao;

import crud.model.Role;
import crud.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

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
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }
    @Override
    public User findUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.login = : login", User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    @Override
    public User findRoles(Set<Role> roles) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u join fetch u.roles where u.roles = : roles", User.class);
        query.setParameter("roles", roles);
        return query.getSingleResult();

//       return entityManager.createQuery("select u from User u join fetch u.roles where : roles", User.class).getSingleResult();
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
