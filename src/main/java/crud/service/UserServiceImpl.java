package crud.service;

import crud.dao.UserDao;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public void saveUpdateUser(User user) {
        if (user.getId() == null) {
            userDao.save(user);
        } else {
            userDao.update(user);
        }
    }

    public User deleteUser(Long id) {
        return userDao.deleteUser(id);
    }
}
