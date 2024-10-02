package com.wjh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSetting {

    @Id
    private String profileID;

    private boolean notify = true;
    private boolean active = true;

    @OneToOne
    @MapsId
    private Profile profile;
}
