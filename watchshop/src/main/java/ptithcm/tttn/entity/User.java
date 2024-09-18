package ptithcm.tttn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long user_id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private  String status;

    @Column
    private float points;

    @Column
    private LocalDateTime created_at;

    @Column
    private  LocalDateTime updated_at;

    @Column
    private Long role_id;

    @Column
    private  String accessToken;

    @Column
    private  String refreshToken;

    @Column
    private Timestamp expiredAccessToken;

    @Column
    private  Timestamp expiredRefresh;

    @Column
    private Long updated_by;


    @ManyToOne
    @JoinColumn(name = "role_id",insertable = false, updatable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Staff> staffs;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    @JsonIgnore
    private Staff update_staff;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Customer> customers;


}
