package ptithcm.tttn.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long customer_id;

    @Column
    private String citizen_id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private Date birthday;

    @Column
    private String address;

    @Column
    private String gender;

    @Column
    private String tax_id;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long user_id;

    @OneToMany(mappedBy = "customer_created")
    @JsonIgnore
    private List<Orders> order_created;

    @OneToMany(mappedBy = "customer_updated")
    @JsonIgnore
    private List<Orders> order_updated;

    @OneToMany(mappedBy = "review_created")
    @JsonIgnore
    private List<Review> review_created;

    @OneToMany(mappedBy = "review_update")
    @JsonIgnore
    private List<Review> review_update;

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;


}
