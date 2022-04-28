package com.epam.esm.dto.serialization;

/**
 * Interface {@link DtoSerializer} needs for serialization DTO from Entity and vice versa.
 *
 * @param <D> DTO
 * @param <E> Entity
 *
 * @author Alex Shevelyanchik
 * @version 1.0
 */
public interface DtoSerializer<D, E> {
    /**
     * Serializes DTO from Entity
     *
     * @param entity Entity to serialize
     * @return DTO serialized from entity
     */
    D serializeDtoFromEntity(E entity);

    /**
     * Serializes Entity from DTO
     *
     * @param dto DTO to serialize
     * @return Entity serialized from DTO
     */
    E serializeDtoToEntity(D dto);
}
