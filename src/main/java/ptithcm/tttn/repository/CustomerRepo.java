package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer WHERE user_id = ?1  ", nativeQuery = true)
    Customer findByUserId(Long user_id);


    @Query(value = "SELECT * FROM customer WHERE email = ?1  ", nativeQuery = true)
    Customer findByEmail(String email);
}
