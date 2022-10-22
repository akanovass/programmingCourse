package bitlab.academyProject.SpringProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "password_histories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private Date saveDate;


    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUser user;


}
