package fishinghelper.common_module.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;
    @NotNull
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_privileges",
    joinColumns = @JoinColumn(name = "role_id_role"),
    inverseJoinColumns = @JoinColumn(name = "privileges_id_privileges"))
    private List<Privileges> privileges;

    public Role(String name) {
        this.name = name;
    }
}
