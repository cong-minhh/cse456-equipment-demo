package vn.edu.eiu.testlab.fecse456studentiddemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.EquipmentType;
import vn.edu.eiu.testlab.fecse456studentiddemo.repository.EquipmentTypeRepo;

import java.util.List;

@Service
public class EquipmentTypeService {
    //DI: dùng dependency injection. Có mấy cách: field, constructor, setter
    @Autowired
    private EquipmentTypeRepo equipmentTypeRepo;

    //Hàm phục vụ lưu equipment type xuống db
    public void saveEquipmentType(EquipmentType equipmentType){
        equipmentTypeRepo.save(equipmentType);
    }

    //Hàm phục vụ lấy tất cả equipment types làm comboBox/dropdown
    public List<EquipmentType> getAllEquipmentTypes(){
        return equipmentTypeRepo.findAll();
    }

    //Hàm lấy equipment type bằng id phục vụ cho việc edit trên view
    public EquipmentType getEquipmentTypeById(Integer id){
        return equipmentTypeRepo.findById(id).orElseThrow(); //hàm tự sinh
    }

    //Hàm phục vụ xóa equipment type
    public void removeEquipmentTypeById(Integer id){
        equipmentTypeRepo.deleteById(id);
    }

    //Hàm phục vụ kiểm tra trùng pk
    public Boolean checkExistsById(Integer id){
        return equipmentTypeRepo.existsById(id); //Hàm tự sinh
    }
}