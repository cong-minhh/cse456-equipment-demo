package vn.edu.eiu.testlab.fecse456studentiddemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_EquipmentType")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipmentTypeId")
    private Integer equipmentTypeId;

    @Column(name = "typeName", unique = true, nullable = false, columnDefinition = "VARCHAR(100)")
    @NotBlank(message = "Type name must not be blank")
    @Size(max = 100, message = "Type name must not exceed 100 characters")
    private String typeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // One-to-many relationship with Equipment
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "equipmentType")
    private List<Equipment> equipments = new ArrayList<>();

    // Helper methods for managing equipment relationships
    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setEquipmentType(this);
    }

    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
        equipment.setEquipmentType(null);
    }

    // Constructor without ID for auto-increment
    public EquipmentType(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }
}