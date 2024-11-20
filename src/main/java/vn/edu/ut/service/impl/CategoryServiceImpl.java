package vn.edu.ut.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.ut.entity.Category;
import vn.edu.ut.enums.ErrorCode;
import vn.edu.ut.exception.AppApiException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.CategoryDto;
import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.repository.CategoryRepository;
import vn.edu.ut.service.ICategoryService;
import vn.edu.ut.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryRequest) {
        checkNameAndSlugCategory(categoryRequest);
        Category category = modelMapper.map(categoryRequest, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> categories;
        if (StringUtils.isNotEmpty(keyword)) {
            categories = categoryRepository.search(keyword, pageable);
        } else {
            categories = categoryRepository.findAll(pageable);
        }
        List<Category> listCategories = categories.getContent();
        List<CategoryDto> content = listCategories.stream().map(category ->
                modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        return ClassResponse.convertToClassResponse(categories, content);
    }

    @Override
    public CategoryDto get(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(Integer categoryId, CategoryDto categoryRequest) {
        Category categoryInDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        checkNameAndSlugCategory(categoryRequest);
        categoryInDB.setName(categoryRequest.getName());
        categoryInDB.setSlug(categoryRequest.getSlug());
        Category updatedCategory = categoryRepository.save(categoryInDB);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(Integer categoryId) {
        Category categoryInDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(categoryInDB);
    }

    private void checkNameAndSlugCategory(CategoryDto categoryRequest) {
        String categoryName = categoryRequest.getName();
        String slug = Utils.removeVietnameseAccents(categoryName);
        Optional<Category> categoryCheckDuplicate = categoryRepository.findByNameOrSlug(categoryName, slug);

        categoryCheckDuplicate.filter(c -> c.getName().equals(categoryName)).ifPresent(t -> {
            throw new AppApiException(ErrorCode.CATEGORY_NAME_EXISTED);
        });
        categoryCheckDuplicate.filter(c -> c.getSlug().equals(slug)).ifPresent(t -> {
            throw new AppApiException(ErrorCode.CATEGORY_SLUG_EXISTED);
        });
        categoryRequest.setSlug(slug);
    }
}
