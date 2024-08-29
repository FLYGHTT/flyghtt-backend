package com.flyghtt.flyghtt_backend.models.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessLogoResponse {

    private byte[] imageData;
}
