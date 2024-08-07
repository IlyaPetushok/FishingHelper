package fishinghelper.common_module.entity.place;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Table(name="owner")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_owner")
    private Integer id;
    @NotNull
    @Column(name = "name")
    private String name;
    @Pattern(regexp = "\\+375(29|33|44)\\d{7}")
    @Column(name="phone_number")
    private String number;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "owner")
    private List<Place> places;
}
