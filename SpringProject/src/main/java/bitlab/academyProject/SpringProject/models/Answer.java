package bitlab.academyProject.SpringProject.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tasks tasks;

    @ManyToOne(fetch = FetchType.EAGER)
    private AuthUser author;

    private Date postDate;
}
