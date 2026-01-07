package com.datasoft.bkash.ea.responseFilter.dto;

import com.datasoft.bkash.ea.response.ApiResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SseResponseDto {
    private ApiResponse apiResponse;
    private String loginId;
    private String generatedId;
}
