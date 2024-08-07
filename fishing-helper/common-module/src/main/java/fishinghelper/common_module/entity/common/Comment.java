package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import lombok.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_comment")
    private Integer id;
    @Column(name="text")
    private String text;
    @Column(name="grade")
    @Min(value = 1,message = "between 1 to 5")
    @Max(value = 5,message = "between 1 to 5")
    private Integer grade;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id_user", referencedColumnName = "id_user")
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id_place", referencedColumnName = "id_place")
    private Place place;
}
