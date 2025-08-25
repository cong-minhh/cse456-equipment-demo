package vn.edu.eiu.testlab.fecse456studentiddemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Student;
import vn.edu.eiu.testlab.fecse456studentiddemo.repository.StudentRepo;

import java.util.List;

@Service
public class StudentService {
    //Tiêm dependency repo để thao tác db
    @Autowired
    StudentRepo studentRepo;

    //Hàm lưu sinh viên xuống db
    public void saveStudent(Student student){
        studentRepo.save(student);
    }
    //Hàm lấy tất cả sinh viên (phục vụ cho trang student-list)
    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    //Hàm lấy sinh viên bằng id phục vụ cho viên edit trên view
    public Student getStudentById(String id){
        return studentRepo.findById(id).orElseThrow(); //hàm tự sinh
    }
    //Hàm phục vụ xóa sinh viên
    public void removeStudentById(String id){
        studentRepo.deleteById(id);
    }

    //Hàm phục vụ tìm kiếm sinh viên bằng tên
    public List<Student> searchStudentsByName(String keyword){
        return studentRepo.searchAllByNameContainingIgnoreCase(keyword);
    }

    //Hàm phục vụ kiểm tra trùng pk
    public Boolean checkExistsById(String id){
        return studentRepo.existsById(id); //Hàm tự sinh
    }

}
