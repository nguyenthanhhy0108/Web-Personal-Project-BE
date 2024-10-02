package com.wjh.service;

import com.wjh.dto.response.UserSettingResponse;
import com.wjh.entity.UserSetting;
import com.wjh.mapper.UserSettingMapper;
import com.wjh.repository.UserSettingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserSettingService {

    private final UserSettingRepository userSettingRepository;
    private final UserSettingMapper userSettingMapper;

    public List<UserSettingResponse> findAll() {
        return this.userSettingRepository.findAll()
                .stream().map(this.userSettingMapper::toResponse)
                .toList();
    }

    public List<String> findAllNotifyTrueAndActiveTrueProfileId() {
        List<UserSetting> userSettingList =
                this.userSettingRepository.findByNotifyTrueAndActiveTrue();
        return userSettingList.stream().map(UserSetting::getProfileID).toList();
    }
}
