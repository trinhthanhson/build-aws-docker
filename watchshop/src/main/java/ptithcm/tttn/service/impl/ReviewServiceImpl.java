package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.Review;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.ReviewRepo;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.ReviewService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final UserService userService;
    private final CustomerService customerService;

    public ReviewServiceImpl(ReviewRepo reviewRepo, UserService userService, CustomerService customerService) {
        this.reviewRepo = reviewRepo;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public Review createReview(Review review, String jwt) throws Exception {
        Review create = new Review();
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        create.setCreated_by(customer.getCustomer_id());
        create.setCreated_at(LocalDateTime.now());
        create.setContent(review.getContent());
        create.setProduct_id(review.getProduct_id());
        create.setStar(review.getStar());
        create.setUpdated_at(LocalDateTime.now());
        create.setUpdated_by(customer.getCustomer_id());
        create.setOrder_detail_id(review.getOrder_detail_id());
        Review save = reviewRepo.save(create);
        if(save != null){
            return save;
        }
        throw new Exception("create review fail");

    }

    @Override
    public Review findById(Long id) throws Exception {
        Optional<Review> review = reviewRepo.findById(id);
        if(review.isPresent()){
            return review.get();
        }
        throw new Exception("Not found review by id " + id);
    }

    @Override
    public List<Review> findAllReviewByCustomer(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        List<Review> findAllByCustomer = reviewRepo.findAllReviewByCustomer(customer.getCustomer_id());
        return findAllByCustomer;
    }

    @Override
    public List<Review> findAllReviewByProduct(String product_id) {
        return reviewRepo.findAllReviewByProduct(product_id);
    }

    @Override
    public Review findByOrderDetail(Long id) {
        return reviewRepo.findReviewByOrderDetail(id);
    }

    @Override
    @Transactional
    public Review updateReview(Long id, String jwt, Review review) throws Exception {
        Review find = findByOrderDetail(id);
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        find.setStar(review.getStar());
        find.setContent(review.getContent());
        find.setUpdated_by(customer.getCustomer_id());
        find.setUpdated_at(LocalDateTime.now());
        return reviewRepo.save(find);
    }
}
