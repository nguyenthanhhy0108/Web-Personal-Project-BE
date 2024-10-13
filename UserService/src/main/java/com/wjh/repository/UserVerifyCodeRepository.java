package com.wjh.repository;

import com.wjh.entity.UserVerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerifyCodeRepository extends JpaRepository<UserVerifyCode, String> {
    UserVerifyCode findByEmail(String email);
}
