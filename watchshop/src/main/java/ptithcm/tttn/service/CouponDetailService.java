package ptithcm.tttn.service;

import ptithcm.tttn.entity.CouponDetail;

public interface CouponDetailService {

    CouponDetail updateStatusDetail(CouponDetail detail, Long coupon_detail_id) throws Exception;

    CouponDetail findById(Long id) throws Exception;
}
