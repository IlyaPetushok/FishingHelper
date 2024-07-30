package fishinghelper.common_module.entity.place;

import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.common.Mistake;
import fishinghelper.common_module.entity.common.Photo;
import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.fish.Fish;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "place")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_place")
    private Integer id;
    @Column(name = "name")
    @NotNull
    private String name;
    @NotNull
    @Column(name = "coordinates")
    private String coordinates;
    @Column(name = "paid")
    @NotNull
    private boolean requirePayment;
    @Column(name = "description")
    @NotNull
    private String description;
    @Column(name = "rating")
    @Min(0)
    @Max(5)
    private Integer rating;
    @Column(name = "status")
    private String status;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id_type", referencedColumnName = "id_type")
    private TypePlace typePlace;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id_owner", referencedColumnName = "id_owner")
    private Owner owner;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "fish_place",
            joinColumns = @JoinColumn(name = "place_id_place"),
            inverseJoinColumns = @JoinColumn(name = "fish_id_fish"))
    private List<Fish> fish;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "place")
    private List<Mistake> mistakes;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "place")
    private List<Comment> comments;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "place")
    private List<Photo> photos;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "place")
    private List<Survey> surveys;
}
