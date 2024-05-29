package problemSubmission.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import problemSubmission.Model.DTO.RecentSubmissionDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentSubmissionResponse {

    private List<RecentSubmissionDto> submissions;

}
