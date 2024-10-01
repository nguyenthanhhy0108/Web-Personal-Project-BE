package com.wjh.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("banner")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Banner {
    @Id
    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    private String bannerUrl;
    private ObjectId bannerImageId;
}
