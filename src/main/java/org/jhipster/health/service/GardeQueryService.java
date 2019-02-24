package org.jhipster.health.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.jhipster.health.domain.Garde;
import org.jhipster.health.domain.*; // for static metamodels
import org.jhipster.health.repository.GardeRepository;
import org.jhipster.health.repository.search.GardeSearchRepository;
import org.jhipster.health.service.dto.GardeCriteria;

/**
 * Service for executing complex queries for Garde entities in the database.
 * The main input is a {@link GardeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Garde} or a {@link Page} of {@link Garde} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GardeQueryService extends QueryService<Garde> {

    private final Logger log = LoggerFactory.getLogger(GardeQueryService.class);

    private final GardeRepository gardeRepository;

    private final GardeSearchRepository gardeSearchRepository;

    public GardeQueryService(GardeRepository gardeRepository, GardeSearchRepository gardeSearchRepository) {
        this.gardeRepository = gardeRepository;
        this.gardeSearchRepository = gardeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Garde} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Garde> findByCriteria(GardeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Garde> specification = createSpecification(criteria);
        return gardeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Garde} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Garde> findByCriteria(GardeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Garde> specification = createSpecification(criteria);
        return gardeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GardeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Garde> specification = createSpecification(criteria);
        return gardeRepository.count(specification);
    }

    /**
     * Function to convert GardeCriteria to a {@link Specification}
     */
    private Specification<Garde> createSpecification(GardeCriteria criteria) {
        Specification<Garde> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Garde_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Garde_.date));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Garde_.amount));
            }
            if (criteria.getSeller_residant() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeller_residant(), Garde_.seller_residant));
            }
            if (criteria.getBuyer_resident() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuyer_resident(), Garde_.buyer_resident));
            }
        }
        return specification;
    }
}
