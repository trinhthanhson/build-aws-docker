package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.PriceUpdateDetail;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.PriceUpdateDetailRepo;
import ptithcm.tttn.service.PriceUpdateDetailService;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class PriceUpdateDetailServiceImpl implements PriceUpdateDetailService {

    private final ProductService productService;
    private final PriceUpdateDetailRepo priceUpdateDetailRepo;
    private final UserService userService;
    private final StaffService staffService;

    public PriceUpdateDetailServiceImpl(ProductService productService, PriceUpdateDetailRepo priceUpdateDetailRepo, UserService userService, StaffService staffService) {
        this.productService = productService;
        this.priceUpdateDetailRepo = priceUpdateDetailRepo;
        this.userService = userService;
        this.staffService = staffService;
    }


    @Override
    @Transactional
    public PriceUpdateDetail updatePriceProduct(String id, PriceUpdateDetail priceUpdateDetail, String jwt) throws Exception {
        PriceUpdateDetail update = priceUpdateDetailRepo.findByProductId(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        if(priceUpdateDetail.getPrice_new() == update.getPrice_new()){
            return update;
        }else {
            update.setUpdated_by(staff.getStaff_id());
            update.setUpdated_at(LocalDateTime.now());
            update.setPrice_old(update.getPrice_new());
            update.setPrice_new(priceUpdateDetail.getPrice_new());
        }
        return priceUpdateDetailRepo.save(update);
    }
}
