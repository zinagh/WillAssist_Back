package FAQ.Faq.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuestion;
    private String questionText;
    @Temporal(TemporalType.TIMESTAMP)
    private String createdby;
    private Date creationDate;
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Reponse reponse;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

}
