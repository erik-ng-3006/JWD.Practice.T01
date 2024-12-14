package fa.training.certmanagement.services;

import fa.training.certmanagement.entities.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CertificateService {
    /**
     * Retrieves a paginated list of all Certificate objects.
     *
     * @return a Page containing Certificate objects
     */
    Page<Certificate> findAll(Pageable pageable);

    /**
     * Retrieves a Certificate object based on its unique identifier.
     *
     * @param id the unique identifier of the Certificate to retrieve
     * @return the Certificate object with the specified identifier, or null if no such Certificate exists
     */
    Certificate findById(String id);

    /**
     * Creates a new Certificate and persists it to the database.
     *
     * @param certificate the Certificate object to be created
     * @return the created Certificate object with its generated ID and persisted state
     */
    Certificate create(Certificate certificate);

    /**
     * Updates an existing Certificate in the system with the details provided.
     *
     * @param certificate the Certificate object containing updated information,
     *                    including the unique identifier for the Certificate to be updated
     * @return the updated Certificate object after the changes are saved
     */
    Certificate update(Certificate certificate);

    /**
     * Deletes the certificate with the specified ID.
     *
     * @param id the unique identifier of the certificate to be deleted
     * @return true if the certificate was successfully deleted; false otherwise
     */
    boolean delete(String id);
}
