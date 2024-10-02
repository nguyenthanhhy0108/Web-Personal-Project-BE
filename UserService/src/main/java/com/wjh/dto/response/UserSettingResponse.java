package com.wjh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingResponse {
    private String profileID;
    private boolean notify = true;
    private boolean active = true;
}
