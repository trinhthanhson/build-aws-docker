package ptithcm.tttn.controller;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.config.JwtTokenProvider;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.Product;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.request.SignInRequest;
import ptithcm.tttn.request.SignUpRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.response.AuthResponse;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;
import ptithcm.tttn.service.impl.UserDetailsServiceImpl;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ptithcm.tttn.function.GenerateOtp.generateOTP;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthController(UserService userService, UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequest rq){
        ApiResponse res = new ApiResponse();
        HttpStatus httpStatus = null;
        if(!rq.getEmail().equals("") && !rq.getLastname().equals("") && !rq.getFirstname().equals("") && !rq.getPassword().equals("") && !rq.getUsername().equals("") && !rq.getRole_name().equals("")) {
        try{
            User user = userService.createUser(rq);
            res.setCode(HttpStatus.CREATED.value());
            res.setMessage("Create user success");
            res.setStatus(HttpStatus.CREATED);
            httpStatus = HttpStatus.CREATED;
        }catch (Exception e){
            System.out.println("error" + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("error" + e.getMessage());
            httpStatus = HttpStatus.CONFLICT;
        }
        }else {
            httpStatus = HttpStatus.CONFLICT;
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("No blank characters allowed");
            res.setStatus(httpStatus);
        }
        return new ResponseEntity<>(res,httpStatus);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest rq){
        AuthResponse res = new AuthResponse();
        if(!rq.getUsername().equals("") && !rq.getPassword().equals("")){
            String username = rq.getUsername();
            String password = rq.getPassword();
            Authentication authentication = authenticate(username,password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String tokenSevenday = jwtTokenProvider.generateAccessToken(authentication);
            String tokenThirtyDay = jwtTokenProvider.generateRefreshToken(authentication);
            LocalDateTime currentTime = LocalDateTime.now();
            // Cộng thêm 7 ngày
            LocalDateTime expiredAccressToken = currentTime.plus(7, ChronoUnit.DAYS);
            LocalDateTime expiredRefreshToken = currentTime.plus(30, ChronoUnit.DAYS);
            // Chuyển LocalDateTime thành Timestamp
            Timestamp expiredAccressTokenTimestamp = Timestamp.valueOf(expiredAccressToken);
            Timestamp expiredRefreshTokenTimestamp = Timestamp.valueOf(expiredRefreshToken);
            // Create new user
            res.setStatus(true);
            res.setToken(tokenSevenday);
            User user = userService.findByUsername(username);
            user.setAccessToken(tokenThirtyDay);
            user.setRefreshToken(tokenThirtyDay);
            user.setExpiredAccessToken(expiredAccressTokenTimestamp);
            user.setExpiredRefresh(expiredRefreshTokenTimestamp);
            userService.signIn(user);
            System.err.println(authentication);
        }else{
            res.setStatus(false);
            res.setToken("No blank characters allowed");
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@RequestHeader("Authorization") String jwt) throws  Exception {
        String newtoken = jwtTokenProvider.invalidateToken(jwt);
        User user = userService.findUserByJwt(jwt);
        user.setAccessToken(newtoken);
        user.setRefreshToken(newtoken);
        user.setExpiredAccessToken(Timestamp.valueOf(LocalDateTime.now()));
        user.setExpiredRefresh(Timestamp.valueOf(LocalDateTime.now()));
        userService.signIn(user);
        AuthResponse res = new AuthResponse();
        res.setStatus(true);
        res.setToken(newtoken);
        return new ResponseEntity<AuthResponse>(res, HttpStatus.OK);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgetPassword(@RequestBody SignUpRequest rq) throws Exception {
        ApiResponse res = new ApiResponse();
        String otp = generateOTP();
        String subject = "Xác Minh Địa Chỉ Email để đổi mật khẩu - Mật khẩu";
        String content = "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Khôi Phục Mật Khẩu</title>"
                + "    <style>"
                + "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + "        .container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #dddddd; border-radius: 5px; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + "        .header { text-align: center; margin-bottom: 20px; }"
                + "        .header h1 { color: #333333; font-size: 24px; margin: 0; }"
                + "        .content { font-size: 16px; color: #333333; line-height: 1.5; }"
                + "        .otp-code { display: block; font-size: 24px; font-weight: bold; color: #007bff; text-align: center; margin: 20px 0; }"
                + "        .footer { font-size: 14px; color: #888888; text-align: center; margin-top: 20px; }"
                + "        .footer a { color: #007bff; text-decoration: none; }"
                + "        .footer a:hover { text-decoration: underline; }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <div class=\"header\">"
                + "            <h1>Khôi Phục Mật Khẩu</h1>"
                + "        </div>"
                + "        <div class=\"content\">"
                + "            <p>Chào <strong>" + rq.getEmail() + "</strong>,</p>"
                + "            <p>Chúng tôi đã nhận được yêu cầu khôi phục mật khẩu từ bạn. Dưới đây là mật khẩu mới của bạn. Vui lòng đăng nhập với mã ở bên dưới để tiếp tục sử dụng</p>"
                + "            <span class=\"otp-code\">" + otp + "</span>"
                + "            <p>Nếu bạn gặp bất kỳ vấn đề nào hoặc cần hỗ trợ thêm, đừng ngần ngại liên hệ với chúng tôi qua địa chỉ email này hoặc gọi điện thoại cho chúng tôi tại <strong>0363000451</strong>.</p>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>Trân trọng,</p>"
                + "            <p><strong>WATCHSHOP</strong><br>"
                + "            <a href=\"mailto:sontrinh2507@gmail.com\">sontrinh2507@gmail.com</a><br>"
                + "            <a href=\"#\">WATCHSHOP</a></p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        boolean checkEmail = customerService.checkEmailExist(rq.getEmail());

        if (checkEmail) {
            userService.sendMail(rq.getEmail(), subject, content, otp);
            userService.updatePassword(otp, rq.getEmail());
            res.setMessage("success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }else{
            res.setMessage("Email not exist");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }

        return new ResponseEntity<>(res, res.getStatus());
    }


    @PostMapping("/sent-otp")
    public ResponseEntity<ApiResponse> sentOptByEmail(@RequestBody SignUpRequest rq) {
        ApiResponse res = new ApiResponse();
        User existUsername = userService.findByUsername(rq.getUsername());
        boolean checkEmail = customerService.checkEmailExist(rq.getEmail());
        String otp = generateOTP();
        String subject = "Xác Minh Địa Chỉ Email để đăng ký tài khoản - Mã OTP";
        String content = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Xác Minh Địa Chỉ Email - Mã OTP</title>"
                + "    <style>"
                + "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + "        .container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #dddddd; border-radius: 5px; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + "        .header { text-align: center; margin-bottom: 20px; }"
                + "        .header h1 { color: #333333; font-size: 24px; margin: 0; }"
                + "        .content { font-size: 16px; color: #333333; line-height: 1.5; }"
                + "        .otp-code { display: block; font-size: 24px; font-weight: bold; color: #007bff; text-align: center; margin: 20px 0; }"
                + "        .footer { font-size: 14px; color: #888888; text-align: center; margin-top: 20px; }"
                + "        .footer a { color: #007bff; text-decoration: none; }"
                + "        .footer a:hover { text-decoration: underline; }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <div class=\"header\">"
                + "            <h1>Xác Minh Địa Chỉ Email</h1>"
                + "        </div>"
                + "        <div class=\"content\">"
                + "            <p>Chào <strong>" + rq.getEmail() + "</strong>,</p>"
                + "            <p>Cảm ơn bạn đã đăng ký tài khoản trên <strong>[WATCHSHOP]</strong>. Để hoàn tất quá trình đăng ký, vui lòng nhập mã OTP (Mã Một Lần) dưới đây vào trang xác minh của chúng tôi.</p>"
                + "            <span class=\"otp-code\">" + otp + "</span>"
                + "            <p> Nếu bạn không yêu cầu mã OTP này, vui lòng bỏ qua email này và mã OTP sẽ không còn hiệu lực.</p>"
                + "            <p>Nếu bạn gặp bất kỳ vấn đề nào hoặc cần hỗ trợ thêm, đừng ngần ngại liên hệ với chúng tôi qua địa chỉ email này hoặc gọi điện thoại cho chúng tôi tại <strong>[0363000451]</strong>.</p>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>Trân trọng,</p>"
                + "            <p><strong>WATCHSHOP</strong><br>"
                + "            <a href=\"mailto: sontrinh2507@gmail.com\">sontrinh2507@gmail.com</a><br>"
                + "            <a href=\"WATCHSHOP\"> WATCHSHOP</a></p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        if(existUsername != null){
            res.setMessage("username exist");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }else if(checkEmail){
            res.setMessage("email exist");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }else {
            try {

                userService.sendMail(rq.getEmail(),subject,content,otp);
                res.setMessage("success");
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
            } catch (Exception e) {
                res.setMessage("fail");
                res.setStatus(HttpStatus.CONFLICT);
                res.setCode(HttpStatus.CONFLICT.value());
            }
        }
        return new ResponseEntity<>(res,res.getStatus());
    }



    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
