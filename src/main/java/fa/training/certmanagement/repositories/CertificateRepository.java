package fa.training.certmanagement.repositories;

import fa.training.certmanagement.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CertificateRepository extends JpaRepository<Certificate, String>, JpaSpecificationExecutor<Certificate> {
    Certificate findByName(String name);

    long countByCategoryId(int categoryId);
}
