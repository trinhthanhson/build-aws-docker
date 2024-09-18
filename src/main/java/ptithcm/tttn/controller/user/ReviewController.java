package ptithcm.tttn.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Review;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/user/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<ListEntityResponse> getAllReviewByProduct(@PathVariable String id){
        ListEntityResponse res = new ListEntityResponse<>();
        try{
            List<Review> allReviewProduct = reviewService.findAllReviewByProduct(id);
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
