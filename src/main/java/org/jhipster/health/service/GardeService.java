package org.jhipster.health.service;

import org.jhipster.health.domain.Garde;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Garde.
 */
public interface GardeService {

    /**
     * Save a garde.
     *
     * @param garde the entity to save
     * @return the persisted entity
     */
    Garde save(Garde garde);

    /**
     * Get all the gardes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Garde> findAll(Pageable pageable);


    /**
     * Get the "id" garde.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Garde> findOne(Long id);

    /**
     * Delete the "id" garde.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the garde corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Garde> search(String query, Pageable pageable);
}
