package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Brand;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {

    @Query(value = "SELECT * FROM brand WHERE brand_name = ?1  ", nativeQuery = true)
    Brand findByBrandName(String brand_name);
}
