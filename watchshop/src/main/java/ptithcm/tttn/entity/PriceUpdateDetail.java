package ptithcm.tttn.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "price_update_detail")
public class PriceUpdateDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long price_update_detail_id;

    @Column
    private int price_new;

    @Column
    private int price_old;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private String product_id;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id",insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "created_by",insertable = false, updatable = false)
    private Staff created_updated_price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    private Staff updated_updated_price;



}
