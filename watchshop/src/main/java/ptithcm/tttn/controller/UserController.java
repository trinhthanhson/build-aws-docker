package ptithcm.tttn.controller;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.ProductRepo;
import ptithcm.tttn.request.ChangePasswordRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final CustomerService customerService;
    private final StaffService staffService;
    private final ReviewService reviewServicep;

    public UserController(UserService userService, CustomerService customerService, StaffService staffService, ReviewService reviewServicep) {
        this.userService = userService;
        this.customerService = customerService;
        this.staffService = staffService;
        this.reviewServicep = reviewServicep;
    }

    @GetMapping("/customer/all")
    public ResponseEntity<ListEntityResponse> findAllUser(@RequestHeader("Authorization") String jwt) throws Exception {
        ListEntityResponse res = new ListEntityResponse();
        try{
            List<User> getAllUer = userService.findAll();
            List<User> userCustomer = new ArrayList<>();
            for(User u : getAllUer){
                if(!(u.getRole().getRole_name().equals("STAFF")) && (!(u.getRole().getRole_name().equals("ADMIN")))){
                    userCustomer.add(u);
                }
            }

            res.setData(userCustomer);
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


        @GetMapping("/find")
    public ResponseEntity<EntityResponse> findCustomerAndStaffProfileByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        EntityResponse res = new EntityResponse();
        try{
            if(user.getRole().getRole_name().equals("CUSTOMER")) {
                Customer customer = customerService.findByUserId(user.getUser_id());
                res.setData(customer);
                res.setMessage("find customer success");
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
            }else if(user.getRole().getRole_name().equals("ADMIN") || user.getRole().getRole_name().equals("MANAGER") || user.getRole().getRole_name().equals("STAFF") || user.getRole().getRole_name().equals("SHIPPER")) {
                Staff staff = staffService.findByUserId(user.getUser_id());
                res.setData(staff);
                res.setMessage("find staff success");
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
            }else {
                res.setData(null);
                res.setMessage("not found");
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
            }
        }catch(Exception e){
            res.setData(null);
            res.setMessage("Error " + e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
        }

        return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePasswordByUser(@RequestHeader("Authorization") String jwt,@RequestBody ChangePasswordRequest user){
        ApiResponse res = new ApiResponse();
        try{
            User update = userService.changePassword(jwt, user);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
            res.setStatus(HttpStatus.OK);
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("Error: " +e.getMessage());
            res.setStatus(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res,res.getStatus());

    }
}
