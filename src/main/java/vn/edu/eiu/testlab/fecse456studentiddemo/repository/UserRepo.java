package vn.edu.eiu.testlab.fecse456studentiddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User searchUserByUsernameIgnoreCase(String username);
    // Truy vấn để lưu user
    // public void save(User user){}
    // Truy vấn để tìm user phục vụ login
}
