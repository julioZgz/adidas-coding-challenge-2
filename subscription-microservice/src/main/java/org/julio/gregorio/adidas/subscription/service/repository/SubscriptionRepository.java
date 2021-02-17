package org.julio.gregorio.adidas.subscription.service.repository;

import org.julio.gregorio.adidas.subscription.service.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}