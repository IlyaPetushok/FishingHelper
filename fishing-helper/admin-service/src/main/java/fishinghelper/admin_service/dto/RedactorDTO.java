package fishinghelper.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedactorDTO {
    private String status;
    private MistakeDTO mistakeDTO;
}
