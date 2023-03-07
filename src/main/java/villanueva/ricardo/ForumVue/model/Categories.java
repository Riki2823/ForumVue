package villanueva.ricardo.ForumVue.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String description;
    String slug;

    @OneToMany(mappedBy = "category")
    Set<Topics> topicsSet;

    public Set<Topics> getTopicsSet() {
        return topicsSet;
    }

    public void setTopicsSet(Set<Topics> topicsSet) {
        this.topicsSet = topicsSet;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
