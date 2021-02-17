package org.julio.gregorio.adidas.subscription.service.mappers;

import java.util.List;

public interface EntityMapper<D, E> {

	E toEntity(D dto);
	
	D toDto(E entity);
	
	List<E> toEntity(List<D> dtoList);
	
	List<D> toDto(List<E> dtoList);
	
}
