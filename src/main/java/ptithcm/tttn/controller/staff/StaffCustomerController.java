package ptithcm.tttn.controller.staff;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/staff/customer")
public class StaffCustomerController {
    private final CustomerService customerService;

    public StaffCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getAllCustomerByStaff(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse<>();
        try{
            List<Customer> allCustomer = customerService.findAll();
            res.setData(allCustomer);
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

    @GetMapping("/{id}/find")
    public ResponseEntity<EntityResponse> getCustomerByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        EntityResponse res = new EntityResponse<>();
        try{
            Customer customer = customerService.findByUserId(id);
            res.setData(customer);
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
