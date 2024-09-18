package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.CartDetail;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartDetailRepo extends JpaRepository<CartDetail, Long> {
    @Query("SELECT SUM(quantity*price) FROM CartDetail WHERE cart_id = ?1")
     int totalPriceByCartId(Long cart_id);
    @Query("SELECT SUM(quantity) FROM CartDetail WHERE cart_id = ?1")
     int totalQuantityByCartId(Long cart_id);
    @Query( value = "SELECT * FROM cart_detail WHERE cart_id = ?1",nativeQuery =  true)
     List<CartDetail> findCartDetailByCartId(Long cart_id);

    @Query( value = "SELECT * FROM cart_detail WHERE cart_id = ?1 AND product_id = ?2 ",nativeQuery =  true)
    CartDetail findCartDetailByProduct(Long cart_id,String product_id);

    @Modifying
    @Query("UPDATE CartDetail SET quantity = :quantity WHERE cart_id = :cartId AND product_id = :productId")
    void updateQuantity(@Param("cartId") Long cartId, @Param("productId") String productId, int quantity);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartDetail cd WHERE cd.product_id = :product_id AND cd.cart_id = :cart_id")
    void deleteItemCartDetail(String product_id,Long cart_id);

    @Modifying
    @Query("DELETE FROM CartDetail cd WHERE cd.cart_id = :cart_id")
    void deleteCartDetail(Long cart_id);
}
