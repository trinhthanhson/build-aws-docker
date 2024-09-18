package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/user/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> findAllCategory(){
        ListEntityResponse res = new ListEntityResponse();
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        try{

            List<Category> categoryList = categoryService.findAll();
            res.setData(categoryList);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            httpStatus = HttpStatus.OK;
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("erorr " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res,httpStatus);
    }

    @GetMapping("/find")
    public ResponseEntity<EntityResponse> findCategoryByName(@RequestParam String name) throws Exception {
        EntityResponse res = new EntityResponse();
        Category category = categoryService.findCategoryByName(name);
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        res.setData(category);
        res.setMessage("Success");
        res.setStatus(HttpStatus.CREATED);
        res.setCode(HttpStatus.CREATED.value());
        httpStatus = HttpStatus.CREATED;
        return new ResponseEntity<>(res,httpStatus);
    }
}
