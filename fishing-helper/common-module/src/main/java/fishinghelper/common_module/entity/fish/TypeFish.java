package fishinghelper.common_module.entity.fish;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "type_fish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeFish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private Integer id;
    @NotNull
    @Column(name = "type_name")
    private String name;
}
