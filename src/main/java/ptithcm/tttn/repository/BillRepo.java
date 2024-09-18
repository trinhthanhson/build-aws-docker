package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Bill;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
}
