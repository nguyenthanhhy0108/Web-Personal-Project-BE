package com.wjh.repository;

import com.wjh.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByProfileIDIn(List<String> profileIDs);
    Profile findByUsername(String username);
    Profile findByUserID(String userID);
}
