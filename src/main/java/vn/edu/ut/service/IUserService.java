package vn.edu.ut.service;

import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.payload.user.UserRequest;
import vn.edu.ut.payload.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ut.payload.user.UserReturnJwt;

public interface IUserService {
    UserResponse createUser(UserRequest userRequest, MultipartFile img);

    ClassResponse listAllUser(int pageNo, int pageSize, String sortBy, String sortDir, String keyword);

    UserResponse get(Integer userId);

    UserResponse updateUser(UserRequest userRequest, Integer userId, MultipartFile img);

    void delete(Integer userId);

    void changePasswordInCustomer(String password);

    UserResponse updateInfoCustomer(String fullName, MultipartFile img);

    UserResponse getUserInfo();
}
