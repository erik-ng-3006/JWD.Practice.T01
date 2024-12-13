package fa.training.certmanagement.controllers;

import fa.training.certmanagement.entities.Category;
import fa.training.certmanagement.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import fa.training.certmanagement.services.CategoryService;
import fa.training.certmanagement.entities.Certificate;

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

        return "/home/index";
    }

}
