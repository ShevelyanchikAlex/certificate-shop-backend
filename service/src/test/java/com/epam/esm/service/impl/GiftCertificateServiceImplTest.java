package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.serialization.DtoSerializer;
import com.epam.esm.dto.serialization.impl.GiftCertificateSerializer;
import com.epam.esm.dto.serialization.impl.TagSerializer;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.filter.condition.GiftCertificateFilterCondition;
import com.epam.esm.repository.filter.condition.SortDirection;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.impl.FilterConditionValidator;
import com.epam.esm.service.validator.impl.GiftCertificateValidator;
import com.epam.esm.service.validator.impl.IdValidator;
import com.epam.esm.service.validator.impl.UpdateGiftCertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GiftCertificateServiceImplTest {
    private static final List<Tag> TEST_TAGS = Arrays.asList(
            new Tag(1L, "#tag1"),
            new Tag(2L, "#tag2"),
            new Tag(3L, "#tag3"));

    private static final List<TagDto> TEST_TAGS_DTO = Arrays.asList(
            new TagDto(1L, "#tag1"),
            new TagDto(2L, "#tag2"),
            new TagDto(3L, "#tag3"));

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 5, 3, 4, 30);

    private static final Set<TagDto> GIFT_CERTIFICATE_1_TAGS_DTO = Set.of(TEST_TAGS_DTO.get(0), TEST_TAGS_DTO.get(1));
    private static final Set<TagDto> GIFT_CERTIFICATE_2_TAGS_DTO = Set.of(TEST_TAGS_DTO.get(1), TEST_TAGS_DTO.get(2));

    private static final List<GiftCertificate> TEST_GIFT_CERTIFICATES = List.of(
            new GiftCertificate(1L, "Gift Certificate first", "Description first", 11, 1,
                    DATE_TIME, DATE_TIME),
            new GiftCertificate(2L, "Gift Certificate second", "DescriptionUpd second", 12, 22,
                    DATE_TIME, DATE_TIME),
            new GiftCertificate(3L, "Gift Certificate third", "DescriptionUpd third", 20, 2,
                    DATE_TIME, DATE_TIME)
    );

    private static final List<GiftCertificateDto> TEST_GIFT_CERTIFICATES_DTO =
            List.of(new GiftCertificateDto(TEST_GIFT_CERTIFICATES.get(0).getId(), TEST_GIFT_CERTIFICATES.get(0).getName(),
                            TEST_GIFT_CERTIFICATES.get(0).getDescription(), TEST_GIFT_CERTIFICATES.get(0).getPrice(),
                            TEST_GIFT_CERTIFICATES.get(0).getDuration(), TEST_GIFT_CERTIFICATES.get(0).getCreateDate(),
                            TEST_GIFT_CERTIFICATES.get(0).getLastUpdateDate(), new HashSet<>()),
                    new GiftCertificateDto(TEST_GIFT_CERTIFICATES.get(1).getId(), TEST_GIFT_CERTIFICATES.get(1).getName(),
                            TEST_GIFT_CERTIFICATES.get(1).getDescription(), TEST_GIFT_CERTIFICATES.get(1).getPrice(),
                            TEST_GIFT_CERTIFICATES.get(1).getDuration(), TEST_GIFT_CERTIFICATES.get(1).getCreateDate(),
                            TEST_GIFT_CERTIFICATES.get(1).getLastUpdateDate(), GIFT_CERTIFICATE_1_TAGS_DTO),
                    new GiftCertificateDto(TEST_GIFT_CERTIFICATES.get(2).getId(), TEST_GIFT_CERTIFICATES.get(2).getName(),
                            TEST_GIFT_CERTIFICATES.get(2).getDescription(), TEST_GIFT_CERTIFICATES.get(2).getPrice(),
                            TEST_GIFT_CERTIFICATES.get(2).getDuration(), TEST_GIFT_CERTIFICATES.get(2).getCreateDate(),
                            TEST_GIFT_CERTIFICATES.get(2).getLastUpdateDate(), GIFT_CERTIFICATE_2_TAGS_DTO));

    private GiftCertificateService giftCertificateService;
    private final GiftCertificateRepository giftCertificateRepositoryMock = Mockito.mock(GiftCertificateRepository.class);
    private final TagRepository tagRepositoryMock = Mockito.mock(TagRepository.class);
    private final DtoSerializer<GiftCertificateDto, GiftCertificate> giftCertificateDtoSerializer = new GiftCertificateSerializer();
    private final DtoSerializer<TagDto, Tag> tagDtoSerializer = new TagSerializer();
    private final GiftCertificateValidator giftCertificateValidator = new GiftCertificateValidator();
    private final IdValidator idValidator = new IdValidator();
    private final UpdateGiftCertificateValidator updateGiftCertificateValidator = new UpdateGiftCertificateValidator();
    private final FilterConditionValidator filterConditionValidator = new FilterConditionValidator();

    @BeforeEach
    public void setUp() {
        giftCertificateService = new GiftCertificateServiceImpl(
                giftCertificateRepositoryMock, tagRepositoryMock,
                giftCertificateDtoSerializer, tagDtoSerializer, giftCertificateValidator, idValidator,
                updateGiftCertificateValidator, filterConditionValidator);
    }

    @Test
    void save() {
        //given
        Mockito.when(giftCertificateRepositoryMock.save(Mockito.any(GiftCertificate.class))).thenReturn(TEST_GIFT_CERTIFICATES.get(0));
        String expectedName = TEST_GIFT_CERTIFICATES.get(0).getName();
        String expectedDescription = TEST_GIFT_CERTIFICATES.get(0).getDescription();
        //when
        GiftCertificateDto savedGiftCertificate = giftCertificateService.save(TEST_GIFT_CERTIFICATES_DTO.get(0));
        String actualName = savedGiftCertificate.getName();
        String actualDescription = savedGiftCertificate.getDescription();
        //then
        Assertions.assertEquals(expectedName, actualName);
        Assertions.assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void findById() {
        //given
        Mockito.when(giftCertificateRepositoryMock.findById(1L)).thenReturn(TEST_GIFT_CERTIFICATES.get(0));
        //when
        GiftCertificateDto actual = giftCertificateService.findById(1L);
        //then
        Mockito.verify(giftCertificateRepositoryMock).findById(1L);
        Assertions.assertEquals(TEST_GIFT_CERTIFICATES_DTO.get(0), actual);
    }

    @Test
    void findAll() {
        //given
        Mockito.when(giftCertificateRepositoryMock.findAll()).thenReturn(TEST_GIFT_CERTIFICATES);
        //when
        List<GiftCertificateDto> actual = giftCertificateService.findAll();
        //then
        Mockito.verify(giftCertificateRepositoryMock).findAll();
        Assertions.assertEquals(TEST_GIFT_CERTIFICATES.size(), actual.size());
    }

    @Test
    void findWithFilter() {
        //given
        GiftCertificateFilterCondition giftCertificateFilterCondition = new GiftCertificateFilterCondition();
        giftCertificateFilterCondition.setDescription("Description");
        giftCertificateFilterCondition.setSortDirection(SortDirection.ASC);
        Mockito.when(giftCertificateRepositoryMock.findWithFilter(giftCertificateFilterCondition)).thenReturn(TEST_GIFT_CERTIFICATES);
        //when
        List<GiftCertificateDto> actual = giftCertificateService.findWithFilter(giftCertificateFilterCondition);
        //then
        Mockito.verify(giftCertificateRepositoryMock).findWithFilter(giftCertificateFilterCondition);
        Assertions.assertEquals(TEST_GIFT_CERTIFICATES.size(), actual.size());
    }

    @Test
    void update() {
        //given
        Mockito.when(giftCertificateRepositoryMock.update(TEST_GIFT_CERTIFICATES.get(0))).thenReturn(1);
        Mockito.when(giftCertificateRepositoryMock.findById(1L)).thenReturn(TEST_GIFT_CERTIFICATES.get(0));
        //when
        giftCertificateService.update(TEST_GIFT_CERTIFICATES_DTO.get(0));
        //then
        Mockito.verify(giftCertificateRepositoryMock, Mockito.times(1)).update(TEST_GIFT_CERTIFICATES.get(0));
    }

    @Test
    void delete() {
        //given
        Mockito.when(giftCertificateService.delete(1L)).thenReturn(1);
        //when
        int actual = giftCertificateService.delete(1L);
        //then
        Mockito.verify(giftCertificateRepositoryMock).delete(Mockito.anyLong());
        Assertions.assertEquals(1, actual);
    }

    @Test
    void updateNonExistingGiftCertificateTest() {
        //given
        Mockito.when(giftCertificateRepositoryMock.findById(1L)).thenThrow(ServiceException.class);
        //then
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.update(TEST_GIFT_CERTIFICATES_DTO.get(0)));
    }

    @Test
    void deleteNonExistingGiftCertificateTest() {
        //given
        Mockito.when(giftCertificateRepositoryMock.delete(1L)).thenThrow(ServiceException.class);
        //then
        Assertions.assertThrows(ServiceException.class, () -> giftCertificateService.delete(1L));
    }
}