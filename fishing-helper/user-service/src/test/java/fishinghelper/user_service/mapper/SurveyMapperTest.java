package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.SurveyDTORequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {SurveyMapperImpl.class})
public class SurveyMapperTest {

    @Autowired
    private SurveyMapper surveyMapper;

    @Test
    public void testToEntity() {
        SurveyDTORequest surveyDTORequest = new SurveyDTORequest();
        surveyDTORequest.setUserId(1);

        Survey survey = surveyMapper.toEntity(surveyDTORequest);

        assertNotNull(survey.getUser());
        assertEquals(1, survey.getUser().getId());
    }

    @Test
    public void testToDTO() {
        User user = new User();
        user.setId(1);

        Survey survey = new Survey();
        survey.setUser(user);

        SurveyDTORequest surveyDTORequest = surveyMapper.toDTO(survey);

        assertNotNull(surveyDTORequest);
        assertEquals(1, surveyDTORequest.getUserId());
    }

    @Test
    public void testMapToUserList() {
        User user = surveyMapper.mapToUserList(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetIdUser() {
        User user = new User();
        user.setId(1);

        Integer userId = surveyMapper.getIdUser(user);

        assertNotNull(userId);
        assertEquals(1, userId);
    }

}
