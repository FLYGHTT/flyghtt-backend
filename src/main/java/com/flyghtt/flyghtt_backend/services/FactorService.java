package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.FactorNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.Factor;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.repositories.FactorRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FactorService {

    private final FactorRepository factorRepository;

    public void createFactor(Factor factor) {

        factorRepository.save(factor);
    }

    public List<Factor> getAllColumnFactors(UUID columnId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return factorRepository.findAllByColumnId(columnId);
    }

    public AppResponse updateFactor(UUID factorId, OneFactorRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Factor factor = factorRepository.findByFactorId(factorId).orElseThrow(FactorNotFoundException::new);
        factor.setName(request.getFactor().toUpperCase());
        factorRepository.save(factor);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Factor has been successfully updated").build();
    }

    @Transactional
    public AppResponse deleteFactor(UUID factorId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        factorRepository.deleteByFactorId(factorId);
        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Factor has been successfully deleted").build();
    }

    public void deleteAllByColumnId(UUID columnId) {

        factorRepository.deleteAllByColumnId(columnId);
    }
}
