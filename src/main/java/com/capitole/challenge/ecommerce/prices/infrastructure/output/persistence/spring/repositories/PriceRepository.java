package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.repositories;

import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long>, PriceRepositoryCustom {
}
