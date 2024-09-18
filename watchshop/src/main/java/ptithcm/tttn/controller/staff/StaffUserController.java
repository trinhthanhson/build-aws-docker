package ptithcm.tttn.controller.staff;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.EntityResponse;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/staff/user")
public class StaffUserController {
    private final UserService userService;
    private final StaffService staffService;

    public StaffUserController(UserService userService, StaffService staffService) {
        this.userService = userService;
        this.staffService = staffService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse> getAllUserCustomerByStaff(@RequestHeader("Authorization") String jwt){
        ListEntityResponse res = new ListEntityResponse<>();
        try{
            List<User> findAll = userService.findAll();
            List<User> allUserCustomer = new ArrayList<>();
            for(User u : findAll){
                if(u.getRole().getRole_name().equals("CUSTOMER")){
                    allUserCustomer.add(u);
                }
            }
            res.setData(allUserCustomer);
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
    public ResponseEntity<EntityResponse> getUserCustomerByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id){
        EntityResponse res = new EntityResponse<>();
        try{
            User user = userService.findById(id);
            res.setData(user);
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

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateStatusCustomerByStaff(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody User user) {
        ApiResponse res = new ApiResponse();
        try {
            User update = userService.updateStatus(id, user.getStatus(), jwt);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/profile/update")
    public ResponseEntity<EntityResponse> updateProfileStaff(@RequestHeader("Authorization") String jwt, @RequestBody Staff staff){
        EntityResponse res = new EntityResponse<>();
        try{
            Staff update = staffService.updateProfileStaff(jwt,staff);
            res.setData(update);
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
