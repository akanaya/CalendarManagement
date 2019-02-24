package org.jhipster.health.service.impl;

import org.jhipster.health.service.GardeService;
import org.jhipster.health.domain.Garde;
import org.jhipster.health.repository.GardeRepository;
import org.jhipster.health.repository.search.GardeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Garde.
 */
@Service
@Transactional
public class GardeServiceImpl implements GardeService {

    private final Logger log = LoggerFactory.getLogger(GardeServiceImpl.class);

    private final GardeRepository gardeRepository;

    private final GardeSearchRepository gardeSearchRepository;

    public GardeServiceImpl(GardeRepository gardeRepository, GardeSearchRepository gardeSearchRepository) {
        this.gardeRepository = gardeRepository;
        this.gardeSearchRepository = gardeSearchRepository;
    }

    /**
     * Save a garde.
     *
     * @param garde the entity to save
     * @return the persisted entity
     */
    @Override
    public Garde save(Garde garde) {
        log.debug("Request to save Garde : {}", garde);
        Garde result = gardeRepository.save(garde);
        gardeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the gardes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Garde> findAll(Pageable pageable) {
        log.debug("Request to get all Gardes");
        return gardeRepository.findAll(pageable);
    }


    /**
     * Get one garde by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Garde> findOne(Long id) {
        log.debug("Request to get Garde : {}", id);
        return gardeRepository.findById(id);
    }

    /**
     * Delete the garde by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Garde : {}", id);
        gardeRepository.deleteById(id);
        gardeSearchRepository.deleteById(id);
    }

    /**
     * Search for the garde corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Garde> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Gardes for query {}", query);
        return gardeSearchRepository.search(queryStringQuery(query), pageable);    }
}
