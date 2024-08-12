package fishinghelper.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherPlaceDTOResponse {
    private String tempMax;
    private String tempMin;
    private String temp;
    private String sunRise;
    private String sunSet;
}
