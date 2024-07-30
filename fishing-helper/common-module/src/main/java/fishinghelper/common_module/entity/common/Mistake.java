package fishinghelper.common_module.entity.common;

import fishinghelper.common_module.entity.place.Place;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "mistake")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mistake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mistake")
    private Integer id;
    @Column(name = "text_mistake")
    @NotNull
    private String text;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mistake_id_article",referencedColumnName = "id_article")
    private Article article;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mistake_id_place",referencedColumnName = "id_place")
    private Place place;
}
