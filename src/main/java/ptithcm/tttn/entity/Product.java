package ptithcm.tttn.entity;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column
    private String product_id;

    @Column
    private String product_name;

    @Column
    private int quantity;

    @Column
    private String image;

    @Column
    private String detail;

    @Column
    private String technology;

    @Column
    private String glass;

    @Column
    private String func;

    @Column
    private String color;

    @Column
    private String machine;

    @Column
    private String sex;

    @Column
    private String accuracy;

    @Column
    private String battery_life;

    @Column
    private String water_resistance;

    @Column
    private String weight;

    @Column
    private String other_features;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long brand_id;

    @Column
    private Long category_id;

    @Column
    private Long created_by;

    @Column
    private String status;

    @Column
    private Long updated_by;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CouponDetail> couponDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CartDetail> cartDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "brand_id",insertable = false, updatable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id",insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false, updatable = false)
    private Staff created_product;

    @OneToMany(mappedBy = "product")
    private List<PriceUpdateDetail> priceUpdateDetails;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    private Staff updated_product;
}
