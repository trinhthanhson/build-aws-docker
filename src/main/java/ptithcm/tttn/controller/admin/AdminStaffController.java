package ptithcm.tttn.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.request.SignUpRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import static ptithcm.tttn.function.GenerateOtp.generateOTP;

@RestController
@RequestMapping("/api/admin/staff")
public class AdminStaffController {

    private final UserService userService;
    private final StaffService staffService;

    public AdminStaffController(UserService userService, StaffService staffService) {
        this.userService = userService;
        this.staffService = staffService;
    }


}
