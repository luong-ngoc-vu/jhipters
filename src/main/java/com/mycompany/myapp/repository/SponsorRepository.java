package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sponsor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sponsor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
