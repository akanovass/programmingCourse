package bitlab.academyProject.SpringProject.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topics {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topic",columnDefinition = "TEXT")
    private String topic;
    @Column(name = "topicDescription",columnDefinition = "TEXT")
    private String topicDescription;



//
//    @ManyToOne(fetch =FetchType.LAZY)
//    private Topics topics;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tasks> tasks;




}
