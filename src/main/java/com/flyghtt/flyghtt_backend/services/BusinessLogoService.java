package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.entities.BusinessLogo;
import com.flyghtt.flyghtt_backend.repositories.BusinessLogoRepository;
import com.flyghtt.flyghtt_backend.services.utils.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class BusinessLogoService {

    private final BusinessLogoRepository businessLogoRepository;

    public byte[] uploadImage(MultipartFile businessLogoFile, UUID businessId) throws IOException {
        var businessLogoToSave = BusinessLogo.builder()
                .type(businessLogoFile.getContentType())
                .imageData(ImageUtils.compressImage(businessLogoFile.getBytes()))
                .businessId(businessId)
                .build();
        businessLogoRepository.save(businessLogoToSave);

        return businessLogoFile.getBytes();
    }

    public byte[] downloadImage(UUID businessId) {
        Optional<BusinessLogo> dbBusinessLogo = businessLogoRepository.findByBusinessId(businessId);

        return dbBusinessLogo.map(businessLogo -> {
            try {
                return ImageUtils.decompressImage(businessLogo.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID",  businessLogo.getBusinessLogoId());
            }
        }).orElse(null);
    }

    @Transactional
    public void deleteByBusinessId(UUID businessId) {

        businessLogoRepository.deleteByBusinessId(businessId);
    }

    @Transactional
    public byte[] updateBusinessLogo(UUID businessId, MultipartFile newBusinessLogoFile) throws IOException {

        BusinessLogo toBeUpdated = businessLogoRepository.findByBusinessId(businessId).orElse(BusinessLogo.builder().businessId(businessId).build());
        toBeUpdated.setType(newBusinessLogoFile.getContentType());
        toBeUpdated.setImageData(ImageUtils.compressImage(newBusinessLogoFile.getBytes()));

        businessLogoRepository.save(toBeUpdated);

        return newBusinessLogoFile.getBytes();
    }
}
