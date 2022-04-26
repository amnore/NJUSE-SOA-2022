package assignment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Edit {


    String type;
    Integer id;
    Integer grade;
    Integer courseId;
}
