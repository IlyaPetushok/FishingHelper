package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.MistakeDTO;
import fishinghelper.common_module.entity.common.Mistake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {MistakeMapperImpl.class})
public class MistakeMapperTest {
    @Autowired
    private MistakeMapper mistakeMapper;

    @Test
    public void testToEntity() {
        MistakeDTO mistakeDTO = new MistakeDTO();
        mistakeDTO.setText("Sample mistake description");

        Mistake mistake = mistakeMapper.toEntity(mistakeDTO);

        assertNotNull(mistake);
        assertEquals(mistakeDTO.getText(), mistake.getText());
    }
}