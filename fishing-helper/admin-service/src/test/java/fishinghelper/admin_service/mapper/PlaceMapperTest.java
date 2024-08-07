package fishinghelper.admin_service.mapper;


import fishinghelper.admin_service.dto.PlaceDTO;
import fishinghelper.admin_service.dto.PlaceDTOResponse;
import fishinghelper.common_module.entity.place.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {PlaceMapperImpl.class})
public class PlaceMapperTest {
    @Autowired
    private PlaceMapper placeMapper;


    @Test
    public void testToDTOsResponse() {
        List<Place> places = new ArrayList<>();
        Place place1 = new Place();
        place1.setId(1);
        place1.setName("Place One");
        place1.setCoordinates("Location One");
        places.add(place1);

        Place place2 = new Place();
        place2.setId(2);
        place2.setName("Place Two");
        place2.setCoordinates("Location Two");
        places.add(place2);

        List<PlaceDTOResponse> dtoResponses = placeMapper.toDTOsResponse(places);

        assertNotNull(dtoResponses);
        assertEquals(place1.getName(), dtoResponses.get(0).getName());
        assertEquals(place2.getName(), dtoResponses.get(1).getName());
    }

    @Test
    public void testToEntity() {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setId(1);
        placeDTO.setName("Place One");
        placeDTO.setCoordinates("Location One");

        Place place = placeMapper.toEntity(placeDTO);

        assertNotNull(place);
        assertEquals(placeDTO.getId(), place.getId());
        assertEquals(placeDTO.getName(), place.getName());
        assertEquals(placeDTO.getCoordinates(), place.getCoordinates());
    }

    @Test
    public void testUpdatePlaceFromDTO() {
        Place place = new Place();
        place.setId(1);
        place.setName("Old Name");
        place.setCoordinates("Old Location");

        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("New Name");
        placeDTO.setCoordinates("New Location");

        placeMapper.updatePlaceFromDTO(placeDTO, place);

        assertEquals(placeDTO.getName(), place.getName());
        assertEquals(placeDTO.getCoordinates(), place.getCoordinates());
    }

    @Test
    public void testToDTO() {
        Place place = new Place();
        place.setId(1);
        place.setName("Place One");
        place.setCoordinates("Location One");

        PlaceDTO placeDTO = placeMapper.toDTO(place);

        assertNotNull(placeDTO);
        assertEquals(place.getId(), placeDTO.getId());
        assertEquals(place.getName(), placeDTO.getName());
        assertEquals(place.getCoordinates(), placeDTO.getCoordinates());
    }
}
