package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.CouponDetailRepo;
import ptithcm.tttn.repository.CouponRepo;
import ptithcm.tttn.request.CouponRequest;
import ptithcm.tttn.service.CouponService;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    private final UserService userService;
    private final StaffService staffService;
    private final CouponRepo couponRepo;
    private final ProductService productService;
    private final CouponDetailRepo couponDetailRepo;

    public CouponServiceImpl(UserService userService, StaffService staffService, CouponRepo couponRepo, ProductService productService, CouponDetailRepo couponDetailRepo) {
        this.userService = userService;
        this.staffService = staffService;
        this.couponRepo = couponRepo;
        this.productService = productService;
        this.couponDetailRepo = couponDetailRepo;
    }

    @Override
    @Transactional
    public Coupon createCoupon(CouponRequest coupon, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Coupon create = new Coupon();
        create.setCreated_at(LocalDateTime.now());
        create.setCreated_by(staff.getStaff_id());
        create.setContent(coupon.getContent());
        create.setStart_date(coupon.getStart_date());
        create.setEnd_date(coupon.getEnd_date());
        create.setUpdated_at(LocalDateTime.now());
        create.setUpdated_by(staff.getStaff_id());
        create.setTitle(coupon.getTitle());
        Coupon saved = couponRepo.save(create);
        if(saved != null) {
            List<Product> allProduct = productService.findAll();
            for (Product p : allProduct) {
                if (p.getStatus().equals("Active")) {
                    CouponDetail couponDetail = new CouponDetail();
                    couponDetail.setCoupon_id(saved.getCoupon_id());
                    couponDetail.setStatus("Active");
                    couponDetail.setPercent(coupon.getPercent()/100);
                    couponDetail.setProduct_id(p.getProduct_id());
                    couponDetailRepo.save(couponDetail);
                }
            }
        }
        return saved;
    }

    @Override
    public List<Coupon> findAll() {
        return couponRepo.findAll();
    }

    @Override
    @Transactional
    public Coupon updateCoupon(Long id, CouponRequest coupon, String jwt) throws Exception {
        Coupon find = findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        find.setUpdated_by(staff.getStaff_id());
        find.setUpdated_at(LocalDateTime.now());
        find.setStart_date(coupon.getStart_date());
        find.setEnd_date(coupon.getEnd_date());
        find.setContent(coupon.getContent());
        return couponRepo.save(find);
    }

    @Override
    public Coupon findById(Long id) throws Exception {
        Optional<Coupon> find = couponRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("Not found coupon by id " + id);
    }

    @Override
    @Transactional
    public void deleteCoupon(Long coupon_id) {
        couponDetailRepo.deleteByCouponId(coupon_id);
        couponRepo.deleteById(coupon_id);
    }

    @Override
    public List<CouponDetail> findAllDetailByCouponId(Long coupon_id) {
        return couponDetailRepo.findAllByCouponId(coupon_id);
    }
}
