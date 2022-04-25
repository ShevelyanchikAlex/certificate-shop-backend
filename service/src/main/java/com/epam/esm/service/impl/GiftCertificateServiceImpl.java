package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.service.exception.ServiceErrorCode;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.filter.condition.FilterCondition;
import com.epam.esm.service.GiftCertificateService;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final DtoSerializer<GiftCertificateDto, GiftCertificate> giftCertificateDtoSerializer;
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer;
    private final GiftCertificateValidator giftCertificateValidator;
    private final IdValidator idValidator;
    private final UpdateGiftCertificateValidator updateGiftCertificateValidator;
    private final FilterConditionValidator filterConditionValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      @Qualifier("giftCertificateDtoSerializer") DtoSerializer<GiftCertificateDto, GiftCertificate> giftCertificateDtoSerializer,
                                      @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer, GiftCertificateValidator giftCertificateValidator, IdValidator idValidator, UpdateGiftCertificateValidator updateGiftCertificateValidator, FilterConditionValidator filterConditionValidator) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateDtoSerializer = giftCertificateDtoSerializer;
        this.tagDtoSerializer = tagDtoSerializer;
        this.giftCertificateValidator = giftCertificateValidator;
        this.idValidator = idValidator;
        this.updateGiftCertificateValidator = updateGiftCertificateValidator;
        this.filterConditionValidator = filterConditionValidator;
    }

    @Override
    @Transactional
    public long save(GiftCertificateDto giftCertificateDto) {
        if (!giftCertificateValidator.validate(giftCertificateDto)) {
            throw new ServiceException(ServiceErrorCode.GIFT_CERTIFICATE_VALIDATE_ERROR, giftCertificateDto.getId());
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        giftCertificateDto.setCreateDate(localDateTime);
        giftCertificateDto.setLastUpdateDate(localDateTime);
        GiftCertificate giftCertificate = giftCertificateDtoSerializer.serializeDtoToEntity(giftCertificateDto);
        long giftCertificateId = giftCertificateRepository.save(giftCertificate);
        associateGiftCertificateWithTag(giftCertificateId, fetchTagSet(giftCertificateDto.getTagSet()
                .stream().map(tagDtoSerializer::serializeDtoToEntity).collect(Collectors.toSet())));
        return giftCertificateId;
    }


    @Override
    public GiftCertificateDto findById(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException(ServiceErrorCode.REQUEST_VALIDATE_ERROR, id);
        }
        GiftCertificateDto giftCertificateDto = giftCertificateDtoSerializer.serializeDtoFromEntity(giftCertificateRepository.findById(id));
        giftCertificateDto.setTagSet(tagRepository.findAllByGiftCertificateId(giftCertificateDto.getId())
                .stream().map(tagDtoSerializer::serializeDtoFromEntity).collect(Collectors.toSet()));
        return giftCertificateDto;
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateRepository.findAll()
                .stream().map(giftCertificateDtoSerializer::serializeDtoFromEntity).collect(Collectors.toList());
        addTagSetToGiftCertificateDto(giftCertificateDtoList);
        return giftCertificateDtoList;
    }

    @Override
    public List<GiftCertificateDto> findWithFilter(FilterCondition filterCondition) {
        if (!filterConditionValidator.validate(filterCondition)) {
            throw new ServiceException(ServiceErrorCode.FILTER_CONDITION_VALIDATE_ERROR);
        }
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateRepository.findWithFilter(filterCondition)
                .stream().map(giftCertificateDtoSerializer::serializeDtoFromEntity).collect(Collectors.toList());
        addTagSetToGiftCertificateDto(giftCertificateDtoList);
        return giftCertificateDtoList;
    }

    @Override
    @Transactional
    public int update(GiftCertificateDto giftCertificateDto) {
        if (!updateGiftCertificateValidator.validate(giftCertificateDto)) {
            throw new ServiceException(ServiceErrorCode.UPDATE_CONDITION_VALIDATE_ERROR, giftCertificateDto.getId());
        }
        if (giftCertificateRepository.findById(giftCertificateDto.getId()) == null) {
            throw new ServiceException(ServiceErrorCode.GIFT_CERTIFICATE_NOT_FOUND, giftCertificateDto.getId());
        }
        int updatedRowsCount = giftCertificateRepository.update(giftCertificateDtoSerializer.serializeDtoToEntity(giftCertificateDto));
        associateGiftCertificateWithTag(giftCertificateDto.getId(), fetchTagSet(giftCertificateDto.getTagSet()
                .stream().map(tagDtoSerializer::serializeDtoToEntity).collect(Collectors.toSet())));
        return updatedRowsCount;
    }

    @Override
    public int delete(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException(ServiceErrorCode.REQUEST_VALIDATE_ERROR, id);
        }
        return giftCertificateRepository.delete(id);
    }

    private void addTagSetToGiftCertificateDto(List<GiftCertificateDto> giftCertificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : giftCertificateDtoList) {
            giftCertificateDto.setTagSet(tagRepository.findAllByGiftCertificateId(giftCertificateDto.getId())
                    .stream().map(tagDtoSerializer::serializeDtoFromEntity).collect(Collectors.toSet()));
        }
    }

    private Set<Tag> fetchTagSet(Set<Tag> tagSet) {
        return tagSet.stream()
                .map(tag -> Optional.ofNullable(tagRepository.findByName(tag.getName()))
                        .orElseGet(() -> {
                            tagRepository.save(tag);
                            return tagRepository.findByName(tag.getName());
                        }))
                .collect(Collectors.toSet());
    }

    private void associateGiftCertificateWithTag(long giftCertificateId, Set<Tag> tagSet) {
        Set<Tag> currentAssociateGiftCertificateWithTag = tagRepository.findAllByGiftCertificateId(giftCertificateId);
        for (Tag tag : tagSet) {
            if (!currentAssociateGiftCertificateWithTag.contains(tag)) {
                giftCertificateRepository.associateGiftCertificateWithTag(giftCertificateId, tag.getId());
            }
        }
    }
}
