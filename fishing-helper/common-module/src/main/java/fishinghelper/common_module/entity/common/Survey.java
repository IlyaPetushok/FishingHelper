package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "survey")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_survey")
    private Integer id;

    @Column(name = "morning")
    private boolean morning;

    @Column(name = "afternoon")
    private boolean afternoon;

    @Column(name = "evening")
    private boolean evening;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id_user", referencedColumnName = "id_user")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id_place", referencedColumnName = "id_place")
    private Place place;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "survey_fish",
            joinColumns = @JoinColumn(name = "survey_id_survey"),
            inverseJoinColumns =@JoinColumn(name = "fish_id_fish"))
    private List<Fish> fishList;

    public Survey(User user, Place place, List<Fish> fishList) {
        this.user = user;
        this.place = place;
        this.fishList = fishList;
    }
}
