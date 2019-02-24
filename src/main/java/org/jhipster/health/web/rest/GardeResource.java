package org.jhipster.health.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.health.domain.Garde;
import org.jhipster.health.service.GardeService;
import org.jhipster.health.web.rest.errors.BadRequestAlertException;
import org.jhipster.health.web.rest.util.HeaderUtil;
import org.jhipster.health.web.rest.util.PaginationUtil;
import org.jhipster.health.service.dto.GardeCriteria;
import org.jhipster.health.service.GardeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Garde.
 */
@RestController
@RequestMapping("/api")
public class GardeResource {

    private final Logger log = LoggerFactory.getLogger(GardeResource.class);

    private static final String ENTITY_NAME = "garde";

    private final GardeService gardeService;

    private final GardeQueryService gardeQueryService;

    public GardeResource(GardeService gardeService, GardeQueryService gardeQueryService) {
        this.gardeService = gardeService;
        this.gardeQueryService = gardeQueryService;
    }

    /**
     * POST  /gardes : Create a new garde.
     *
     * @param garde the garde to create
     * @return the ResponseEntity with status 201 (Created) and with body the new garde, or with status 400 (Bad Request) if the garde has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gardes")
    @Timed
    public ResponseEntity<Garde> createGarde(@Valid @RequestBody Garde garde) throws URISyntaxException {
        log.debug("REST request to save Garde : {}", garde);
        if (garde.getId() != null) {
            throw new BadRequestAlertException("A new garde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Garde result = gardeService.save(garde);
        return ResponseEntity.created(new URI("/api/gardes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gardes : Updates an existing garde.
     *
     * @param garde the garde to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated garde,
     * or with status 400 (Bad Request) if the garde is not valid,
     * or with status 500 (Internal Server Error) if the garde couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gardes")
    @Timed
    public ResponseEntity<Garde> updateGarde(@Valid @RequestBody Garde garde) throws URISyntaxException {
        log.debug("REST request to update Garde : {}", garde);
        if (garde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Garde result = gardeService.save(garde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, garde.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gardes : get all the gardes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of gardes in body
     */
    @GetMapping("/gardes")
    @Timed
    public ResponseEntity<List<Garde>> getAllGardes(GardeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Gardes by criteria: {}", criteria);
        Page<Garde> page = gardeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gardes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /gardes/count : count all the gardes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/gardes/count")
    @Timed
    public ResponseEntity<Long> countGardes (GardeCriteria criteria) {
        log.debug("REST request to count Gardes by criteria: {}", criteria);
        return ResponseEntity.ok().body(gardeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /gardes/:id : get the "id" garde.
     *
     * @param id the id of the garde to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the garde, or with status 404 (Not Found)
     */
    @GetMapping("/gardes/{id}")
    @Timed
    public ResponseEntity<Garde> getGarde(@PathVariable Long id) {
        log.debug("REST request to get Garde : {}", id);
        Optional<Garde> garde = gardeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(garde);
    }

    /**
     * DELETE  /gardes/:id : delete the "id" garde.
     *
     * @param id the id of the garde to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gardes/{id}")
    @Timed
    public ResponseEntity<Void> deleteGarde(@PathVariable Long id) {
        log.debug("REST request to delete Garde : {}", id);
        gardeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/gardes?query=:query : search for the garde corresponding
     * to the query.
     *
     * @param query the query of the garde search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/gardes")
    @Timed
    public ResponseEntity<List<Garde>> searchGardes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Gardes for query {}", query);
        Page<Garde> page = gardeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/gardes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
