package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private Integer id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "importance")
    private boolean importance;
    @Column(name = "status")
    private String status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id_user", referencedColumnName = "id_user")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id_integer"),
            inverseJoinColumns = @JoinColumn(name = "tags_id_article"))
    private List<Tags> tagsList;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "article")
    private List<Photo> photos;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "article")
    private List<Mistake> mistakes;
}
