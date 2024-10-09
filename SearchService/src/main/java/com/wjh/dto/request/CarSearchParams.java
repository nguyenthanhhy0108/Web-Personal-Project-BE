package com.wjh.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarSearchParams {
    private String brandName;
    private String carName;
    @NotNull
    @Min(value = 1, message = "INVALID_PAGE_NUMBER")
    private int pageNumber;
    @Min(value = 1, message = "INVALID_PAGE_SIZE")
    private int pageSize;
}
