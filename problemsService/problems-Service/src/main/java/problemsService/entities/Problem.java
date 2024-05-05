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
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    @Column(length = 5000)
    private String description;
    private String attemptcount;
    private String solvedcount;
    @Column(length = 5000)
    private String solution;
    private String difficulty;
    private boolean identifier;


    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Example> examples;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Constrains> constraints;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
