package ptithcm.tttn.controller.staff;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Coupon;
import ptithcm.tttn.entity.CouponDetail;
import ptithcm.tttn.request.CouponRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CouponDetailService;
import ptithcm.tttn.service.CouponService;

import java.util.List;

@RestController
@RequestMapping("/api/staff/coupon")
public class StaffCouponController {

    private final CouponService couponService;
    private final CouponDetailService couponDetailService;

    public StaffCouponController(CouponService couponService, CouponDetailService couponDetailService) {
        this.couponService = couponService;
        this.couponDetailService = couponDetailService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getAllCouponByStaff(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<Coupon> allCoupon = couponService.findAll();
            res.setData(allCoupon);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
        }catch (Exception e){
            res.setData(null);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("success");
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<EntityResponse> addCoupontByStaff(@RequestHeader("Authorization") String jwt, @RequestBody CouponRequest coupon){
        EntityResponse res = new EntityResponse();
        System.out.println(coupon.getContent() + " " + coupon.getPercent() + " " + coupon.getEnd_date() + " " + coupon.getStart_date());
        try{
            if(!coupon.getContent().equals("") && coupon.getEnd_date() != null&& coupon.getStart_date() != null){
                Coupon create = couponService.createCoupon(coupon,jwt);
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
                res.setData(create);
                res.setMessage("success");
            }else{
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setData(null);
                res.setMessage("No blank characters allowed");
            }
        }catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setData(null);
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCouponByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        ApiResponse res = new ApiResponse();
        try{
            couponService.deleteCoupon(id);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ListEntityResponse> getAllCouponDetailByStaff(@RequestHeader("Authorization") String jwt,@PathVariable Long id){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<CouponDetail> allCoupon = couponService.findAllDetailByCouponId(id);
            res.setData(allCoupon);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
        }catch (Exception e){
            res.setData(null);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("success");
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> updateStatusCouponDetail(@RequestHeader("Authorization") String jwt,@PathVariable Long id, @RequestBody CouponDetail detail){
        ApiResponse res = new ApiResponse();
        try {
            CouponDetail couponDetail = couponDetailService.updateStatusDetail(detail, id);
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
