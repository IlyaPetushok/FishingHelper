package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.user_service.dto.PlaceDTORequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {PlaceMapperImpl.class})
public class PlaceMapperTest {

    @Autowired
    private PlaceMapper placeMapper;

    @Test
    public void testToEntity() {
        PlaceDTORequest placeDTORequest = new PlaceDTORequest();
        placeDTORequest.setName("Test Place");
        placeDTORequest.setFish(List.of(1, 2, 3));

        Place place = placeMapper.toEntity(placeDTORequest);

        assertNotNull(place);
        assertEquals("Test Place", place.getName());
        assertNotNull(place.getFish());
        assertEquals(3, place.getFish().size());

        assertEquals(1, place.getFish().get(0).getId());
        assertEquals(2, place.getFish().get(1).getId());
        assertEquals(3, place.getFish().get(2).getId());
    }

    @Test
    public void testMapToFishList() {
        List<Integer> fishIds = List.of(1, 2, 3);

        List<Fish> fishList = placeMapper.mapToFishList(fishIds);

        assertNotNull(fishList);
        assertEquals(3, fishList.size());

        assertEquals(1, fishList.get(0).getId());
        assertEquals(2, fishList.get(1).getId());
        assertEquals(3, fishList.get(2).getId());
    }
}
