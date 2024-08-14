package fishinghelper.weather_service.service;

import fishinghelper.weather_service.dto.WeatherPlaceDTORequest;
import fishinghelper.weather_service.dto.WeatherPlaceDTOResponse;
import fishinghelper.weather_service.exception.WeatherInvalidLocationException;
import fishinghelper.weather_service.parser.ParserJsonToEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    @Value("${url.api.weather}")
    private String urlApiWeather;

    @Value("${token.api.weather}")
    private String apiKey;

    private final ParserJsonToEntity parserJsonToEntity;
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(ParserJsonToEntity parserJsonToEntity, RestTemplate restTemplate) {
        this.parserJsonToEntity = parserJsonToEntity;
        this.restTemplate = restTemplate;
    }

    @Cacheable(key = "#placeDTO.coordinates", value = "weather_data")
    @CircuitBreaker(name = "handlerResilienceVisualCrossingWebServices")
    public WeatherPlaceDTOResponse getDataWeatherForPlace(WeatherPlaceDTORequest placeDTO) {
        String dataWeather= sendRequestWeatherApi(placeDTO.getCoordinates());
        return parserJsonToEntity.parseJsonToEntity(dataWeather);
    }

    private String sendRequestWeatherApi(String coordinates){
        String localDateTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        String url =  UriComponentsBuilder.fromHttpUrl(urlApiWeather+coordinates+"/"+localDateTime+"/")
                .queryParam("key", apiKey)
                .queryParam("include", "current")
                .queryParam("elements", "tempmax,tempmin,temp,sunrise,sunset")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        }catch (HttpClientErrorException exception){
            throw new WeatherInvalidLocationException(HttpStatus.NO_CONTENT,exception.getMessage());
        }
    }
}
