package fa.training.certmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    @NotBlank(message = "Category name is required")
    String name;

    @Column(length = 1000)
    @NotBlank(message = "Category description is required")
    String description;

    @OneToMany(mappedBy = "category")
    List<Certificate> certs;
}
