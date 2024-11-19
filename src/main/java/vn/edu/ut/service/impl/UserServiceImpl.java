package vn.edu.ut.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.ut.entity.Role;
import vn.edu.ut.entity.User;
import vn.edu.ut.enums.ErrorCode;
import vn.edu.ut.enums.RoleType;
import vn.edu.ut.exception.AppApiException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.ClassResponse;
import vn.edu.ut.payload.user.UserRequest;
import vn.edu.ut.payload.user.UserResponse;
import vn.edu.ut.payload.user.UserReturnJwt;
import vn.edu.ut.repository.RoleRepository;
import vn.edu.ut.repository.UserRepository;
import vn.edu.ut.service.IUserService;
import vn.edu.ut.utils.UploadFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UploadFile uploadFile;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest, MultipartFile img) {
        if (userRepository.existsUserByEmail(userRequest.getEmail())) {
            throw new AppApiException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsUserByUsername(userRequest.getUsername())) {
            throw new AppApiException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.existsUserByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new AppApiException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        String nameRole = RoleType.ROLE_ADMIN.toString();
        Role role = roleRepository.findByName(nameRole).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", nameRole));

        User user = checkValid(userRequest, role, img);
        user.setEnabled(true);
        User savedUser = userRepository.save(user);

        return convertToDto(savedUser);
    }

    @Override
    public ClassResponse listAllUser(int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = null;
        if (keyword != null && !keyword.isEmpty()) {
            users = userRepository.search(keyword, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        List<User> listUsers = users.getContent();

        List<UserResponse> content = listUsers.stream().map(this::convertToDto).toList();

        return ClassResponse.convertToClassResponse(users, content);
    }

    @Override
    public UserResponse get(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return convertToDto(user);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Integer userId, MultipartFile img) {
        User userInDB = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (img != null) {
            if (userInDB.getPhoto() != null) {
                uploadFile.deleteImageInCloudinary(userInDB.getPhoto());
            }
            String url = uploadFile.uploadFileOnCloudinary(img);
            userInDB.setPhoto(url);
        }

        if (!userRequest.getPassword().equals("Unknown password")) {
            userInDB.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userInDB.setFullName(userRequest.getFullName());
        userInDB.setEmail(userRequest.getEmail());
        userInDB.setPhoneNumber(userRequest.getPhoneNumber());
        userInDB.setEnabled(userRequest.isEnabled());

        User savedUser = userRepository.save(userInDB);
        return convertToDto(savedUser);
    }

    @Override
    public void delete(Integer userId) {
        User userInDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (userInDB.getPhoto() != null) {
            System.out.println(userInDB.getPhoto());
            uploadFile.deleteImageInCloudinary(userInDB.getPhoto());
        }
        userRepository.delete(userInDB);
    }

    @Override
    public UserResponse updateInfoCustomer(String fullName, MultipartFile img) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (fullName != null) {
            user.setFullName(fullName);
        }

        if (img != null) {
            String urlImage = uploadFile.uploadFileOnCloudinary(img);
            user.setPhoto(urlImage);
        }

        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setRoleName(savedUser.getRole().getName());

        return userResponse;
    }

    @Override
    public void changePasswordInCustomer(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return convertToDto(user);
    }

    private UserResponse convertToDto(User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }

    private User checkValid(UserRequest userRequest, Role role, MultipartFile img) {
        User user = modelMapper.map(userRequest, User.class);
        if (img != null) {
            String url = uploadFile.uploadFileOnCloudinary(img);
            user.setPhoto(url);
        }
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
