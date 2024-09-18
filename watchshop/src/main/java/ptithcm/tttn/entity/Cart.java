package ptithcm.tttn.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long cart_id;

    @Column
    private int total_price;

    @Column
    private int total_quantity;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long customer_id;

    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id",insertable = false, updatable = false)
    private Customer customer;


}
