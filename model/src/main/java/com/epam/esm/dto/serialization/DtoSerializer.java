package com.epam.esm.dto.serialization;

public interface DtoSerializer<D, E> {
    D serializeDtoFromEntity(E entity);

    E serializeDtoToEntity(D dto);
}
