package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.GiftCertificateUpdateCondition;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.FilterConditionValidator;
import com.epam.esm.service.validator.impl.GiftCertificateValidator;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.UpdateGiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implemented {@link GiftCertificateService}
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final DtoConverter<GiftCertificateDto, GiftCertificate> giftCertificateDtoConverter;
    private final GiftCertificateValidator giftCertificateValidator;
    private final IdValidator idValidator;
    private final UpdateGiftCertificateValidator updateGiftCertificateValidator;
    private final FilterConditionValidator filterConditionValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      @Qualifier("giftCertificateDtoConverter") DtoConverter<GiftCertificateDto, GiftCertificate> giftCertificateDtoConverter,
                                      GiftCertificateValidator giftCertificateValidator, IdValidator idValidator, UpdateGiftCertificateValidator updateGiftCertificateValidator, FilterConditionValidator filterConditionValidator) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateDtoConverter = giftCertificateDtoConverter;
        this.giftCertificateValidator = giftCertificateValidator;
        this.idValidator = idValidator;
        this.updateGiftCertificateValidator = updateGiftCertificateValidator;
        this.filterConditionValidator = filterConditionValidator;
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        if (!giftCertificateValidator.validate(giftCertificateDto)) {
            throw new ServiceException("gift.certificate.validate.error");
        }
        if (giftCertificateRepository.existsGiftCertificateByName(giftCertificateDto.getName())) {
            throw new ServiceException("resource.already.exist", "GIFT_CERTIFICATE");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        giftCertificateDto.setCreateDate(localDateTime);
        giftCertificateDto.setLastUpdateDate(localDateTime);
        GiftCertificate giftCertificate = giftCertificateDtoConverter.convertDtoToEntity(giftCertificateDto);
        giftCertificate.setTags(fetchAssociatedTags(giftCertificate.getTags()));
        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificate);
        return giftCertificateDtoConverter.convertDtoFromEntity(savedGiftCertificate);
    }

    private List<Tag> fetchAssociatedTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> Optional.ofNullable(tagRepository.findByName(tag.getName()))
                        .orElseGet(() -> tagRepository.save(tag)))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findById(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error");
        }
        Optional<GiftCertificate> certificateOptional = Optional.ofNullable(giftCertificateRepository.findById(id));
        return certificateOptional.map(giftCertificateDtoConverter::convertDtoFromEntity)
                .orElseThrow(() -> new ServiceException("gift.certificate.not.found", id));
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return giftCertificateRepository.findAll()
                .stream().map(giftCertificateDtoConverter::convertDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findWithFilter(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        if (!filterConditionValidator.validate(giftCertificateFilterCondition)) {
            throw new ServiceException("gift.certificate.filter.condition.validate.error");
        }
        return giftCertificateRepository.findWithFilter(giftCertificateFilterCondition)
                .stream().map(giftCertificateDtoConverter::convertDtoFromEntity).distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        if (!updateGiftCertificateValidator.validate(giftCertificateDto)) {
            throw new ServiceException("gift.certificate.update.condition.error");
        }
        if (giftCertificateRepository.findById(giftCertificateDto.getId()) == null) {
            throw new ServiceException("gift.certificate.not.found");
        }
        GiftCertificate giftCertificate = giftCertificateDtoConverter.convertDtoToEntity(giftCertificateDto);
        GiftCertificateUpdateCondition updateCondition = new GiftCertificateUpdateCondition(giftCertificate.getId(), giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getTags());
        GiftCertificate preUpdateGiftCertificate = createPreUpdateGiftCertificate(giftCertificateRepository.findById(giftCertificateDto.getId()), updateCondition);
        return giftCertificateDtoConverter.convertDtoFromEntity(giftCertificateRepository.update(preUpdateGiftCertificate));
    }

    public GiftCertificate createPreUpdateGiftCertificate(GiftCertificate certificate, GiftCertificateUpdateCondition giftCertificateUpdateCondition) {
        if (giftCertificateUpdateCondition.getName() != null) {
            if (giftCertificateRepository.existsGiftCertificateByName(giftCertificateUpdateCondition.getName())) {
                throw new ServiceException("resource.already.exist", "GIFT_CERTIFICATE");
            }
            certificate.setName(giftCertificateUpdateCondition.getName());
        }
        if (giftCertificateUpdateCondition.getDescription() != null) {
            certificate.setDescription(giftCertificateUpdateCondition.getDescription());
        }
        if (giftCertificateUpdateCondition.getPrice() != null) {
            certificate.setPrice(giftCertificateUpdateCondition.getPrice());
        }
        if (giftCertificateUpdateCondition.getDuration() != null) {
            certificate.setDuration(giftCertificateUpdateCondition.getDuration());
        }
        if (giftCertificateUpdateCondition.getTags() != null) {
            certificate.setTags(fetchAssociatedTags(giftCertificateUpdateCondition.getTags()));
        }
        certificate.setLastUpdateDate(LocalDateTime.now());
        return certificate;
    }

    @Override
    public void delete(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error");
        }
        giftCertificateRepository.delete(id);
    }
}
