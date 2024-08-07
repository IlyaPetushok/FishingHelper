package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.user_service.dto.FishDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {FishMapperImpl.class})
public class FishMapperTest {

    @Autowired
    private FishMapper fishMapper;

    @Test
    public void testToEntitiesWithEmptyList() {
        FishDTO fishDTO1 = new FishDTO();
        fishDTO1.setId(1);
        fishDTO1.setName("Carp");

        FishDTO fishDTO2 = new FishDTO();
        fishDTO2.setId(2);
        fishDTO2.setName("Okun");

        List<FishDTO> fishDTOs = List.of(fishDTO1, fishDTO2);

        List<Fish> fishEntities = fishMapper.toEntities(fishDTOs);

        assertNotNull(fishEntities);
        assertEquals(2, fishEntities.size());

        Fish fish1 = fishEntities.get(0);
        assertEquals(1, fish1.getId());
        assertEquals("Carp", fish1.getName());

        Fish fish2 = fishEntities.get(1);
        assertEquals(2, fish2.getId());
        assertEquals("Okun", fish2.getName());
    }
}
