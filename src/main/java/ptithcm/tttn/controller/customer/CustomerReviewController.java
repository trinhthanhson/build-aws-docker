package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Review;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/customer/review")
public class CustomerReviewController {
    private final ReviewService reviewService;

    public CustomerReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createReviewByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody Review review){
        ApiResponse res = new ApiResponse();
        try{
            Review createReview = reviewService.createReview(review,jwt);
            res.setMessage("success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        }catch (Exception e){
            res.setMessage("error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getAllReviewByCustomer(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<Review> allReview = reviewService.findAllReviewByCustomer(jwt);
            res.setData(allReview);
            res.setMessage("success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
        }catch (Exception e){
            res.setData(null);
            res.setMessage("error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
    @GetMapping("/{id}/detail")
    public ResponseEntity<EntityResponse> getAllReviewByOrderDetail(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        EntityResponse res = new EntityResponse<>();
        try{
            Review allReviewProduct = reviewService.findByOrderDetail(id);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setData(allReviewProduct);
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EntityResponse> updateReviewByOrderDetail(@PathVariable Long id,@RequestHeader("Authorization") String jwt, @RequestBody Review review){
        EntityResponse res = new EntityResponse<>();
        try{
            Review allReviewProduct = reviewService.updateReview(id,jwt,review);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setData(allReviewProduct);
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

}
