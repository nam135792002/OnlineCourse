package vn.edu.ut.repository;

import vn.edu.ut.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END
            FROM users u
            WHERE u.email = :email
            """, nativeQuery = true)
    boolean existsUserByEmail(String email);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END
            FROM users u
            WHERE u.phone_number = :phoneNumber
            """, nativeQuery = true)
    boolean existsUserByPhoneNumber(String phoneNumber);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN 'true' ELSE 'false' END
            FROM users u
            WHERE u.user_name = :username
            """, nativeQuery = true)
    boolean existsUserByUsername(String username);

    @Query(value = """
            SELECT u.*
            FROM users u
            WHERE u.email = :email
            """, nativeQuery = true)
    Optional<User> findByEmail(String email);

    User findUserByResetPasswordToken(String token);

    @Query("select u from User u where u.fullName like %?1%" +
            "or u.phoneNumber like %?1%" +
            "or u.email like %?1%")
    Page<User> search(String keyword, Pageable pageable);

    @Modifying
    @Query("update User u set u.enabled = true, u.verificationCode = null where u.id = ?1")
    void enable(Integer id);
}
