package ptithcm.tttn.service;

import ptithcm.tttn.entity.Coupon;
import ptithcm.tttn.entity.CouponDetail;
import ptithcm.tttn.request.CouponRequest;

import java.sql.SQLException;
import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponRequest coupon, String jwt) throws Exception;

    List<Coupon> findAll();

    Coupon updateCoupon(Long id, CouponRequest coupon, String jwt) throws Exception;

    Coupon findById(Long id) throws Exception;

    void deleteCoupon(Long coupon_id);

    List<CouponDetail> findAllDetailByCouponId(Long coupon_id);
}
