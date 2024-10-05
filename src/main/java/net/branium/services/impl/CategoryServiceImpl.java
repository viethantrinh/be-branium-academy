package net.branium.services.impl;

import lombok.RequiredArgsConstructor;
import net.branium.domains.Category;
import net.branium.dtos.category.CategoryResponse;
import net.branium.mappers.CategoryMapper;
import net.branium.repositories.CategoryRepository;
import net.branium.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryResponse> response = categories
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
        return response;
    }
}
