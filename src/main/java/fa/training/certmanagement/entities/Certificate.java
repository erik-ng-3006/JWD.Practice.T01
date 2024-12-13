package fa.training.certmanagement.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Certificate {
    @Id
    @NotBlank(message = "Certificate ID is required")
    @Size(max = 12, message = "Certificate ID cannot exceed 12 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "Certificate ID must contain only letters, numbers, and dashes")
    @Column(length = 12)
    String id;

    @NotBlank(message = "Certificate name is required")
    @Size(min = 3, max = 255, message = "Certificate name must be between 3 and 255 characters")
    @Column(name = "cert_name")
    String name;

    @NotNull(message = "Certificate cost is required")
    @Positive(message = "Certificate cost must be positive number")
    @Column(columnDefinition = "decimal(5,1)")
    Float cost;

    @NotNull(message = "Certificate category is required")
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
