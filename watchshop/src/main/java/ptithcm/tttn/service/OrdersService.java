package ptithcm.tttn.service;

import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.request.StatisticRequest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface OrdersService {

    Orders orderBuyNow(OrderRequest rq, String jwt) throws Exception;

    List<Orders> findByJwtCustomer(String jwt) throws Exception;

    Orders findById(Long id) throws Exception;

    Orders updateStatus(String status, Long id, String jwt) throws Exception;
    Orders updateStatusPayment(String status, Long id) throws Exception;

    Orders orderBuyCart(OrderRequest rq,String jwt) throws Exception;

    List<StatisticRequest> getTotalAmountByMonth(int year);

    List<Orders> findAll();

    List<ProductSaleRequest> getTotalAmountByDate(Date start, Date end);

    Orders updateStatusOrderByStaff(String status, Long id, String jwt) throws Exception;

    Orders orderPaymentBuyNow(OrderRequest rq, String jwt) throws Exception;

    Orders orderPaymentBuyCart(OrderRequest rq, String jwt) throws Exception;

    List<Orders> allOrderReceiveByStaff(String jwt) throws Exception;

    List<StatisticRequest>  getTotalPriceByStatus();

}
