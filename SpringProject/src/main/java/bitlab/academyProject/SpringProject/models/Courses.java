package bitlab.academyProject.SpringProject.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photo;
    @Column(name = "content",columnDefinition = "TEXT")
    private String content;
    private String name;
    private String mesta;
    private String lavel;
    @Column(name = "description",columnDefinition = "TEXT")
    private String description;
    private int price;
    private String startDate;
    private String finishDate;
    private String teacher;
    private String days;
    private String startTime;
    private String finishTime;





    @ManyToMany(fetch = FetchType.LAZY)
    private List<Topics> topics;

}
