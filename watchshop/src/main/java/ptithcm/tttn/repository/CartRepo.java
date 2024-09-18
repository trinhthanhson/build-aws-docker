package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    @Query(value =  "SELECT * FROM cart WHERE customer_id = ?1",nativeQuery =  true)
    Cart findCartByJwtCustomer(Long customer_id);
}
