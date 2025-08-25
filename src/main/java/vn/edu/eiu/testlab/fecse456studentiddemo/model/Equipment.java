package vn.edu.eiu.testlab.fecse456studentiddemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_Equipment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Equipment {
    @Id
    @Column(name = "equipmentId", columnDefinition = "CHAR(10)")
    @Size(min = 10, max = 10, message = "Equipment ID must be exactly 10 characters")
    @NotBlank(message = "Equipment ID must not be blank")
    private String equipmentId;

    @ManyToOne
    @JoinColumn(name = "equipmentTypeId", nullable = false)
    @NotNull(message = "Equipment type must not be null")
    private EquipmentType equipmentType;

    @Column(name = "equipmentName", nullable = false, columnDefinition = "NVARCHAR(100)")
    @NotBlank(message = "Equipment name must not be blank")
    @Size(min = 5, max = 100, message = "Equipment name must be between 5 and 100 characters")
    private String equipmentName;

    @Column(name = "purchasePrice", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Purchase price must not be null")
    @DecimalMin(value = "1000.00", message = "Purchase price must be greater than or equal to 1000")
    private BigDecimal purchasePrice;

    @Column(name = "quantityAvailable", nullable = false)
    @NotNull(message = "Quantity available must not be null")
    @Min(value = 0, message = "Quantity available must be greater than or equal to 0")
    @Max(value = 500, message = "Quantity available must be less than or equal to 500")
    private Integer quantityAvailable;

    @CreatedDate
    @Column(name = "purchaseDate")
    private LocalDateTime purchaseDate;

    // Constructor without equipmentType for initial creation
    public Equipment(String equipmentId, String equipmentName, BigDecimal purchasePrice, Integer quantityAvailable) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.purchasePrice = purchasePrice;
        this.quantityAvailable = quantityAvailable;
    }
}