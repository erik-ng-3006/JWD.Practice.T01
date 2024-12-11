package fa.training.certmanagement.repositories;

import fa.training.certmanagement.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, String> {
    public Certificate findByName(String name);
}
