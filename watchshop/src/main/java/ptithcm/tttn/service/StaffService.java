package ptithcm.tttn.service;

import ptithcm.tttn.entity.Staff;

import java.sql.SQLException;

public interface StaffService {

    Staff findByUserId(Long user_id) throws SQLException;

    boolean checkEmailExist(String email);

    Staff updateProfileStaff(String jwt, Staff staff) throws Exception;
}
