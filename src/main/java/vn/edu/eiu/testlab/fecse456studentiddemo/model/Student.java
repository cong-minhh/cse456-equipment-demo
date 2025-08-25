package vn.edu.eiu.testlab.fecse456studentiddemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_Student")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) // Tự động ghi nhật ký
public class Student {
    // Khai báo mapping ORM
    @Id
    @Column(name = "ID", columnDefinition = "CHAR(5)")
    // Khai báo validator
    @Size(min = 5, max = 5, message = "Length of ID must be exactly 5 charaters")
    @NotBlank(message = "ID must be not left blank")
    private String id;

    @Column(name = "Name", nullable = false, columnDefinition = "NVARCHAR(100)")
    // Validate
    @NotNull(message = "Name must be not null")
    @Size(min = 5, max = 100, message = "Length of name must be between 5 and 100")
    @Pattern(
            // Biểu thức regex để kiểm tra
            regexp = "^(\\p{Lu}\\p{Ll}+)(\\s\\p{Lu}\\p{Ll}+)*$", message = "Each word must start with an uppercase letter")
    private String name;

    @Min(value = 2000, message = "Yob must be from 2000")
    @Max(value = 2007, message = "Yob must be to 2007")
    @NotNull(message = "Yob must not be null")
    @Column(name = "Year of Birth", nullable = false)
    private int yob;

    @Min(value = 0, message = "GPA must be grater than or equal 0")
    @Max(value = 100, message = "GPA must be less than or equal 100")
    @Column(name = "GPA")
    private double gpa;

    @ManyToOne()
    @JoinColumn(name = "majorID") // Để báo là tui muốn đặt tên cột khóa ngoại
    private Major major; // Cái này sẽ gắn vào mappedBy bên class Major

    // Thuộc tính ghi nhận ngày giờ tạo
    @CreatedDate // Tự động ghi ngày tạo sinh viên
    private LocalDateTime createdDate;

    @LastModifiedDate // Ghi nhận thời điểm chỉnh sửa lần cuối
    private LocalDateTime modifiedDate;

    // @CreatedBy //Ghi nhận user tạo
    // @LastModifiedBy //Ghi nhận người chỉnh sửa cuối cùng

    // Bổ sung thêm constructor không có major, vì lúc thêm mới sinh viên chưa có
    // thông tin major.
    public Student(String id, String name, int yob, double gpa) {
        this.id = id;
        this.name = name;
        this.yob = yob;
        this.gpa = gpa;
    }
}
