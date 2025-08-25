package vn.edu.eiu.testlab.fecse456studentiddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.EquipmentType;

@Repository
public interface EquipmentTypeRepo extends JpaRepository<EquipmentType, Integer> {
    // JpaRepository provides all basic CRUD operations
    // No additional methods needed for basic functionality
}