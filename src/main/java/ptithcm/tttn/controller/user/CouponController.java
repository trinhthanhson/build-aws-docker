package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.Coupon;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CouponService;

import java.util.List;

@RestController
@RequestMapping("/api/user/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> allCoupon(){
        ListEntityResponse res = new ListEntityResponse<>();
        try{
            List<Coupon> allCoupon = couponService.findAll();
            res.setData(allCoupon);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setData(null);
            res.setMessage("error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res,res.getStatus());

    }
}
