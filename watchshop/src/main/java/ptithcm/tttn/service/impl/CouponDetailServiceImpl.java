package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.CouponDetail;
import ptithcm.tttn.repository.CouponDetailRepo;
import ptithcm.tttn.service.CouponDetailService;

import java.util.Optional;

@Service
public class CouponDetailServiceImpl implements CouponDetailService
{
    private  final CouponDetailRepo couponDetailRepo;

    public CouponDetailServiceImpl(CouponDetailRepo couponDetailRepo) {
        this.couponDetailRepo = couponDetailRepo;
    }

    @Override
    public CouponDetail updateStatusDetail(CouponDetail detail, Long coupon_detail_id) throws Exception {
        CouponDetail find = findById(coupon_detail_id);
        if(detail.getStatus().equals("Active")){
        find.setStatus("Inactive");
        }else{
            find.setStatus("Active");
        }
        return couponDetailRepo.save(find);
    }

    @Override
    public CouponDetail findById(Long id) throws Exception {
        Optional<CouponDetail> find = couponDetailRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("Not found coupon detail by id " + id);
    }
}
