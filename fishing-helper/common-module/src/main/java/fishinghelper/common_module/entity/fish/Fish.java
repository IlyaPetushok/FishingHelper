package fishinghelper.common_module.entity.fish;

import fishinghelper.common_module.entity.common.Photo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fish")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_fish")
    private Integer id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name="description")
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "type_fish_fish",
    joinColumns = @JoinColumn(name = "type_fish_id_type"),
    inverseJoinColumns = @JoinColumn(name = "fish_id_fish"))
    private List<TypeFish> typeFish;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch =FetchType.LAZY)
    @JoinTable(name = "fish_food_fish",
    joinColumns = @JoinColumn(name="fish_id_fish"),
    inverseJoinColumns = @JoinColumn(name="food_fish_id_food"))
    private List<FoodForFish> foodForFish;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "fish")
    private List<Photo> photos;

    public Fish(Integer id) {
        this.id = id;
    }
}
