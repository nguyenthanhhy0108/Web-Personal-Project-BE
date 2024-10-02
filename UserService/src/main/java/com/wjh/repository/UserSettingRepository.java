package com.wjh.repository;

import com.wjh.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, String> {
    List<UserSetting> findByNotifyTrueAndActiveTrue();
}
