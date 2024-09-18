package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.StaffRepo;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepo staffRepo;
    private final UserService userService;

    public StaffServiceImpl(StaffRepo staffRepo, UserService userService) {
        this.staffRepo = staffRepo;
        this.userService = userService;
    }

    @Override
    public Staff findByUserId(Long user_id) throws SQLException {
        try{
            return staffRepo.findByUserId(user_id);
        }
       catch (Exception e){
           throw new SQLException("Error finding staff by user ID: " + user_id, e);
       }
    }


    @Override
    public boolean checkEmailExist(String email) {
        Staff user = staffRepo.findByEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Staff updateProfileStaff(String jwt, Staff staff) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff find = findByUserId(user.getUser_id());
        find.setBirthday(staff.getBirthday());
        find.setCitizen_id(staff.getCitizen_id());
        find.setUpdated_at(LocalDateTime.now());
        find.setEmail(staff.getEmail());
        find.setTax_id(staff.getTax_id());
        find.setPhone(staff.getPhone());
        find.setGender(staff.getGender());
        find.setFirst_name(staff.getFirst_name());
        find.setLast_name(staff.getLast_name());
        find.setAddress(staff.getAddress());
        return staffRepo.save(find);
    }
}
