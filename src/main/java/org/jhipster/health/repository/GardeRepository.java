package org.jhipster.health.repository;

import org.jhipster.health.domain.Garde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Garde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GardeRepository extends JpaRepository<Garde, Long>, JpaSpecificationExecutor<Garde> {

}
