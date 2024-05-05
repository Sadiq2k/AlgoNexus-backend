package problemsService.request;


import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AddProblemRequest {

    private String title;

    private String description;

    // You can add more validation annotations as needed
    private String attemptcount;

    // You can add more validation annotations as needed
    private String solvedcount;

    private String solution;

    @NotBlank(message = "Difficulty cannot be blank")
    private String difficulty;

    // You can add more fields as needed

//    private String name;
//    private String message;

    // Consider using a custom class instead of arrays for input/output
//    @ElementCollection
//    private List<String> exampleInput;
//
////    @ElementCollection
//    private List<String> exampleOutput;
    private String explanation;

//    @ElementCollection
    private List<String> input;

//    @ElementCollection
    private List<String> output;

//    @ElementCollection
    private List<String> constraintText;

}
