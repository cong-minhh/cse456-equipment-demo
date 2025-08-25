package vn.edu.eiu.testlab.fecse456studentiddemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Major;
import vn.edu.eiu.testlab.fecse456studentiddemo.repository.MajorRepo;

import java.util.List;

@Service //Có quyền dùng @Combonent
public class MajorService {
    //DI: dùng dependency injection. Có mấy cách: field, constructor, setter
    @Autowired
    private MajorRepo majorRepo;

    //Hàm phục vụ lưu major xuống db
    public void saveMajor(Major major){
        majorRepo.save(major);
    }

    //Hàm phục vụ lấy tất cả major làm comboBox
    public List<Major> getAllMajors(){
        return majorRepo.findAll();
    }
}
