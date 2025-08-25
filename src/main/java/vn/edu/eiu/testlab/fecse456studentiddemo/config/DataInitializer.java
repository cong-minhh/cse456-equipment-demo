package vn.edu.eiu.testlab.fecse456studentiddemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Equipment;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.EquipmentType;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Major;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Student;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.User;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.EquipmentService;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.EquipmentTypeService;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.MajorService;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.UserService;

import java.math.BigDecimal;

/**
 * Khi thêm data chú ý là phải thêm bảng 1 trước,
 * thêm bảng nhiều sau.
 */
@Component
public class DataInitializer implements CommandLineRunner {
    // Tiêm service để thao tác dữ liệu
    @Autowired // tiêm bằng field (có 3 cách)
    MajorService majorServ;

    @Autowired
    UserService userServ;

    @Autowired
    EquipmentTypeService equipmentTypeServ;

    @Autowired
    EquipmentService equipmentServ;

    @Override
    public void run(String... args) throws Exception {
        // Tạo mới obj
        Major m1 = new Major("CSE - Kỹ Thuật Phần Mềm", "Ngành kỹ thuật phần mềm");
        Major m2 = new Major("CSW - Mạng Và Truyền Thông Dữ Liệu", "Ngành Mạng và truyền thông dữ liệu");
        Student s1m1 = new Student("st1m1", "Ngô Nhất", 2000, 85);
        Student s2m1 = new Student("st2m1", "Trương Nhị", 2000, 90);
        Student s3m1 = new Student("st3m1", "Phan Tam", 2001, 85);
        Student s1m2 = new Student("st1m2", "Lý Tứ", 2002, 95);
        Student s2m2 = new Student("st2m2", "Phạm Ngũ", 2001, 75);

        // thêm student vào major
        m1.addStudent(s1m1);
        m1.addStudent(s2m1);
        m1.addStudent(s3m1);
        m2.addStudent(s1m2);
        m2.addStudent(s2m2);

        // Gọi service lưu database
        majorServ.saveMajor(m1);
        majorServ.saveMajor(m2);

        // Thêm mới user
        User admin = new User("admin", "admin123", 1);
        User staff = new User("staff", "staff", 2);
        User student = new User("student", "student", 3);
        userServ.save(admin);
        userServ.save(staff);
        userServ.save(student);

        // Tạo mới equipment types (phải tạo trước vì là bảng 1)
        EquipmentType et1 = new EquipmentType("Computer Hardware", "Desktop computers, laptops, and related hardware");
        EquipmentType et2 = new EquipmentType("Laboratory Equipment", "Scientific instruments and lab equipment");
        EquipmentType et3 = new EquipmentType("Audio Visual", "Projectors, speakers, and multimedia equipment");
        EquipmentType et4 = new EquipmentType("Furniture", "Desks, chairs, and classroom furniture");

        // Lưu equipment types xuống database
        equipmentTypeServ.saveEquipmentType(et1);
        equipmentTypeServ.saveEquipmentType(et2);
        equipmentTypeServ.saveEquipmentType(et3);
        equipmentTypeServ.saveEquipmentType(et4);

        // Tạo mới equipment items (bảng nhiều, tạo sau)
        Equipment eq1 = new Equipment("EQ00000001", "Dell OptiPlex Desktop", new BigDecimal("1500.00"), 25);
        Equipment eq2 = new Equipment("EQ00000002", "HP Laptop ProBook", new BigDecimal("2200.00"), 15);
        Equipment eq3 = new Equipment("EQ00000003", "Lenovo ThinkPad", new BigDecimal("2800.00"), 10);
        Equipment eq4 = new Equipment("EQ00000004", "Microscope Olympus", new BigDecimal("3500.00"), 8);
        Equipment eq5 = new Equipment("EQ00000005", "Digital Scale", new BigDecimal("1200.00"), 12);
        Equipment eq6 = new Equipment("EQ00000006", "Centrifuge Machine", new BigDecimal("4500.00"), 5);
        Equipment eq7 = new Equipment("EQ00000007", "Epson Projector", new BigDecimal("1800.00"), 20);
        Equipment eq8 = new Equipment("EQ00000008", "Sound System", new BigDecimal("2500.00"), 6);
        Equipment eq9 = new Equipment("EQ00000009", "Interactive Whiteboard", new BigDecimal("3200.00"), 8);
        Equipment eq10 = new Equipment("EQ00000010", "Office Desk", new BigDecimal("1000.00"), 50);
        Equipment eq11 = new Equipment("EQ00000011", "Ergonomic Chair", new BigDecimal("1100.00"), 45);
        Equipment eq12 = new Equipment("EQ00000012", "Conference Table", new BigDecimal("1500.00"), 12);

        // Thêm equipment vào equipment types
        et1.addEquipment(eq1);
        et1.addEquipment(eq2);
        et1.addEquipment(eq3);
        et2.addEquipment(eq4);
        et2.addEquipment(eq5);
        et2.addEquipment(eq6);
        et3.addEquipment(eq7);
        et3.addEquipment(eq8);
        et3.addEquipment(eq9);
        et4.addEquipment(eq10);
        et4.addEquipment(eq11);
        et4.addEquipment(eq12);

        // Lưu equipment xuống database
        equipmentServ.saveEquipment(eq1);
        equipmentServ.saveEquipment(eq2);
        equipmentServ.saveEquipment(eq3);
        equipmentServ.saveEquipment(eq4);
        equipmentServ.saveEquipment(eq5);
        equipmentServ.saveEquipment(eq6);
        equipmentServ.saveEquipment(eq7);
        equipmentServ.saveEquipment(eq8);
        equipmentServ.saveEquipment(eq9);
        equipmentServ.saveEquipment(eq10);
        equipmentServ.saveEquipment(eq11);
        equipmentServ.saveEquipment(eq12);

    }
}
