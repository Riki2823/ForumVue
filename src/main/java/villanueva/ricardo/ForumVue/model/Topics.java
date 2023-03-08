package villanueva.ricardo.ForumVue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    String title;
    String category;
    String createdAt;
    String updatedAt;
    String _id;

    int replies;
    int views;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Categories categoryRef;

    public String get_id() {
        return _id;
    }

    public void set_id() {
        this._id = String.valueOf(this.id);
    }
    public Topics(){
    }
    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedDate() {
        return updatedAt;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedAt = updatedDate;
    }

    public Categories getCategoryRef() {
        return categoryRef;
    }

    public Topics(String content, String title, String category) {
        this.content = content;
        this.title = title;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryRef(Categories categoryRef) {
        this.categoryRef = categoryRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
