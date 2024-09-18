package ptithcm.tttn.controller.staff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.ValueResponse;
import ptithcm.tttn.service.OrdersService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/staff/order")
public class StaffOrderController {

    private final OrdersService ordersService;

    public StaffOrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getAllOrderByStaff(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse<>();
        try{
            List<Orders> getAllOrder = ordersService.findAll();
            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<EntityResponse> cancelOrderByCustomer(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody Orders od){
        EntityResponse res = new EntityResponse<>();
        try{
            Orders orders = ordersService.updateStatusOrderByStaff(od.getStatus(),id,jwt);
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

    @GetMapping("/value")
    public ResponseEntity<ValueResponse> getValueOrder(@RequestHeader("Authorization") String jwt){
        ValueResponse res = new ValueResponse();
        try{
            List<Orders> getAllOrder = ordersService.findAll();
            Integer total = 0;
            for(Orders od : getAllOrder){
                total+=od.getTotal_price();
            }
            res.setValue(total);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setValue(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }


    @GetMapping("/all/shipper")
    public ResponseEntity<ListEntityResponse> getAllOrderByStaffShipper(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse<>();
        try{
            List<Orders> getAllOrder = ordersService.allOrderReceiveByStaff(jwt);

            res.setData(getAllOrder);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setData(null);
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }

}
