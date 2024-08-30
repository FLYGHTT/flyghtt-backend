package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.ToolNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UnauthorizedException;
import com.flyghtt.flyghtt_backend.models.entities.Column;
import com.flyghtt.flyghtt_backend.models.entities.Factor;
import com.flyghtt.flyghtt_backend.models.entities.Tool;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.requests.FavouriteRequest;
import com.flyghtt.flyghtt_backend.models.requests.LikeRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolAllRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.ColumnResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.repositories.ToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ToolService {

    private final ToolRepository toolRepository;
    private final ColumnService columnService;
    private final UserService userService;
    private final FactorService factorService;
    private final ToolCommentService toolCommentService;

    public IdResponse createTool(ToolRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();
        throwErrorIfToolNameNotAvailable(request.getToolName().toUpperCase());

        Tool tool = request.toDb();
        toolRepository.save(tool);

        return IdResponse.builder()
                .id(tool.getToolId())
                .message("Tool Has been successfully created (Tool Id)")
                .build();
    }

    public ToolResponse getToolById(UUID toolId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return toolRepository.findByToolId(toolId).orElseThrow(ToolNotFoundException::new).toDto();
    }

    public List<ToolResponse> getAllToolsByUser() {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return toolRepository.findAllByCreatedBy(UserUtil.getLoggedInUser().get().getUserId())
                .parallelStream().map(Tool::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AppResponse updateTool(ToolRequest request, UUID toolId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();
        throwErrorIfToolNameNotAvailable(request.getToolName().toUpperCase());

        canUserAlterTool();

        Tool tool = toolRepository.findByToolId(toolId).orElseThrow(ToolNotFoundException::new);
        tool.setName(request.getToolName().toUpperCase());
        tool.setDescription(request.getToolDescription());
        tool.setLink(request.getLink());
        tool.setPublic(request.isPublic());
        tool.setCommentable(request.isCommentable());

        toolRepository.save(tool);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Tool has been successfully updated").build();
    }

    @Transactional
    public AppResponse deleteTool(UUID toolId) {

        canUserAlterTool();

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        toolCommentService.deleteByToolId(toolId);
        columnService.deleteAllToolColumns(toolId);
        toolRepository.deleteByToolId(toolId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Tool has been successfully deleted").build();
    }

    public IdResponse createColumn(UUID toolId, ColumnRequest request) {

        canUserAlterTool();

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Column column = Column.builder()
                .name(request.getColumnName().toUpperCase())
                .description(request.getDescription())
                .toolId(toolId)
                .build();

        columnService.saveColumn(column);

        return IdResponse.builder()
                .id(column.getColumnId())
                .message("Column has been successfully created (Column Id)")
                .build();
    }

    public List<ColumnResponse> getAllToolColumns(UUID toolId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return columnService.getAllByToolId(toolId).parallelStream().map(Column::toDto)
                .collect(Collectors.toList());
    }

    public void canUserAlterTool() {

        if (!toolRepository.existsByCreatedBy(UserUtil.getLoggedInUser().get().getUserId())) {

            throw new UnauthorizedException("You're not the creator of this tool");
        }
    }

    public void throwErrorIfToolNameNotAvailable(String toBeName) {

        if (toolRepository.existsByNameAndIsPublicTrue(toBeName)) {

            throw new DataIntegrityViolationException("Tool name not available");
        }
    }

    public AppResponse likeTool(UUID toolId, LikeRequest request) {

        User user = UserUtil.getLoggedInUser().get();
        Tool tool = toolRepository.findByToolId(toolId).orElseThrow(ToolNotFoundException::new);

        Set<Tool> currentLikedTools = user.getLikedTools();
        String message;

        if (request.isLike()) {

            currentLikedTools.add(tool);
            message = "Tool has been successfully liked";
        } else {

            currentLikedTools.removeIf(tool1 -> tool1.equals(tool));
            message = "Tool has been successfully unliked";
        }

        user.setLikedTools(currentLikedTools);

        userService.saveUser(user);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message(message).build();
    }

    public AppResponse addToFavourites(UUID toolId, FavouriteRequest request) {

        User user = UserUtil.getLoggedInUser().get();
        Tool tool = toolRepository.findByToolId(toolId).orElseThrow(ToolNotFoundException::new);

        Set<Tool> currentFavouriteTools = user.getFavouriteTools();
        String message;

        if (request.isFavourite()) {

            currentFavouriteTools.add(tool);
            message = "Tool has been added to favourites successfully";
        } else {

            currentFavouriteTools.removeIf(tool1 -> tool1.equals(tool));
            message = "Tool has been successfully removed from favourites";
        }

        user.setFavouriteTools(currentFavouriteTools);

        userService.saveUser(user);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message(message).build();
    }

    public IdResponse createToolAll(ToolAllRequest request) {

        Tool tool = Tool.builder()
                        .name(request.getName().toUpperCase())
                                .createdBy(UserUtil.getLoggedInUser().get().getUserId())
                                        .description(request.getDescription())
                                                .commentable(request.isCommentable())
                                                        .isPublic(request.isPublic())
                                                                .link(request.getLink())
                                                                        .build();

        toolRepository.save(tool);

        request.getColumns().parallelStream().forEach(

                columnAllRequest -> {

                    Column column = Column.builder()
                                    .name(columnAllRequest.getName().toUpperCase())
                                            .description(columnAllRequest.getDescription())
                                                    .toolId(tool.getToolId()).build();

                    columnService.saveColumn(column);

                    columnAllRequest.getFactors().forEach(
                            factorName -> {

                                Factor factor = Factor.builder()
                                        .columnId(column.getColumnId())
                                                .name(factorName.toUpperCase())
                                                        .build();
                                factorService.saveFactor(factor);
                            }
                    );
                }
        );

        return IdResponse.builder()
                .id(tool.getToolId())
                .message("Tool has been successfully created (Tool Id)")
                .build();
    }
}
