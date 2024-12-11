package fa.training.certmanagement.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(length = 12)
    String id;

    @NotBlank(message = "Certificate name is required")
    @Column(name = "cert_name")
    String name;

    @NotBlank(message = "Certificate cost is required")
    @Column(columnDefinition = "decimal(5,1)")
    Float cost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
