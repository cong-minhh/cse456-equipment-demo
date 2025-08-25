package vn.edu.eiu.testlab.fecse456studentiddemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse456studentiddemo.model.User;
import vn.edu.eiu.testlab.fecse456studentiddemo.repository.UserRepo;

@Service
public class UserService {
    //Tiêm repo
    @Autowired
    private UserRepo userRepo;

    //Hàm phục vụ lưu user
    public void save(User user){
        userRepo.save(user);
    }

    //Hàm phục vụ login
    public User findByUsername(String userName){
        return userRepo.searchUserByUsernameIgnoreCase(userName);
    }

}
