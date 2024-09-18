package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/user/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<EntityResponse> getInfoProduct(@PathVariable String id){
        EntityResponse res = new EntityResponse();
        try{
            Product product = productService.findById(id);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setData(product);
        }catch (Exception e){
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Fail " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getAllProduct(){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<Product> product = productService.findAll();
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setData(product);
        }catch (Exception e){
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Fail " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
    @GetMapping("/{id}/category")
    public ResponseEntity<ListEntityResponse> getInfoProductByCategory(@PathVariable Long id){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<Product> product = productService.findByCategoryId(id);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setData(product);
        }catch (Exception e){
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Fail " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/find")
    public ResponseEntity<ListEntityResponse> getAllProductByDetail(@RequestParam String keyword){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<Product> find = productService.findByDetail(keyword);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setData(find);
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());

    }

}
