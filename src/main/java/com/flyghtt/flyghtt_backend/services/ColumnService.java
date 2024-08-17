package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.ColumnNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.Column;
import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.repositories.ColumnRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ColumnService {

    private final ColumnRepository columnRepository;

    public Column createColumn(Column column) {

        return columnRepository.save(column);
    }

    public List<Column> getAllByToolId(UUID toolId) {

        return columnRepository.findAllByToolId(toolId);
    }

    public Column getByColumnId(UUID columnId) {

        return columnRepository.findByColumnId(columnId).orElseThrow(ColumnNotFoundException::new);
    }

    public AppResponse updateColumn(UUID columnId, ColumnRequest request) {

        Column column = columnRepository.findByColumnId(columnId).orElseThrow(ColumnNotFoundException::new);
        column.setName(request.getColumnName().toUpperCase());
        column.setDescription(request.getDescription());

        columnRepository.save(column);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Column has been successfully updated")
                .build();
    }

    @Transactional
    public AppResponse deleteColumn(UUID columnId) {

        columnRepository.deleteByColumnId(columnId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Column has been successfully deleted")
                .build();
    }

    @Transactional
    void deleteAllToolColumns(UUID toolId) {

        columnRepository.deleteAllByToolId(toolId);
    }
}
