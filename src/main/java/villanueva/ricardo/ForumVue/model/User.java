package villanueva.ricardo.ForumVue.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;
    String name;
    String password;
    String role;

    @OneToMany(mappedBy = "user")
    Set<Topics> topicsSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", passwd='" + password + '\'' +
                ", role='" + role + '\'';
    }
}
