package org.julio.gregorio.adidas.subscription.service.repository;

import org.julio.gregorio.adidas.subscription.service.model.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {
	
}