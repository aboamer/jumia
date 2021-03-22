package com.phonevalidator.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

    E convertDtoToEntity(D dto);

    D convertEntityToDto(E entity);

    List<E> convertDtosTOEntities(List<D> dtos);

    List<D> convertEntitiesToDtos(List<E> entities);
}
