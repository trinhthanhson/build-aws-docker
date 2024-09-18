package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.PriceUpdateDetail;

@Repository
public interface PriceUpdateDetailRepo extends JpaRepository<PriceUpdateDetail, Long> {
    @Query(value = "SELECT * FROM price_update_detail WHERE product_id = ?1  ", nativeQuery = true)
    PriceUpdateDetail findByProductId(String product_id);
}
