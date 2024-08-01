package fishinghelper.weather_service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.weather_service.dto.WeatherPlaceDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParserJsonToEntity {
    private final ObjectMapper objectMapper;

    @Autowired
    public ParserJsonToEntity(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WeatherPlaceDTOResponse parseJsonToEntity(String json) {
        JsonNode rootNode;
        WeatherPlaceDTOResponse placeResponse = new WeatherPlaceDTOResponse();
        
        try {
            rootNode = objectMapper.readTree(json);

            JsonNode daysNode = rootNode.get("days").get(0);
            JsonNode currentConditionsNode = rootNode.get("currentConditions");

            if (daysNode != null && currentConditionsNode != null) {
                placeResponse.setTempMax(daysNode.get("tempmax").asText());
                placeResponse.setTempMin(daysNode.get("tempmin").asText());
                
                placeResponse.setTemp(currentConditionsNode.get("temp").asText());
                placeResponse.setSunRise(currentConditionsNode.get("sunrise").asText());
                placeResponse.setSunSet(currentConditionsNode.get("sunset").asText());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return placeResponse;
    }
}
