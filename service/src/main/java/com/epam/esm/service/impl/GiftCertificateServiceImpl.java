package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.mapper.GiftCertificateMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final IdValidator idValidator;
    private final UpdateGiftCertificateValidator updateGiftCertificateValidator;
    private final FilterConditionValidator filterConditionValidator;

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
        GiftCertificate giftCertificate = giftCertificateMapper.toEntity(giftCertificateDto);
        giftCertificate.setTags(fetchAssociatedTags(giftCertificate.getTags()));
        GiftCertificate savedGiftCertificate = giftCertificateRepository.save(giftCertificate);
        return giftCertificateMapper.toDto(savedGiftCertificate);
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error");
        }
        Optional<GiftCertificate> certificateOptional = Optional.ofNullable(giftCertificateRepository.findById(id));
        return certificateOptional.map(giftCertificateMapper::toDto)
                .orElseThrow(() -> new ServiceException("gift.certificate.not.found", id));
    }

    @Override
    public Page<GiftCertificateDto> findAll(Pageable pageable) {
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateRepository.findAll(pageable)
                .stream().map(giftCertificateMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(giftCertificateDtoList, pageable, giftCertificateRepository.countAll());
    }

    @Override
    public Page<GiftCertificateDto> findWithFilter(Pageable pageable, GiftCertificateFilterCondition giftCertificateFilterCondition) {
        if (!filterConditionValidator.validate(giftCertificateFilterCondition)) {
            throw new ServiceException("gift.certificate.filter.condition.validate.error");
        }
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateRepository.findWithFilter(pageable, giftCertificateFilterCondition)
                .stream().map(giftCertificateMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(giftCertificateDtoList, pageable, giftCertificateRepository.countAll());
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
        GiftCertificate giftCertificate = giftCertificateMapper.toEntity(giftCertificateDto);
        GiftCertificateUpdateCondition updateCondition = new GiftCertificateUpdateCondition(giftCertificate.getId(), giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getTags());
        GiftCertificate preUpdateGiftCertificate = createPreUpdateGiftCertificate(giftCertificateRepository.findById(giftCertificateDto.getId()), updateCondition);
        return giftCertificateMapper.toDto(giftCertificateRepository.update(preUpdateGiftCertificate));
    }

    @Transactional
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
    @Transactional
    public void delete(Long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException("request.validate.error");
        }
        giftCertificateRepository.delete(id);
    }

    private List<Tag> fetchAssociatedTags(List<Tag> tags) {
        return tags.stream()
                .map(tag -> Optional.ofNullable(tagRepository.findByName(tag.getName()))
                        .orElseGet(() -> tagRepository.save(tag)))
                .collect(Collectors.toList());
    }
}
