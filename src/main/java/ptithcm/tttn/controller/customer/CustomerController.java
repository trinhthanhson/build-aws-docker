package ptithcm.tttn.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Category;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PutMapping("/update-info")
    public ResponseEntity<ApiResponse> updateInfoByCustomer(@RequestHeader("Authorization") String jwt, @RequestBody Customer customer){
        ApiResponse res = new ApiResponse();
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        try{
            Customer saveCustomer = customerService.updateCustomer(customer,jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
            httpStatus = HttpStatus.CREATED;
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("erorr " + e.getMessage());
        }
        return new ResponseEntity<>(res,httpStatus);
    }
}
