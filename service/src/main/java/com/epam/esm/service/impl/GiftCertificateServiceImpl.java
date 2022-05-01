package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceErrorCode;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implemented {@link GiftCertificateService}
 */
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
                                      @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer, GiftCertificateValidator giftCertificateValidator,
                                      IdValidator idValidator, UpdateGiftCertificateValidator updateGiftCertificateValidator, FilterConditionValidator filterConditionValidator) {
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
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        if (!giftCertificateValidator.validate(giftCertificateDto)) {
            throw new ServiceException(ServiceErrorCode.GIFT_CERTIFICATE_VALIDATE_ERROR);
        }
        if (giftCertificateRepository.existsGiftCertificateByName(giftCertificateDto.getName())) {
            throw new ServiceException(ServiceErrorCode.RESOURCE_ALREADY_EXIST, "GIFT_CERTIFICATE");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        giftCertificateDto.setCreateDate(localDateTime);
        giftCertificateDto.setLastUpdateDate(localDateTime);
        GiftCertificate giftCertificate = giftCertificateDtoSerializer.serializeDtoToEntity(giftCertificateDto);
        GiftCertificateDto savedGiftCertificateDto = giftCertificateDtoSerializer.serializeDtoFromEntity(giftCertificateRepository.save(giftCertificate));

        Set<Tag> tagSet = fetchTagSet(Optional.ofNullable(giftCertificateDto.getTagSet()).orElse(new HashSet<>())
                .stream().map(tagDtoSerializer::serializeDtoToEntity).collect(Collectors.toSet()));
        associateGiftCertificateWithTag(savedGiftCertificateDto.getId(), tagSet);
        savedGiftCertificateDto.setTagSet(tagSet.stream().map(tagDtoSerializer::serializeDtoFromEntity).collect(Collectors.toSet()));
        return savedGiftCertificateDto;
    }


    @Override
    public GiftCertificateDto findById(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException(ServiceErrorCode.REQUEST_VALIDATE_ERROR);
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
    public List<GiftCertificateDto> findWithFilter(GiftCertificateFilterCondition giftCertificateFilterCondition) {
        if (!filterConditionValidator.validate(giftCertificateFilterCondition)) {
            throw new ServiceException(ServiceErrorCode.FILTER_CONDITION_VALIDATE_ERROR);
        }
        List<GiftCertificateDto> giftCertificateDtoSet = giftCertificateRepository.findWithFilter(giftCertificateFilterCondition)
                .stream().map(giftCertificateDtoSerializer::serializeDtoFromEntity)
                .distinct().collect(Collectors.toList());
        addTagSetToGiftCertificateDto(giftCertificateDtoSet);
        return giftCertificateDtoSet;
    }

    @Override
    @Transactional
    public int update(GiftCertificateDto giftCertificateDto) {
        if (!updateGiftCertificateValidator.validate(giftCertificateDto)) {
            throw new ServiceException(ServiceErrorCode.UPDATE_CONDITION_VALIDATE_ERROR);
        }
        if (giftCertificateRepository.findById(giftCertificateDto.getId()) == null) {
            throw new ServiceException(ServiceErrorCode.GIFT_CERTIFICATE_NOT_FOUND);
        }
        int updatedRowsCount = giftCertificateRepository.update(giftCertificateDtoSerializer.serializeDtoToEntity(giftCertificateDto));

        Set<Tag> tagSet = fetchTagSet(Optional.ofNullable(giftCertificateDto.getTagSet()).orElse(new HashSet<>())
                .stream().map(tagDtoSerializer::serializeDtoToEntity).collect(Collectors.toSet()));
        associateGiftCertificateWithTag(giftCertificateDto.getId(), tagSet);
        return updatedRowsCount;
    }

    @Override
    public int delete(long id) {
        if (!idValidator.validate(id)) {
            throw new ServiceException(ServiceErrorCode.REQUEST_VALIDATE_ERROR);
        }
        return giftCertificateRepository.delete(id);
    }

    /**
     * Adds Set of Tags to GiftCertificateDto
     *
     * @param giftCertificateDtoList List of GiftCertificateDto
     */
    private void addTagSetToGiftCertificateDto(List<GiftCertificateDto> giftCertificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : giftCertificateDtoList) {
            giftCertificateDto.setTagSet(tagRepository.findAllByGiftCertificateId(giftCertificateDto.getId())
                    .stream().map(tagDtoSerializer::serializeDtoFromEntity).collect(Collectors.toSet()));
        }
    }

    /**
     * Fetch Set of Tag.
     * Saves Tag if it not exists and adds to Set.
     *
     * @param tagSet New set of Tag
     * @return Set of Tag
     */
    private Set<Tag> fetchTagSet(Set<Tag> tagSet) {
        return tagSet.stream()
                .map(tag -> {
                    if (!tagRepository.existsTagByName(tag.getName())) {
                        tagRepository.save(tag);
                    }
                    return tagRepository.findByName(tag.getName());
                }).collect(Collectors.toSet());
    }

    /**
     * Associate id of GiftCertificate and ids of Tags
     *
     * @param giftCertificateId id of GiftCertificate
     * @param tagSet            Set of Tag
     */
    private void associateGiftCertificateWithTag(long giftCertificateId, Set<Tag> tagSet) {
        Set<Tag> currentAssociateGiftCertificateWithTag = tagRepository.findAllByGiftCertificateId(giftCertificateId);
        if (currentAssociateGiftCertificateWithTag.isEmpty()) {
            tagSet.forEach(tag -> giftCertificateRepository.associateGiftCertificateWithTag(giftCertificateId, tag.getId()));
        } else {
            resolveAssociationsOfGiftCertificateWithTag(giftCertificateId, currentAssociateGiftCertificateWithTag, tagSet);
        }
    }

    /**
     * Deassociate id of GiftCertificate and iв of Tag if the new set no longer contains the old tags otherwise associate
     *
     * @param giftCertificateId                      id Of GiftCertificate
     * @param currentAssociateGiftCertificateWithTag prev association of GiftCertificates and Tags
     * @param tagSet                                 Set of Tag
     */
    private void resolveAssociationsOfGiftCertificateWithTag(long giftCertificateId, Set<Tag> currentAssociateGiftCertificateWithTag, Set<Tag> tagSet) {
        currentAssociateGiftCertificateWithTag.forEach(tag -> {
            if (!tagSet.contains(tag)) {
                giftCertificateRepository.deAssociateGiftCertificateWithTag(giftCertificateId, tag.getId());
            }
        });
        tagSet.forEach(tag -> {
            if (!currentAssociateGiftCertificateWithTag.contains(tag)) {
                giftCertificateRepository.associateGiftCertificateWithTag(giftCertificateId, tag.getId());
            }
        });
    }
}
