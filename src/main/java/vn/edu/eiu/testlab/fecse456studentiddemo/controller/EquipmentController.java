package vn.edu.eiu.testlab.fecse456studentiddemo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.Equipment;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.EquipmentType;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.User;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.EquipmentService;
import vn.edu.eiu.testlab.fecse456studentiddemo.service.EquipmentTypeService;

import java.util.ArrayList;
import java.util.List;

/**
 * UI -- Controller(Đang ở đây) -- Service -- Repository -- [(JDBC) --
 * DB(Mysql)]
 * mỗi một url thì cần phải có một hàm xử lý
 * Đối với form thì dùng @PostMapping
 */
@Controller
public class EquipmentController {
    // Tiêm service để truy xuất db
    @Autowired // tiêm bằng field
    private EquipmentService equipmentServ;
    @Autowired
    private EquipmentTypeService equipmentTypeServ;

    // url localhost:8080/equipments --> trả về trang equipment-list.html
    /*
     * link này có 2 cách request:
     * 1. Gõ trực tiếp
     * - Khi gõ trực tiếp link /equipments nếu hàm xử lý có @RequestParam thì sẽ bị
     * lỗi Null (NPE), nên phải xử lý bằng 1 trong 2 cách: gán default value
     * cho @RequestParam hoặc thêm thuộc tính required = false
     * 2. Thông qua nút bấm search
     */
    @GetMapping("equipments")
    public String equipments(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model,
            HttpSession ses) {
        // Xử lý phân quyền, chặn gõ link trực tiếp, nếu chưa login thì > login.html
        // todo: dùng spring security (dự án lớn), handlerInterceptor (dự án vừa, nhỏ)
        // sẽ hay hơn.
        User user = (User) ses.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Check role authorization - Admin (1) and Staff (2) can view equipment list
        if (user.getRole() != 1 && user.getRole() != 2) {
            model.addAttribute("errorMessage", "You are not authorized to access this function!");
            return "login";
        }

        List<Equipment> equipmentList = new ArrayList<>();
        if (keyword.isBlank()) {
            // Lấy danh sách equipment từ db
            equipmentList = equipmentServ.getAllEquipments();
        } else {
            equipmentList = equipmentServ.searchEquipmentsByName(keyword);
        }
        // Gửi qua view cho thymeleaf mix với html tag
        model.addAttribute("equipmentList", equipmentList);

        return "equipment-list"; // equipment-list.html
    }

    // Hàm xử lý url: localhost:8080/equipment/edit/{id}
    @GetMapping("equipment/edit/{id}")
    public String editEquipment(@PathVariable("id") String id, Model model, HttpSession ses,
            RedirectAttributes RedAttr) {
        // Không cho gõ link trực tiếp, chưa login > login
        User user = (User) ses.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // Login rồi > Kiểm tra thêm role không phải là 1 (chỉ Admin mới được edit)
        if (user.getRole() != 1) {
            // báo lỗi access denied.
            RedAttr.addFlashAttribute("errRole", "Access Denied. You are not allowed to perform this action.");
            return "redirect:/equipments";
        }
        // Lấy equipment từ db có mã là id.
        Equipment e = equipmentServ.getEquipmentById(id);

        // Gửi qua cho form chỉnh sửa
        model.addAttribute("equipment", e);

        // Gửi thêm danh sách equipment type để làm select (comboBox)
        List<EquipmentType> equipmentTypeList = equipmentTypeServ.getAllEquipmentTypes();
        model.addAttribute("equipmentTypeList", equipmentTypeList);

        // Gửi kèm một attribute để báo là edit equipment
        model.addAttribute("formMode", "edit");

        return "equipment-form"; // Trả về trang form chỉnh sửa thông tin equipment.
    }

    // Hàm xử lí link thêm mới equipment
    @GetMapping("/equipment/add")
    public String addEquipment(Model model, HttpSession ses, RedirectAttributes RedAttr) {
        // Không cho gõ link trực tiếp, chưa login > login
        User user = (User) ses.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // Login rồi > Kiểm tra thêm role không phải là 1 (chỉ Admin mới được add)
        if (user.getRole() != 1) {
            // báo lỗi access denied.
            RedAttr.addFlashAttribute("errRole", "Access Denied. You are not allowed to perform this action.");
            return "redirect:/equipments";
        }
        // Lấy toàn bộ equipment type để gửi qua comboBox(select) của form
        // Note: attributeName phải giống với hàm edit
        List<EquipmentType> equipmentTypeList = equipmentTypeServ.getAllEquipmentTypes();
        model.addAttribute("equipmentTypeList", equipmentTypeList);

        // Tạo mới và gửi một equipment chưa có thông tin để gửi qua form
        // Note: attributeName phải giống với hàm edit
        model.addAttribute("equipment", new Equipment());

        // Gửi kèm một attribute để báo là thêm equipment
        model.addAttribute("formMode", "add");
        return "equipment-form";
    }

    // Hàm xử lý cho url /equipment/form, khi người dùng bấm save trên form bằng
    // Post method
    @PostMapping("/equipment/form")
    public String saveEquipment(@Valid @ModelAttribute("equipment") Equipment e, BindingResult result, Model model,
            @RequestParam("formMode") String formMode, HttpSession ses, RedirectAttributes RedAttr) {

        // Không cho gõ link trực tiếp, chưa login > login
        User user = (User) ses.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // Login rồi > Kiểm tra thêm role không phải là 1 (chỉ Admin mới được add/edit
        // equipment)
        if (user.getRole() != 1) {
            // báo lỗi access denied.
            RedAttr.addFlashAttribute("errRole", "Access Denied. You are not allowed to perform this action.");
            return "redirect:/equipments";
        }

        // Lấy thông tin gửi từ form xuống, Nếu có lỗi thì sẽ quay lại trang form, kèm
        // theo các message
        if (result.hasErrors()) {
            model.addAttribute("formMode", formMode);
            model.addAttribute("equipmentTypeList", equipmentTypeServ.getAllEquipmentTypes());

            return "equipment-form";
        }

        /*
         * Nếu thêm mới mà trùng key thì báo lỗi và trả lại trang form kèm lỗi:
         * Lấy key đem dò trong db xem đã có hay chưa (cần service?)
         * Nếu có thì quay lại form gửi kèm thông báo trùng key.
         */
        if (formMode.equals("add")) {
            if (equipmentServ.checkExistsById(e.getEquipmentId())) {
                model.addAttribute("formMode", formMode);
                model.addAttribute("equipmentTypeList", equipmentTypeServ.getAllEquipmentTypes());
                model.addAttribute("duplicateId", "Id is already exists");
                return "equipment-form";
            }
        }

        // Nếu data ok hết thì lưu equipment và chuyển qua trang equipments
        equipmentServ.saveEquipment(e);
        // Validate coi thông tin có hợp lệ không
        // Lưu xuống database
        // Trả về url equipments để hiển thị danh sách equipment đã cập nhật bằng
        // redirect
        return "redirect:/equipments";
    }

    // Hàm xử lý link xóa một equipment /equipment/delete
    @GetMapping("equipment/delete/{id}")
    public String deleteEquipment(@PathVariable("id") String id, HttpSession ses, RedirectAttributes RedAttr) {
        // Không cho gõ link trực tiếp, chưa login > login
        User user = (User) ses.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // Login rồi > Kiểm tra thêm role không phải là 1 (chỉ Admin mới được delete)
        if (user.getRole() != 1) {
            // báo lỗi access denied.
            RedAttr.addFlashAttribute("errRole", "Access Denied. You are not allowed to perform this action.");
            return "redirect:/equipments";
        }
        // Gọi service thực hiện xóa equipment
        equipmentServ.removeEquipmentById(id);
        // Trả về trang equipment
        return "redirect:/equipments";
    }

    // Hàm xử lý advanced equipment listing - chỉ Admin mới được truy cập
    @GetMapping("equipments/advanced")
    public String advancedEquipmentListing(Model model, HttpSession ses, RedirectAttributes RedAttr) {
        // Không cho gõ link trực tiếp, chưa login > login
        User user = (User) ses.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // Login rồi > Kiểm tra thêm role không phải là 1 (chỉ Admin mới được truy cập
        // advanced)
        if (user.getRole() != 1) {
            // báo lỗi access denied.
            RedAttr.addFlashAttribute("errRole", "Access Denied. You are not allowed to perform this action.");
            return "redirect:/equipments";
        }

        // Lấy top equipment by type từ service
        List<Equipment> topEquipmentList = equipmentServ.getTopEquipmentByType();
        model.addAttribute("topEquipmentList", topEquipmentList);

        return "equipment-advanced"; // equipment-advanced.html
    }
}