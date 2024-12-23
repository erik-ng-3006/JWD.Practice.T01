package fa.training.certmanagement.controllers;

import fa.training.certmanagement.entities.Category;
import fa.training.certmanagement.entities.Certificate;
import fa.training.certmanagement.services.CertificateService;
import fa.training.certmanagement.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final CategoryService categoryService;
    private final CertificateService certificateService;

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        populateModel(model, page, size, new Certificate());
        return "home/index";
    }

    @PostMapping("/certificates/save")
    public String saveOrUpdateCertificate(
            @ModelAttribute("certificate") @Valid Certificate certificate,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            populateModel(model, 0, 5, certificate);
            return "home/index";
        }

        try {
            if (certificate.getId() == null || certificate.getId().isEmpty()) {
                // Create a new certificate
                Certificate result = certificateService.create(certificate);
                redirectAttributes.addFlashAttribute("success",
                        "Certificate " + result.getName() + " has been created successfully!");
            } else {
                // Update an existing certificate
                Certificate updatedCertificate = certificateService.update(certificate);
                redirectAttributes.addFlashAttribute("success",
                        "Certificate " + updatedCertificate.getName() + " has been updated successfully!");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/";
    }


    @GetMapping("/certificates/delete/{id}")
    public String deleteCertificate(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            if (certificateService.delete(id)) {
                redirectAttributes.addFlashAttribute("success", "Certificate with id " + id + " has been deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Certificate with id " + id + " not found!");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/certificates/update/{id}")
    public String updateCertificate(@PathVariable String id, Model model) {
        Certificate certificate = certificateService.findById(id);
        if (certificate == null) {
            model.addAttribute("error", "Certificate not found!");
            return "redirect:/";
        }

        populateModel(model, 0, 5, certificate);
        return "home/index";
    }

    // Helper method to populate model with common attributes
    private void populateModel(Model model, int page, int size, Certificate certificate) {
        // Pagination
        Pageable pageable = PageRequest.of(page, size);

        // Get categories and certificates
        List<Category> categories = categoryService.findAll();
        Page<Certificate> certificates = certificateService.findAll(pageable);

        // Calculate page numbers
        List<Integer> pageNumbers = IntStream
                .range(Math.max(page - 3, 0), Math.min(page + 3 + 1, certificates.getTotalPages()))
                .boxed()
                .toList();

        // Classify
        List<Map<String, Serializable>> classifiedCertificates = categoryService.findAll().stream()
                .map(category -> {
                    Map<String, Serializable> classification = new HashMap<>();
                    classification.put("category", category.getName());
                    classification.put("count", certificateService.countByCategoryId(category.getId()));
                    return classification;
                })
                .toList();

        // Set model attributes
        model.addAttribute("categories", categories);
        model.addAttribute("certificates", certificates.getContent());
        model.addAttribute("totalPages", certificates.getTotalPages());
        model.addAttribute("totalElements", certificates.getTotalElements());
        model.addAttribute("currentPage", certificates.getNumber());
        model.addAttribute("certificate", certificate);
        model.addAttribute("classifiedCertificates", classifiedCertificates);
        model.addAttribute("pageNumbers", pageNumbers);

    }
}
