/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.activedirectory.service_impl.mapper;

import java.util.List;

public interface BaseMapper<E, D> {
    E dtoToEntity(D dto);

    D entityToDto(E entity);

    List<E> dtoListToEntityList(List<D> dtoList);

    List<D> entityListToDtoList(List<E> entityList);
}
