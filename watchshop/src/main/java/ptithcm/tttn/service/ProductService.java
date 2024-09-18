package ptithcm.tttn.service;

import ptithcm.tttn.entity.Product;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.request.ProductSaleRequest;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findByName(String name) throws Exception;

    Product findById(String id) throws Exception;

    List<Product> findByDetail(String desc);

    List<Product> findByCategoryId(Long id);

    List<Product> findByBrandId(Long id);

    Product createProduct(ProductRequest product, String jwt) throws Exception;

    Product updateProduct(String id, ProductRequest product, String jwt) throws Exception;

    boolean checkExistProductName(String name) throws Exception;

    Product deleteProduct(String product_id, String jwt) throws Exception;

    List<ProductSaleRequest> getProductSales();

}
