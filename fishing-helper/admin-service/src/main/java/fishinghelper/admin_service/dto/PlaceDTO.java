package fishinghelper.admin_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {
    private Integer id;
    private String name;
    private String coordinates;
    private boolean requirePayment;
    private String description;
    private Integer rating;
    private String status;
}
