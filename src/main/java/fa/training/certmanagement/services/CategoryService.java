package fa.training.certmanagement.services;

import fa.training.certmanagement.entities.Category;

import java.util.List;

public interface CategoryService {
    /**
     * Retrieves a list of all categories.
     *
     * @return a list of all categories, represented as Category objects.
     */
    List<Category> findAll();

    /**
     * Retrieves a Category object based on the provided ID.
     *
     * @param id the unique identifier of the Category to retrieve
     * @return the Category object with the specified ID, or null if no such Category exists
     */
    Category findById(int id);

    /**
     * Creates a new Category and persists it to the database.
     *
     * @param category the Category object to be created
     * @return the created Category object with its generated ID and persisted state
     */
    Category create(Category category);

    /**
     * Updates an existing category in the system with the provided details.
     *
     * @param category an instance of Category containing updated information,
     *                 including the unique identifier for the category to be updated.
     * @return the updated Category instance after changes are saved.
     */
    Category update(Category category);

    /**
     * Deletes the category with the specified ID.
     *
     * @param id the ID of the category to be deleted
     * @return true if the category was successfully deleted; false otherwise
     */
    boolean delete(int id);
}
