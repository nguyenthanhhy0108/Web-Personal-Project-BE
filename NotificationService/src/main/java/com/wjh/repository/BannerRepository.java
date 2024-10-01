package com.wjh.repository;

import com.wjh.entity.Banner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends MongoRepository<Banner,String> {

}
