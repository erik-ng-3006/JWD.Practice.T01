package fa.training.certmanagement.services;

import fa.training.certmanagement.entities.Certificate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import fa.training.certmanagement.repositories.CertificateRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CategoryService categoryService;

    @Override
    public Page<Certificate> findAll(Pageable pageable) {
        return certificateRepository.findAll(pageable);
    }

    @Override
    public Certificate findById(String id) {
        return certificateRepository.findById(id).orElse(null);
    }

    @Override
    public Certificate create(Certificate certificate) {
        // check if certificate is null
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate cannot be null");
        }

        // check if certificate already exists
        if (certificateRepository.findByName(certificate.getName()) != null) {
            throw new IllegalArgumentException("Certificate with name " + certificate.getName() + " already exists");
        }
        // check if category valid
        if (categoryService.findById(certificate.getCategory().getId()) == null) {
            throw new IllegalArgumentException("Category with id " + certificate.getCategory().getId() + " not found");
        }

        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate update(Certificate certificate) {
        // Check if certificate is null
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate cannot be null");
        }

        // Check if certificate name already exists with a different ID
        var certificateByName = certificateRepository.findByName(certificate.getName());
        if (certificateByName != null && !certificate.getId().equals(certificateByName.getId())) {
            throw new IllegalArgumentException("Certificate with name " + certificate.getName() + " already exists");
        }

        // Validate category
        if (certificate.getCategory() == null) {
            throw new IllegalArgumentException("Certificate category is invalid");
        }
        if (categoryService.findById(certificate.getCategory().getId()) == null) {
            throw new IllegalArgumentException("Category with id " + certificate.getCategory().getId() + " not found");
        }

        // Ensure certificate exists before updating
        if (!certificateRepository.existsById(certificate.getId())) {
            throw new IllegalArgumentException("Certificate with id " + certificate.getId() + " does not exist");
        }

        // Save and return the updated certificate
        return certificateRepository.save(certificate);
    }


    @Override
    public boolean delete(String id) {
        // find certificate
        var certificate = certificateRepository.findById(id).orElse(null);

        // check if certificate not found
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate with id " + id + " not found");
        }

        // delete certificate
        certificateRepository.delete(certificate);

        // check if certificate deleted
        return !certificateRepository.existsById(id);
    }

    public long countByCategoryId(int categoryId) {
        return certificateRepository.countByCategoryId(categoryId);
    }
}
