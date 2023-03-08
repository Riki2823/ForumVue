package villanueva.ricardo.ForumVue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import villanueva.ricardo.ForumVue.model.User;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
    List<User> findByEmailLike(String email);

    List<User> findByIdLike(Long userId);
}
