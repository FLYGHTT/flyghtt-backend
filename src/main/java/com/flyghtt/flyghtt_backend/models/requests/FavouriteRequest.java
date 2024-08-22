package com.flyghtt.flyghtt_backend.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavouriteRequest {

    @NotNull
    private boolean favourite;
}
