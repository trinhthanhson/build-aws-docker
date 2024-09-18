package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.PriceUpdateDetailRepo;
import ptithcm.tttn.repository.ProductRepo;
import ptithcm.tttn.request.ProductRequest;
import ptithcm.tttn.request.ProductSaleRequest;
import ptithcm.tttn.service.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final StaffService staffService;
    private final ProductRepo productRepo;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final PriceUpdateDetailRepo priceUpdateDetailRepo;

    public ProductServiceImpl(UserService userService, StaffService staffService, ProductRepo productRepo, BrandService brandService, CategoryService categoryService, PriceUpdateDetailRepo priceUpdateDetailRepo) {
        this.userService = userService;
        this.staffService = staffService;
        this.productRepo = productRepo;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.priceUpdateDetailRepo = priceUpdateDetailRepo;
    }

    @Override
    @Transactional
    public Product createProduct(ProductRequest product, String jwt) throws Exception {
        Product create = new Product();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Brand brand = brandService.findByBrandName(product.getBrand_name());
        Category category = categoryService.findCategoryByName(product.getCategory_name());
        String id = generateNewProductId();
        Product save = new Product();
        if(!checkExistProductName(product.getProduct_name())) {
            create.setProduct_name(product.getProduct_name());
            create.setProduct_id(id);
            create.setImage(product.getImage());
            create.setCreated_at(LocalDateTime.now());
            create.setCreated_by(staff.getStaff_id());
            create.setBattery_life(product.getBattery_life());
            create.setAccuracy(product.getAccuracy());
            create.setBrand_id(brand.getBrand_id());
            create.setCategory_id(category.getCategory_id());
            create.setColor(product.getColor());
            create.setDetail(product.getDetail());
            create.setFunc(product.getFunc());
            create.setWeight(product.getWeight());
            create.setWater_resistance(product.getWater_resistance());
            create.setSex(product.getSex());
            create.setQuantity(product.getQuantity());
            create.setTechnology(product.getTechnology());
            create.setMachine(product.getMachine());
            create.setGlass(product.getGlass());
            create.setUpdated_at(LocalDateTime.now());
            create.setUpdated_by(staff.getStaff_id());
            create.setOther_features(product.getOther_features());
            create.setStatus("Active");
            save = productRepo.save(create);
            if (save != null) {
                PriceUpdateDetail priceUpdateDetail = new PriceUpdateDetail();
                priceUpdateDetail.setCreated_at(LocalDateTime.now());
                priceUpdateDetail.setProduct_id(save.getProduct_id());
                priceUpdateDetail.setUpdated_at(LocalDateTime.now());
                priceUpdateDetail.setCreated_by(staff.getStaff_id());
                priceUpdateDetail.setUpdated_by(staff.getStaff_id());
                priceUpdateDetail.setPrice_new(product.getPrice());
                priceUpdateDetail.setPrice_old(product.getPrice());
                priceUpdateDetailRepo.save(priceUpdateDetail);
            }
        }else{
            throw new Exception("exist product by name " + product.getProduct_name());
        }
    return save;
    }

    @Override
    public Product updateProduct(String id, ProductRequest product, String jwt) throws Exception {
        Product find = findById(id);
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Brand brand = brandService.findByBrandName(product.getBrand_name());
        Category category = categoryService.findCategoryByName(product.getCategory_name());
        Product save = new Product();

        if(find != null){
        if(checkExistProductName(product.getProduct_name())) {
            find.setProduct_name(product.getProduct_name());
            find.setImage(product.getImage());
            find.setBattery_life(product.getBattery_life());
            find.setAccuracy(product.getAccuracy());
            find.setBrand_id(brand.getBrand_id());
            find.setCategory_id(category.getCategory_id());
            find.setColor(product.getColor());
            find.setDetail(product.getDetail());
            find.setFunc(product.getFunc());
            find.setWeight(product.getWeight());
            find.setWater_resistance(product.getWater_resistance());
            find.setSex(product.getSex());
            find.setQuantity(product.getQuantity());
            find.setTechnology(product.getTechnology());
            find.setMachine(product.getMachine());
            find.setGlass(product.getGlass());
            find.setUpdated_at(LocalDateTime.now());
            find.setUpdated_by(staff.getStaff_id());
            find.setOther_features(product.getOther_features());
            find.setStatus(product.getStatus());
            PriceUpdateDetail priceUpdateDetail = priceUpdateDetailRepo.findByProductId(id);
            if(product.getPrice() != priceUpdateDetail.getPrice_new()){
                priceUpdateDetail.setPrice_old(priceUpdateDetail.getPrice_new());
                priceUpdateDetail.setPrice_new(product.getPrice());
                priceUpdateDetail.setUpdated_by(staff.getStaff_id());
                priceUpdateDetail.setUpdated_at(LocalDateTime.now());
                priceUpdateDetailRepo.save(priceUpdateDetail);
            }
        }else{
            find.setProduct_name(product.getProduct_name());
        }
        }
       return save = productRepo.save(find);
    }

    @Override
    public boolean checkExistProductName(String name) throws Exception {
        Product find = productRepo.findByName(name);
        if(find != null){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Product deleteProduct(String product_id, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        Product product = findById(product_id);
        product.setStatus("Inactive");
        product.setUpdated_by(staff.getStaff_id());
        return productRepo.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findByName(String name) throws Exception {
        Product find = productRepo.findByName(name);
        if(find != null){
            return find;
        }
        throw new Exception("not found product by name " + name);
    }

    @Override
    public Product findById(String id) throws Exception {
        Optional<Product> find = productRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("not found product by id " + id);

    }

    @Override
    public List<Product> findByDetail(String desc) {
        return productRepo.searchProducts(desc);
    }

    @Override
    public List<Product> findByCategoryId(Long id) {
        return productRepo.findByCategoryId(id);
    }

    @Override
    public List<Product> findByBrandId(Long id) {
        return productRepo.findByBrandId(id);
    }

    private String generateNewProductId() {

        List<Product> products = productRepo.findAll();
        int maxId = 0;
        for (Product p : products) {
            String idStr = p.getProduct_id().substring(2);  // Remove "DH" prefix
            int id = Integer.parseInt(idStr);
            if (id > maxId) {
                maxId = id;
            }
        }
        return String.format("DH%08d", maxId + 1);
    }
    @Override
    public List<ProductSaleRequest> getProductSales() {
        List<Object[]> results = productRepo.getProductSales();
        return results.stream()
                .map(this::mapToProductSaleRequest)
                .collect(Collectors.toList());
    }
    private ProductSaleRequest mapToProductSaleRequest(Object[] result) {
        String productId = (String) result[0];
        String productName = (String) result[1];
        long totalSoldQuantity = (long) result[2];
        long totalQuanity = (long) result[3];
        return new ProductSaleRequest(productId, productName, totalSoldQuantity,totalQuanity,null);
    }
}
