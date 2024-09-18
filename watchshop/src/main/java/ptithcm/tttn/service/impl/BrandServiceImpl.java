package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Brand;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.BrandRepo;
import ptithcm.tttn.repository.StaffRepo;
import ptithcm.tttn.repository.UserRepo;
import ptithcm.tttn.service.BrandService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepo brandRepo;
    private final UserService userService;
    private final StaffRepo staffRepo;

    public BrandServiceImpl(BrandRepo brandRepo, UserService userService, StaffRepo staffRepo) {
        this.brandRepo = brandRepo;
        this.userService = userService;
        this.staffRepo = staffRepo;
    }

    @Override
    public Brand findBrandById(Long id) throws SQLException {
        Optional<Brand> brand = brandRepo.findById(id);
        if(brand.isPresent()){
            return brand.get();
        }
        throw  new SQLException("not found with id " + id);
    }

    @Override
    @Transactional
    public Brand createBrand(Brand brand, String jwt) throws Exception {
        Brand create = new Brand();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        Brand saveBrand = new Brand();
        Brand checkExist = brandRepo.findByBrandName(brand.getBrand_name());
        if(checkExist == null) {
            try {
                create.setCreated_at(LocalDateTime.now());
                create.setCreated_by(staff.getStaff_id());
                create.setBrand_name(brand.getBrand_name());
                create.setUpdated_at(LocalDateTime.now());
                create.setUpdated_by(staff.getStaff_id());
                create.setStatus("Active");
                saveBrand = brandRepo.save(create);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else if(checkExist != null && checkExist.getStatus().equals("Inactive")){
            checkExist.setStatus("Active");
            checkExist.setUpdated_by(staff.getStaff_id());
            return brandRepo.save(checkExist);
        }
        else {
            throw new Exception("exist brand by name " + brand.getBrand_name());
        }
        return saveBrand;
    }

    @Override
    public List<Brand> findAll() {
        return brandRepo.findAll();
    }

    @Override
    @Transactional
    public Brand updateBrand(Long id, Brand brand, String jwt) throws Exception {
        Brand findBrand = findBrandById(id);
        boolean checkExist = checkExistBrand(brand.getBrand_name());
    if(!checkExist) {
        try {
            User user = userService.findUserByJwt(jwt);
            Staff staff = staffRepo.findByUserId(user.getUser_id());
            findBrand.setBrand_name(brand.getBrand_name());
            findBrand.setUpdated_by(staff.getStaff_id());
            findBrand.setUpdated_at(LocalDateTime.now());
        } catch (Exception e) {
            throw new Exception("error " + e.getMessage());
        }
    }else{
        throw new Exception("exist brand by name " + brand.getBrand_name());
    }
    return findBrand;
    }

    @Override
    public Brand findByBrandName(String brandName) throws Exception {
        Brand findBrand = brandRepo.findByBrandName(brandName);
        if(findBrand != null){
            return findBrand;
        }
        throw new Exception("not found brand with brand name  " + brandName);
    }

    @Override
    public boolean checkExistBrand(String name) {
        Brand brand = brandRepo.findByBrandName(name);
        if(brand != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Brand deleteBrand(Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        Brand find = findBrandById(id);
        if(find.getStatus().equals("Active")){
            find.setStatus("Inactive");
            find.setUpdated_by(staff.getStaff_id());
            return brandRepo.save(find);
        }else if(find.getStatus().equals("Inactive")){
            find.setStatus("Active");
            find.setUpdated_by(staff.getStaff_id());
            return brandRepo.save(find);
        }
        throw new Exception("Not found category by id " + id);
    }
}
