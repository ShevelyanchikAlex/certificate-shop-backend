package com.epam.esm.dto.converter;

/**
 * Interface {@link DtoConverter} needs for convert DTO from Entity and vice versa.
 *
 * @param <D> DTO
 * @param <E> Entity
 *
 * @author Alex Shevelyanchik
 * @version 1.0
 */
public interface DtoConverter<D, E> {
    /**
     * Converts DTO from Entity
     *
     * @param entity Entity to convert
     * @return DTO converted from entity
     */
    D convertDtoFromEntity(E entity);

    /**
     * Converts Entity from DTO
     *
     * @param dto DTO to convert
     * @return Entity converted from DTO
     */
    E convertDtoToEntity(D dto);
}
