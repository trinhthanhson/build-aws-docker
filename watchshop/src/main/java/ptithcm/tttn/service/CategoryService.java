package ptithcm.tttn.service;

import ptithcm.tttn.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String category_name, String jwt) throws Exception;

    List<Category> findAll();

    Category updateCategory(Long id, String category_name, String jwt) throws Exception;

    Category findById(Long id) throws Exception;

    Category findCategoryByName(String name) throws Exception;

    Category deleteCategory(Long id, String jwt) throws Exception;

    boolean checkExitsCategory(String name);
}
