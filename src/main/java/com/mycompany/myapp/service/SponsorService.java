package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Sponsor;
import com.mycompany.myapp.repository.SponsorRepository;
import com.mycompany.myapp.service.dto.SponsorDTO;
import com.mycompany.myapp.service.mapper.SponsorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sponsor}.
 */
@Service
@Transactional
public class SponsorService {

    private final Logger log = LoggerFactory.getLogger(SponsorService.class);

    private final SponsorRepository sponsorRepository;

    private final SponsorMapper sponsorMapper;

    public SponsorService(SponsorRepository sponsorRepository, SponsorMapper sponsorMapper) {
        this.sponsorRepository = sponsorRepository;
        this.sponsorMapper = sponsorMapper;
    }

    /**
     * Save a sponsor.
     *
     * @param sponsorDTO the entity to save.
     * @return the persisted entity.
     */
    public SponsorDTO save(SponsorDTO sponsorDTO) {
        log.debug("Request to save Sponsor : {}", sponsorDTO);
        Sponsor sponsor = sponsorMapper.toEntity(sponsorDTO);
        sponsor = sponsorRepository.save(sponsor);
        return sponsorMapper.toDto(sponsor);
    }

    /**
     * Get all the sponsors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SponsorDTO> findAll() {
        log.debug("Request to get all Sponsors");
        return sponsorRepository.findAll().stream()
            .map(sponsorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sponsor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SponsorDTO> findOne(Long id) {
        log.debug("Request to get Sponsor : {}", id);
        return sponsorRepository.findById(id)
            .map(sponsorMapper::toDto);
    }

    /**
     * Delete the sponsor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sponsor : {}", id);
        sponsorRepository.deleteById(id);
    }
}
