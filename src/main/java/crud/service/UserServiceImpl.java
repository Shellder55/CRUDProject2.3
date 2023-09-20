package crud.service;

import crud.dao.UserDao;
import crud.dao.UserDaoImpl;
import crud.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

    UserDao userDao = new UserDaoImpl();

    public List<User> getUsers(List<User> list) {
        return userDao.getUsers(list);
    }

    public User save(User user){
       return userDao.save(user);
    }
}
