package fa.training.certmanagement.repositories;

import fa.training.certmanagement.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findByName(String name);
}
