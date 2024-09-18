package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.PaymentResponse;
import ptithcm.tttn.service.OrdersService;
import ptithcm.tttn.service.VNPayService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer/payment")
public class CustomerPaymentController {


    private final VNPayService vnPayService;
    private final OrdersService ordersService;

    public CustomerPaymentController(VNPayService vnPayService, OrdersService ordersService) {
        this.vnPayService = vnPayService;
        this.ordersService = ordersService;
    }

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitOrder(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        // Extract details from the request body
        ApiResponse res = new ApiResponse();
        Orders orders = ordersService.orderPaymentBuyNow(orderRequest,jwt);
        if(orders != null){
            int orderTotal = orders.getTotal_price();
            String orderInfo = orders.getAddress();
            // Construct base URL
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            // Generate VNPay URL
            String vnpayUrl = vnPayService.createOrder(orderTotal, orders.getOrder_id(), orderInfo, baseUrl);
            res.setMessage(vnpayUrl);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            return new ResponseEntity<>(res,res.getStatus());

        }
        return ResponseEntity.status(302).header("Location", null).build();

        // Redirect the client to the VNPay URL
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse> submitOrderCart(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        // Extract details from the request body
        ApiResponse res = new ApiResponse();
        Orders orders = ordersService.orderPaymentBuyCart(orderRequest,jwt);
        if(orders != null){
            int orderTotal = orders.getTotal_price();
            String orderInfo = orders.getAddress();
            // Construct base URL
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            // Generate VNPay URL
            String vnpayUrl = vnPayService.createOrder(orderTotal,orders.getOrder_id(), orderInfo, baseUrl);
            res.setMessage(vnpayUrl);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            return new ResponseEntity<>(res,res.getStatus());

        }
        return ResponseEntity.status(302).header("Location", null).build();

        // Redirect the client to the VNPay URL
    }

    @GetMapping("/vnpay-payment")
    public RedirectView handlePaymentReturn(HttpServletRequest request) throws Exception {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String order_id = request.getParameter("vnp_TxnRef");
        Long orderId = Long.parseLong(order_id);

        // If payment is successful
        if (paymentStatus == 1) {
            Orders orders = ordersService.updateStatusPayment("4", orderId);
            return new RedirectView("http://localhost:5173/orders-history");
        } else {
            // Handle failure case, possibly redirect to a failure page
            return new RedirectView("http://localhost:5173/payment-failure");
        }
    }

}
