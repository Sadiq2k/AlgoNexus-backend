package problemSubmission.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import problemSubmission.Model.DTO.AllSubmissionDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllSubmissionResponse {

    List<AllSubmissionDto> allSubmissionDtos;
    private int totalPages;
    private long totalElements;
}
