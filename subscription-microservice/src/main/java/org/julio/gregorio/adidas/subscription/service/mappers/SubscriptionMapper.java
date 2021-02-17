package org.julio.gregorio.adidas.subscription.service.mappers;

import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionDTO;
import org.julio.gregorio.adidas.subscription.service.dto.SubscriptionRequestDTO;
import org.julio.gregorio.adidas.subscription.service.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { NewsLetterMapper.class
		// , GenreMapper.class
})
public interface SubscriptionMapper extends EntityMapper<SubscriptionDTO, Subscription> {

	SubscriptionDTO toDto(Subscription entity);

	Subscription toEntity(SubscriptionDTO dto);

	@Mappings({ @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "yyyy-MM-dd") })
	Subscription toEntity(SubscriptionRequestDTO subscriptionRequestDTO);

}