package com.flyghtt.flyghtt_backend.models.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BusinessToolValueDto {

    private String name;
    private List<ColumnDto> columns;
}
