package fishinghelper.weather_service.controller;

import fishinghelper.weather_service.dto.WeatherPlaceDTORequest;
import fishinghelper.weather_service.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<?> weatherGetData(@RequestBody WeatherPlaceDTORequest placeDTO){
        return new ResponseEntity<>(weatherService.getDataWeatherForPlace(placeDTO),HttpStatus.OK);
    }
}
