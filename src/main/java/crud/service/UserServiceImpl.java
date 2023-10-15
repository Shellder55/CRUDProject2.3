package crud.service;

import crud.dao.UserDao;
import crud.model.Role;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;

    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    public User findUserByName(String name){
        return userDao.findUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findUserByName(username);
//        if (user == null){
//            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        return userDao.findUserByName(username);
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
//    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public List<User> getMyProfile(String login){
        return userDao.getMyProfile(login);
    }

    public void saveUpdateUser(User user) {
        if (user.getId() == null) {
//            if (userDao.findUserByName(user.getName()) == null){
//                user.setRoles(Collections.singleton(new Role(1L, "ADMIN")));
//                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userDao.saveUser(user);
//            }
        } else {
            userDao.updateUser(user);
        }
    }

    public User deleteUser(Long id) {
        return userDao.deleteUser(id);
    }


}
