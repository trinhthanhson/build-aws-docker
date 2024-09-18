package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Coupon;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Long> {
}
