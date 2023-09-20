package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDao {
    List<User> getUsers(List<User> list);

    User save(User user);
}
