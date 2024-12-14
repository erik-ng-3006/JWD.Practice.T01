package fa.training.certmanagement.controllers;

import fa.training.certmanagement.entities.Category;
import fa.training.certmanagement.services.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import fa.training.certmanagement.services.CategoryService;
import fa.training.certmanagement.entities.Certificate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        Pageable pageable = PageRequest.of(page, size);
        List<Category> categories = categoryService.findAll();
        Page<Certificate> certificates = certificateService.findAll(pageable);

        model.addAttribute("categories", categories);
        model.addAttribute("certificates", certificates.getContent());
        model.addAttribute("totalPages", certificates.getTotalPages());
        model.addAttribute("totalElements", certificates.getTotalElements());
        model.addAttribute("currentPage", certificates.getNumber());

        // create new certificate
        Certificate certificate = new Certificate();

        // pass certificate to view
        model.addAttribute("certificate", certificate);
        return "home/index";
    }

    @PostMapping("/certificates/save")
    public String saveCertificate(@ModelAttribute("certificate") @Valid Certificate certificate,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            Pageable pageable = PageRequest.of(0, 5);
            // Re-fetch categories and certificates for the view
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("certificates", certificateService.findAll(pageable));

            // Return the form with error messages
            return "home/index";
        }

        try {
            Certificate result = certificateService.create(certificate);
            redirectAttributes.addFlashAttribute("success", "Certificate " + result.getName() + " has been created successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/certificates/delete/{id}")
    public String deleteCertificate(@PathVariable String id, RedirectAttributes redirectAttributes) {
        boolean result = false;

        try {
            result = certificateService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Certificate with id " + id + " has been deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "home/index";
        }

        if (!result) {
            redirectAttributes.addFlashAttribute("error", "Certificate with id " + id + " not found!");
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
        Pageable pageable = PageRequest.of(0, 5);

        List<Category> categories = categoryService.findAll();
        Page<Certificate> certificates = certificateService.findAll(pageable);

        model.addAttribute("certificate", certificate); // Pre-fill form
        model.addAttribute("categories", categories);
        model.addAttribute("certificates", certificates);

        return "home/index";
    }

    @PostMapping("/certificates/update")
    public String updateCertificate(@ModelAttribute("certificate") @Valid Certificate certificate,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes, Model model) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            Pageable pageable = PageRequest.of(0, 5);
            // Re-fetch categories and certificates for the view
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("certificates", certificateService.findAll(pageable));

            // Return to the home page with errors displayed
            return "home/index";
        }

        try {
            // Call the service layer to update the certificate
            Certificate updatedCertificate = certificateService.update(certificate);

            // Add a success message to the redirect attributes
            redirectAttributes.addFlashAttribute("success",
                    "Certificate " + updatedCertificate.getName() + " has been updated successfully!");
        } catch (IllegalArgumentException e) {
            // Handle exceptions and add an error message
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        // Redirect back to the home page
        return "redirect:/";
    }

}
