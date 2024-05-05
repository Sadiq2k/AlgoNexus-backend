package problemsService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String explanation;

//    @ElementCollection
//    @Column(name = "input")
//    private List<String> input;
//
//    @ElementCollection
//    @Column(name = "output")
//    private List<String> output;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;



}
