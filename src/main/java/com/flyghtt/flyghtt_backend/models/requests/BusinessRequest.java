package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BusinessRequest {

    private String businessName;
    private String description;
    private MultipartFile businessLogo;

    public void clean() {

        this.businessName = businessName.trim().toUpperCase();
    }
}
