package FAQ.Faq.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idReponse;
    private String responseText;
    private String createdby;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private Date updateDate;
    @JsonIgnore
    @OneToOne
    private Question question;

}
