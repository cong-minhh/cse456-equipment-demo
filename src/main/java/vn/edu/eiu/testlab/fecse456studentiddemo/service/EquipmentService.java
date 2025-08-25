package vn.edu.eiu.testlab.fecse456studentiddemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Equipment;
import vn.edu.eiu.testlab.fecse456studentiddemo.repository.EquipmentRepo;

import java.util.List;

@Service
public class EquipmentService {
    //Tiêm dependency repo để thao tác db
    @Autowired
    EquipmentRepo equipmentRepo;

    //Hàm lưu equipment xuống db
    public void saveEquipment(Equipment equipment){
        equipmentRepo.save(equipment);
    }
    
    //Hàm lấy tất cả equipment (phục vụ cho trang equipment-list)
    public List<Equipment> getAllEquipments(){
        return equipmentRepo.findAll();
    }

    //Hàm lấy equipment bằng id phục vụ cho việc edit trên view
    public Equipment getEquipmentById(String id){
        return equipmentRepo.findById(id).orElseThrow(); //hàm tự sinh
    }
    
    //Hàm phục vụ xóa equipment
    public void removeEquipmentById(String id){
        equipmentRepo.deleteById(id);
    }

    //Hàm phục vụ kiểm tra trùng pk
    public Boolean checkExistsById(String id){
        return equipmentRepo.existsById(id); //Hàm tự sinh
    }

    //Hàm phục vụ tìm kiếm equipment bằng tên
    public List<Equipment> searchEquipmentsByName(String keyword){
        return equipmentRepo.findAllByEquipmentNameContainingIgnoreCase(keyword);
    }

    //Hàm phục vụ lấy top 3 equipment theo stock per type cho advanced listing
    public List<Equipment> getTopEquipmentByType(){
        return equipmentRepo.findTop3EquipmentByStockPerType();
    }
}