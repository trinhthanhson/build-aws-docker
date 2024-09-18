package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT SUM(quantity*price) FROM OrderDetail WHERE order_id = ?1")
    int totalPriceByOrderId(Long cart_id);

    @Query("SELECT SUM(quantity) FROM OrderDetail WHERE order_id = ?1")
    int totalQuantityByOrderId(Long cart_id);


}
