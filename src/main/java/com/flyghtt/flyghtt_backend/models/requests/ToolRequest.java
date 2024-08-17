package com.flyghtt.flyghtt_backend.models.requests;

import com.flyghtt.flyghtt_backend.models.entities.Tool;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolRequest {

    @NotNull
    private String toolName;
    @NotNull
    private String toolDescription;
    private String link;
    private boolean commentable;
    private boolean isPublic;

    public Tool toDb() {

        return Tool.builder()
                .name(toolName.toUpperCase())
                .description(toolDescription)
                .link(link)
                .commentable(commentable)
                .isPublic(isPublic)
                .createdBy(UserUtil.getLoggedInUser().get().getUserId())
                .build();
    }
}
