package villanueva.ricardo.ForumVue.model;

import jakarta.persistence.*;

@Entity
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    String title;
    String categorySlug;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Categories category;

    public Categories getCategory() {
        return category;
    }

    public Topics(String content, String title, String category) {
        this.content = content;
        this.title = title;
        this.categorySlug = category;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public void setCategory(Categories category) {
        this.category = category;
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
