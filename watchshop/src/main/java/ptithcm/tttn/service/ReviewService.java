package ptithcm.tttn.service;

import ptithcm.tttn.entity.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review, String jwt) throws Exception;
    Review findById(Long id) throws Exception;
    List<Review> findAllReviewByCustomer(String jwt) throws Exception;
    List<Review> findAllReviewByProduct(String product_id);
    Review findByOrderDetail(Long id);
    Review updateReview(Long id, String jwt, Review review) throws Exception;
}
