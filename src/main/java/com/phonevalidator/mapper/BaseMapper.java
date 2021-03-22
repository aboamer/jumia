package com.phonevalidator.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDTOs(List<E> entities);
}
