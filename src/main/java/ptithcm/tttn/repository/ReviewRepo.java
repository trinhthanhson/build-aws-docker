package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review WHERE created_by = ?1  ", nativeQuery = true)
    List<Review> findAllReviewByCustomer(Long customer_id);

    @Query(value = "SELECT * FROM review WHERE product_id = ?1  ", nativeQuery = true)
    List<Review> findAllReviewByProduct(String product_id);

    @Query(value = "SELECT * FROM review WHERE order_detail_id = ?1  ", nativeQuery = true)
    Review findReviewByOrderDetail(Long order_detail_id);
}
