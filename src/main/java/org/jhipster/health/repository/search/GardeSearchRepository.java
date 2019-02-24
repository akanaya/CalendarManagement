package org.jhipster.health.repository.search;

import org.jhipster.health.domain.Garde;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Garde entity.
 */
public interface GardeSearchRepository extends ElasticsearchRepository<Garde, Long> {
}
