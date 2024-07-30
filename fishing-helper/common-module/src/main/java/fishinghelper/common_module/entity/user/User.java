package fishinghelper.common_module.entity.user;

import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.common.Survey;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "login")
    private String login;
    @NotNull
    @Column(name = "password")
    private String password;
    @Column(name = "mail")
    @Pattern(regexp = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)")
    private String mail;
    @Column(name = "mail_status")
    private boolean mailStatus;
    @Column(name = "data_registration")
    private Date dateRegistration;
//    @CreationTimestamp
//    private LocalDateTime dateRegistration;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id_user"),
    inverseJoinColumns = @JoinColumn(name = "role_id_role"))
    private List<Role> roles;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    private List<Article> article;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    private List<Comment> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    private List<Survey> surveys;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "constrain",joinColumns = @JoinColumn(name = "user_id_constr"),
    inverseJoinColumns = @JoinColumn(name = "privileges_id_constr"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Privileges> privileges;

    public User(Integer id) {
        this.id = id;
    }
}
