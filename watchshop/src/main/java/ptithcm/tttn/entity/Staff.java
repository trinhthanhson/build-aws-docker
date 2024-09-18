package ptithcm.tttn.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long staff_id;

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
    private int salary;

    @Column
    private String phone;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @Column
    private Long user_id;

    @OneToMany(mappedBy = "staff_created")
    @JsonIgnore
    private List<Bill> bills;

    @OneToMany(mappedBy = "created_brand")
    @JsonIgnore
    private List<Brand> created_brand;

    @OneToMany(mappedBy = "updated_brand")
    @JsonIgnore
    private List<Brand> updated_brand;

    @OneToMany(mappedBy = "staff_updated")
    @JsonIgnore
    private List<Orders> staff_updated;

    @OneToMany(mappedBy = "created_category")
    @JsonIgnore
    private List<Category> created_category;

    @OneToMany(mappedBy = "updated_category")
    @JsonIgnore
    private List<Category> updated_category;

    @OneToMany(mappedBy = "created_coupon")
    @JsonIgnore
    private List<Coupon> created_coupon;

    @OneToMany(mappedBy = "updated_coupon")
    @JsonIgnore
    private List<Coupon> updated_coupon;

    @OneToMany(mappedBy = "created_updated_price")
    @JsonIgnore
    private List<PriceUpdateDetail> priceCreateDetails;

    @OneToMany(mappedBy = "updated_updated_price")
    @JsonIgnore
    private List<PriceUpdateDetail> priceUpdateDetails;

    @OneToMany(mappedBy = "created_product")
    @JsonIgnore
    private List<Product> created_product;

    @OneToMany(mappedBy = "updated_product")
    @JsonIgnore
    private List<Product> updated_product;


    @OneToMany(mappedBy = "update_staff")
    @JsonIgnore
    private List<User> update_staff;

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
    private User user;


}
