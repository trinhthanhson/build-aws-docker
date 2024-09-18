package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {
    @Query(value = "SELECT * FROM staff WHERE user_id = ?1  ", nativeQuery = true)
    Staff findByUserId(Long user_id);

    @Query(value = "SELECT * FROM staff WHERE email = ?1  ", nativeQuery = true)
    Staff findByEmail(String email);
}
