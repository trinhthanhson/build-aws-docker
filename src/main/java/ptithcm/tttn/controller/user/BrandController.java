package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Brand;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/user/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> findAllBrand(){
        ListEntityResponse res = new ListEntityResponse();
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        try{
            List<Brand> listBrand = brandService.findAll();
            res.setData(listBrand);
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
        Brand brand = brandService.findByBrandName(name);
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        res.setData(brand);
        res.setMessage("Success");
        res.setStatus(HttpStatus.OK);
        res.setCode(HttpStatus.OK.value());
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(res,httpStatus);
    }
}
