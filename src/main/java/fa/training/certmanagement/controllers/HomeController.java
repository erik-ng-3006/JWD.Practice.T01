package fa.training.certmanagement.controllers;

import fa.training.certmanagement.entities.Category;
import fa.training.certmanagement.services.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Certificate> certificates = certificateService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("certificates", certificates);

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
            // Re-fetch categories and certificates for the view
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("certificates", certificateService.findAll());

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

}
