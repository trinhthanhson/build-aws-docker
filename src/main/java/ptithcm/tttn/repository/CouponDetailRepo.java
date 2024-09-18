package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.CouponDetail;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CouponDetailRepo extends JpaRepository<CouponDetail, Long> {


    @Modifying
    @Transactional
    @Query("DELETE FROM CouponDetail cd WHERE cd.coupon_id = :coupon_id")
    void deleteByCouponId(Long coupon_id);

    @Query("SELECT cd FROM CouponDetail cd WHERE cd.coupon.id = :couponId")
    List<CouponDetail> findAllByCouponId(@Param("couponId") Long couponId);
}
