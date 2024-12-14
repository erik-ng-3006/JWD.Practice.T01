package fa.training.certmanagement.services;

import fa.training.certmanagement.entities.Certificate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fa.training.certmanagement.repositories.CertificateRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CategoryService categoryService;

    @Override
    public List<Certificate> findAll() {
        return certificateRepository.findAll();
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
        // check if certificate is null
        if (certificate == null) {
            throw new IllegalArgumentException("Certificate cannot be null");
        }
        //check if certificate name already exists with other id
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
}
