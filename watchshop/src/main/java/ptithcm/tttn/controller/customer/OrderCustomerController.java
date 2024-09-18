package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.OrderDetailService;
import ptithcm.tttn.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/api/customer/order")
public class OrderCustomerController {
    private final OrdersService ordersService;
    private final OrderDetailService orderDetailService;

    public OrderCustomerController(OrdersService ordersService, OrderDetailService orderDetailService) {
        this.ordersService = ordersService;
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/buy-cart")
    public ResponseEntity<ApiResponse> buyCartByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest rq){
        ApiResponse res = new ApiResponse();
        try{
            Orders orders = ordersService.orderBuyCart(rq,jwt);
            if(orders != null){
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
                res.setMessage("create order buy now success");
            }else{
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("create order buy now fail");
            }
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @PostMapping("/buy-now")
    public ResponseEntity<ApiResponse> buyNowByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody OrderRequest rq){
        ApiResponse res = new ApiResponse();
        try{
            Orders orders = ordersService.orderBuyNow(rq,jwt);
            if(orders != null){
                res.setStatus(HttpStatus.CREATED);
                res.setCode(HttpStatus.CREATED.value());
                res.setMessage("create order buy now success");
            }else{
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("create order buy now fail");
            }
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getALlOrderCustomer(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<Orders> allOrderByJwtCustomer = ordersService.findByJwtCustomer(jwt);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
            res.setData(allOrderByJwtCustomer);
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("error " + e.getMessage());
            res.setData(null);

        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<EntityResponse > getOrderCustomerById(@RequestHeader("Authorization") String jwt,@PathVariable Long id){
        EntityResponse res = new EntityResponse<>();
        try{
            Orders orders = ordersService.findById(id);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setData(orders);
        }catch (Exception e){
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setData(null);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<EntityResponse> cancelOrderByCustomer(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody Orders od){
        EntityResponse res = new EntityResponse<>();
        try{
            Orders orders = ordersService.updateStatus(od.getStatus(),id,jwt);
            res.setData(orders);
            res.setMessage("success");
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setData(null);
            res.setMessage("error " + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());
    }


}
