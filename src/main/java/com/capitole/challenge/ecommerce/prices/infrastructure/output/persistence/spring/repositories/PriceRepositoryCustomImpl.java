package com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.repositories;

import com.capitole.challenge.ecommerce.prices.infrastructure.output.persistence.spring.entities.PriceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class PriceRepositoryCustomImpl implements PriceRepositoryCustom {

    private final EntityManager entityManager;

    String findPrice = """
            select p from PriceEntity p 
            join fetch p.product pr 
            join fetch pr.brand b 
            where
            pr.id = :productId
            and b.id = :brandId
            and p.startDate < :applicationDate
            and p.endDate > :applicationDate
            order by p.priority desc
        """;

    /**
     * Finds a price using parameters. Only retrieves the first occurrence sorted by the Priority field.
     * In this way, we ensure to retrieve the real price if there is one or more prices for the same period of time.
     * @param productId
     * @param brandId
     * @param applicationDate
     * @return
     */
    @Override
    public Optional<PriceEntity> findPriceByDate(Long brandId, Long productId, LocalDateTime applicationDate) {
        Query query = entityManager.createQuery(findPrice, PriceEntity.class)
                .setParameter("brandId", brandId)
                .setParameter("productId", productId)
                .setParameter("applicationDate", applicationDate)
                .setMaxResults(1);
        PriceEntity price = (PriceEntity) query.getSingleResult();
        return Optional.ofNullable(price);
    }
}
