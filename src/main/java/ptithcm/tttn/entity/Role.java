package ptithcm.tttn.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long role_id;

    @Column
    private String role_name;

    @Column
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    List<User> users;
}
