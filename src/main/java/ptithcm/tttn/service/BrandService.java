package ptithcm.tttn.service;

import ptithcm.tttn.entity.Brand;

import java.sql.SQLException;
import java.util.List;

public interface BrandService {

    Brand findBrandById(Long id) throws SQLException;
    Brand createBrand(Brand brand, String jwt) throws Exception;
    List<Brand> findAll();
    Brand updateBrand(Long id, Brand brand, String jwt) throws Exception;
    Brand findByBrandName(String brandName) throws Exception;

    boolean checkExistBrand(String name);

    Brand deleteBrand(Long id, String jwt) throws Exception;
}
