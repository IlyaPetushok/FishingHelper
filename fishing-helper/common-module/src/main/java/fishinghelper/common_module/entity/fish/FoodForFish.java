package fishinghelper.common_module.entity.fish;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_fish")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodForFish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_food")
    private Integer id;
    @NotNull
    @Column(name = "name")
    private String name;
}
