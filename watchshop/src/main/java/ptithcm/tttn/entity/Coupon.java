package ptithcm.tttn.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long coupon_id;

    @Column
    private Date start_date;

    @Column
    private Date end_date;

    @Column
    private String content;

    @Column
    private String title;

    @Column
    private LocalDateTime created_at;

    @Column
    private  LocalDateTime updated_at;

    @Column
    private Long created_by;

    @Column
    private Long updated_by;

    @ManyToOne
    @JoinColumn(name = "created_by",insertable = false, updatable = false)
    private Staff created_coupon;

    @ManyToOne
    @JoinColumn(name = "updated_by",insertable = false, updatable = false)
    private Staff updated_coupon;

    @OneToMany(mappedBy = "coupon")
    private List<CouponDetail> couponDetails;

}
