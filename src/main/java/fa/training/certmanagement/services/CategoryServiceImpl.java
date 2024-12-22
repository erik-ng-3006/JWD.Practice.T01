package fa.training.certmanagement.services;

import fa.training.certmanagement.entities.Category;
import fa.training.certmanagement.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category create(Category category) {
        // check if category is null
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        // check if category already exists
        Category categoryByName = categoryRepository.findByName(category.getName());
        if (categoryByName != null) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        // save category
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        // check if category is null
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        // check if category already exists
        Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);
        if (foundCategory == null) {
            throw new IllegalArgumentException("Category with id " + category.getId() + " not found");
        }
        // check if category name already exists with a different ID
        Category categoryByName = categoryRepository.findByName(category.getName());
        if (categoryByName != null && category.getId() != categoryByName.getId()) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }

        // save category
        return categoryRepository.save(category);
    }

    @Override
    public boolean delete(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Category with id " + id + " not found");
        }

        categoryRepository.delete(category);

        return !categoryRepository.existsById(id);
    }
}
