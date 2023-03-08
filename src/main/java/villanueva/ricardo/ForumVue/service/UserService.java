package villanueva.ricardo.ForumVue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.ForumVue.dao.UserDAO;
import villanueva.ricardo.ForumVue.model.User;

import java.util.ArrayList;
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

    public List<String> getPermissions(String role) {
        List<String> root = new ArrayList<>();

        switch (role) {
            case "admin":
                root.add("own_topics:write");
                root.add("own_topics:delete");
                root.add("own_replies:write");
                root.add("own_replies:delete");
                root.add("categories:write");
                root.add("categories:delete");
                break;
            case "user":
                root.add("own_topics:write");
                root.add("own_topics:delete");
                root.add("own_replies:write");
                root.add("own_replies:delete");
                break;
        }
        return root;
    }

    public User findById(Long userId) {
        List<User> users = userDAO.findByIdLike(userId);
        return users.get(0);
    }
}
