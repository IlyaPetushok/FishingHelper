package fishinghelper.user_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer id;
    private String text;
    private Integer grade;
    private UserDTO user;

    public CommentDTO(Integer grade) {
        this.grade = grade;
    }
}
