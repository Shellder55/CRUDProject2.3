package crud.service;

import crud.dao.UserDao;
import crud.dao.UserDaoImpl;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    UserDao userDao = new UserDaoImpl();

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User save(User user){
       return userDao.save(user);
    }
}
