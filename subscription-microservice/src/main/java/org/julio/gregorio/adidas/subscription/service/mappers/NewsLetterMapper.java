package org.julio.gregorio.adidas.subscription.service.mappers;

import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterDTO;
import org.julio.gregorio.adidas.subscription.service.dto.NewsLetterRequestDTO;
import org.julio.gregorio.adidas.subscription.service.model.NewsLetter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NewsLetterMapper extends EntityMapper<NewsLetterDTO, NewsLetter> {

	@Mappings({ @Mapping(target = "newsLetterId", source = "id") })
	NewsLetterDTO toDto(NewsLetter entity);

	@Mappings({ @Mapping(target = "id", source = "newsLetterId") })
	NewsLetter toEntity(NewsLetterDTO dto);

	NewsLetter toEntity(NewsLetterRequestDTO newsLetterRequestDTO);

}