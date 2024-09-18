package ptithcm.tttn.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_detail")
public class CartDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long cart_detail_id;

    @Column
    private int quantity;

    @Column
    private int price;

    @Column
    private Long cart_id;

    @Column
    private String product_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id",insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false, updatable = false)
    private Product product;
}
