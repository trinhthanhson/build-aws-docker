package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CategoryRepo;
import ptithcm.tttn.repository.StaffRepo;
import ptithcm.tttn.service.CategoryService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private final UserService userService;
    private final StaffRepo staffRepo;
    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(UserService userService, StaffRepo staffRepo, CategoryRepo categoryRepo) {
        this.userService = userService;
        this.staffRepo = staffRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    @Transactional
    public Category createCategory(String category_name, String jwt) throws Exception {
        Category create = new Category();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        Category saveCategory = new Category();
        Category checkExist = categoryRepo.findCategoryByName(category_name);
        if(checkExist == null){
            try {
                create.setCreated_at(LocalDateTime.now());
                create.setCreated_by(staff.getStaff_id());
                create.setCategory_name(category_name);
                create.setUpdated_at(LocalDateTime.now());
                create.setUpdated_by(staff.getStaff_id());
                create.setStatus("Active");
                saveCategory = categoryRepo.save(create);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else if(checkExist != null && checkExist.getStatus().equals("Inactive")) {
            checkExist.setStatus("Active");
            checkExist.setUpdated_by(staff.getStaff_id());
            return categoryRepo.save(checkExist);
        }
        else{
            throw new Exception("exist category name " + category_name);
        }
        return saveCategory;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, String category_name, String jwt) throws Exception {
        Category find = findById(id);
        boolean checkExist = checkExitsCategory(category_name);
        Category save = new Category();
        if(!checkExist){
        try{
            User user = userService.findUserByJwt(jwt);
            Staff staff = staffRepo.findByUserId(user.getUser_id());
            find.setCategory_name(category_name);
            find.setUpdated_by(staff.getStaff_id());
            find.setUpdated_at(LocalDateTime.now());
            save = categoryRepo.save(find);
        }catch (Exception e){
            throw new Exception("error " + e.getMessage());
        }
        }else{
            throw new Exception("exist category by name " + category_name);
        }
        return save;
    }

    @Override
    public Category findById(Long id) throws Exception {
        Optional<Category> find = categoryRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("not found category by id " + id);
    }

    @Override
    public Category findCategoryByName(String name) throws Exception {
        Category category = categoryRepo.findCategoryByName(name);
        if(category != null){
            return category;
        }
        throw new Exception("not found category by name " + name);
    }

    @Override
    @Transactional
    public Category deleteCategory(Long id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffRepo.findByUserId(user.getUser_id());
        Category find = findById(id);
        if(find.getStatus().equals("Active")){
            find.setStatus("Inactive");
            find.setUpdated_by(staff.getStaff_id());
            return categoryRepo.save(find);
        } else if (find.getStatus().equals("Inactive")) {
            find.setStatus("Active");
            find.setUpdated_by(staff.getStaff_id());
            return categoryRepo.save(find);
        }

        throw new Exception("Not found category by id " + id);
    }

    @Override
    public boolean checkExitsCategory(String name) {
        Category category = categoryRepo.findCategoryByName(name);
        if(category != null){
            return true;
        }else{
            return false;
        }
    }
}
