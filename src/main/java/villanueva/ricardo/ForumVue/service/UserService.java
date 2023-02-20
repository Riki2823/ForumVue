package villanueva.ricardo.ForumVue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.ForumVue.dao.UserDAO;
import villanueva.ricardo.ForumVue.model.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;
    public void addUser(User u){
        userDAO.save(u);
    }

    public List<User> findByEmail(String email) {
        return userDAO.findByEmailLike(email);
    }
}
