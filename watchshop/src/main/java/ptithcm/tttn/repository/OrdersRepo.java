package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.StatisticRequest;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT * FROM orders WHERE created_by = ?1  ", nativeQuery = true)
    List<Orders> findByCustomerId(Long customer_id);

    @Query("SELECT MONTH(o.created_at) AS month, SUM(o.total_price) AS totalAmount " +
            "FROM Orders o " +
            "WHERE YEAR(o.created_at) = :year AND (o.status = 4 OR o.status = 5) " +
            "GROUP BY MONTH(o.created_at)")
    List<Object[]> getTotalAmountByMonth(int year);

    @Query("SELECT " +
            "    p.product_id AS product_id, " +
            "    p.product_name AS product_name, " +
            "    SUM(od.quantity * od.price) AS total_sold,  " +
            "	 SUM(od.quantity) AS total_quantity,	 " +
            "    DATE(o.updated_at) as date_pay "+
            "FROM " +
            "    Product p " +
            "JOIN " +
            "    OrderDetail od ON p.product_id = od.product.product_id " +
            "JOIN " +
            "    Orders o ON od.order.order_id = o.order_id AND (o.status = 4 OR o.status = 5) " +
            "WHERE " +
            "    DATE(o.created_at) >= DATE(:startDate) " +
            "    AND DATE(o.updated_at) <= DATE(:endDate) " +
            "GROUP BY " +
            "    p.product_id, p.product_name,o.updated_at " +
            "ORDER BY " +
            "    total_sold DESC")
    List<Object[]> getTotalAmountByDate(Date startDate, Date endDate);

    @Query("select sum(o.total_price) as total_price from Orders o where o.status = '5'")
    List<Object[]> findTotalPriceByStatus();

}
