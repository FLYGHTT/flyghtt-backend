package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.ColumnNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UnauthorizedException;
import com.flyghtt.flyghtt_backend.models.entities.Column;
import com.flyghtt.flyghtt_backend.models.entities.Factor;
import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.requests.FactorRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.repositories.ColumnRepository;
import com.flyghtt.flyghtt_backend.repositories.ToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final ToolRepository toolRepository;
    private final FactorService factorService;

    public void saveColumn(Column column) {

        try {
            columnRepository.save(column);
            columnRepository.flush();
        } catch (DataIntegrityViolationException ex) {

            throw new com.flyghtt.flyghtt_backend.exceptions.DataIntegrityViolationException("COLUMN NAME " + column.getName());
        }
    }

    public List<Column> getAllByToolId(UUID toolId) {

        return columnRepository.findAllByToolId(toolId);
    }

    public Column getByColumnId(UUID columnId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return columnRepository.findByColumnId(columnId).orElseThrow(ColumnNotFoundException::new);
    }

    public AppResponse updateColumn(UUID columnId, ColumnRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();
        canUserAlterTool();

        Column column = columnRepository.findByColumnId(columnId).orElseThrow(ColumnNotFoundException::new);
        column.setName(request.getColumnName().toUpperCase());
        column.setDescription(request.getDescription());

        saveColumn(column);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Column has been successfully updated")
                .build();
    }

    @Transactional
    public AppResponse deleteColumn(UUID columnId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();
        canUserAlterTool();

        factorService.deleteAllByColumnId(columnId);
        columnRepository.deleteByColumnId(columnId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Column has been successfully deleted")
                .build();
    }

    @Transactional
    void deleteAllToolColumns(UUID toolId) {

        columnRepository.findAllByToolId(toolId).forEach(
                column -> factorService.deleteAllByColumnId(column.getColumnId())
        );
        columnRepository.deleteAllByToolId(toolId);
    }

    public void canUserAlterTool() {

        if (!toolRepository.existsByCreatedBy(UserUtil.getLoggedInUser().get().getUserId())) {

            throw new UnauthorizedException("You're not the creator of this tool");
        }
    }

    public AppResponse createFactor(UUID columnId, FactorRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        request.getFactors().forEach(
                factor -> factorService.saveFactor(
                        Factor.builder()
                        .columnId(columnId)
                        .name(factor.toUpperCase())
                                .build()
                )
        );

        return AppResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Factors has been successfully added to column").build();
    }
}
