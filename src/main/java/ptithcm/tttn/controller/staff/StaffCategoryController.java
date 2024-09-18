package ptithcm.tttn.controller.staff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff/category")
public class StaffCategoryController {

    private final CategoryService categoryService;

    public StaffCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<EntityResponse> addCategoriesByStaff(@RequestBody Category category, @RequestHeader("Authorization") String jwt) {
        EntityResponse res = new EntityResponse();
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        try {
//            for (Map<String, Object> categoryMap : categoriesMap) {
//                if (categoryMap.containsKey("category_name")) {
//                    String category_name = (String) categoryMap.get("category_name");
//                    saveCategory = categoryService.createCategory(category_name, jwt);
//                } else {
//                    throw new Exception("Please enter complete information for all categories");
//                }
//            }
            Category saveCategory = categoryService.createCategory(category.getCategory_name(),jwt);
            res.setData(saveCategory);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error: " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, httpStatus);
    }



    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updatedCategoryByStaff(@RequestBody Category category, @RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        ApiResponse res = new ApiResponse();
        try{
            if(category.getCategory_name().equals("")){
                throw new Exception("Please enter complete information");
            }
            Category saveCategory = categoryService.updateCategory(id,category.getCategory_name(),jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        ApiResponse res = new ApiResponse();
        try{
            
            Category saveCategory = categoryService.deleteCategory(id,jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

}
