package vn.edu.eiu.testlab.fecse456studentiddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Equipment;

import java.util.List;

/** keyword : Derived query methods in spring data jpa */
@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, String> {

    // Search method for equipment listing - similar to student search
    List<Equipment> findAllByEquipmentNameContainingIgnoreCase(String equipmentName);

    // Find equipment by equipment type for filtering
    List<Equipment> findAllByEquipmentType_EquipmentTypeId(Integer equipmentTypeId);

    // Custom query for advanced listing - top 3 equipment by stock per type
    @Query("SELECT e FROM Equipment e " +
            "WHERE (SELECT COUNT(e2) FROM Equipment e2 " +
            "       WHERE e2.equipmentType = e.equipmentType " +
            "       AND e2.quantityAvailable >= e.quantityAvailable) <= 3 " +
            "ORDER BY e.equipmentType.equipmentTypeId, e.quantityAvailable DESC")
    List<Equipment> findTop3EquipmentByStockPerType();

    // Kế thừa các hàm abstract tự sinh để truy xuất db, không cần viết gì thêm.
}