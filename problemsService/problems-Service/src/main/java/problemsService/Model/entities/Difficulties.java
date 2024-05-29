package problemsService.Model.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "difficulties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Difficulties {

    @Id
    private String id;
    private String name;
    private Long totalCount;

}
