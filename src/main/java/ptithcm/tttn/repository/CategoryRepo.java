package ptithcm.tttn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ptithcm.tttn.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category WHERE category_name = ?1  ", nativeQuery = true)
    Category findCategoryByName(String category_name);
}
