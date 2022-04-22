package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.filter.FilterCondition;
import com.epam.esm.service.GiftCertificateService;
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

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      @Qualifier("giftCertificateDtoSerializer") DtoSerializer<GiftCertificateDto, GiftCertificate> giftCertificateDtoSerializer,
                                      @Qualifier("tagDtoSerializer") DtoSerializer<TagDto, Tag> tagDtoSerializer) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateDtoSerializer = giftCertificateDtoSerializer;
        this.tagDtoSerializer = tagDtoSerializer;
    }

    @Override
    @Transactional
    public long save(GiftCertificateDto giftCertificateDto) {
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
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateRepository.findWithFilter(filterCondition)
                .stream().map(giftCertificateDtoSerializer::serializeDtoFromEntity).collect(Collectors.toList());
        addTagSetToGiftCertificateDto(giftCertificateDtoList);
        return giftCertificateDtoList;
    }

    @Override
    @Transactional
    public int update(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificateToUpdate = Optional.ofNullable(giftCertificateRepository.findById(giftCertificateDto.getId()))
                .orElseThrow(() -> new ServiceException("40401", giftCertificateDto.getId()));
        updateNecessaryParams(giftCertificateDto, giftCertificateToUpdate);
        associateGiftCertificateWithTag(giftCertificateDto.getId(),
                fetchTagSet(giftCertificateDto.getTagSet()
                        .stream().map(tagDtoSerializer::serializeDtoToEntity).collect(Collectors.toSet())));
        return giftCertificateRepository.update(giftCertificateToUpdate);
    }

    private void updateNecessaryParams(GiftCertificateDto giftCertificateDto, GiftCertificate giftCertificate) {
        Optional.ofNullable(giftCertificateDto.getName()).ifPresent(giftCertificate::setName);
        Optional.ofNullable(giftCertificateDto.getDescription()).ifPresent(giftCertificate::setDescription);
        Optional.of(giftCertificateDto.getDuration()).ifPresent(giftCertificate::setDuration);
        Optional.of(giftCertificateDto.getPrice()).ifPresent(giftCertificate::setPrice);
        Optional.of(LocalDateTime.now()).ifPresent(giftCertificate::setLastUpdateDate);
    }

    @Override
    public int delete(long id) {
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
