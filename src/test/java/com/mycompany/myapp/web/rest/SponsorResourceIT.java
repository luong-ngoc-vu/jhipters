package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;
import com.mycompany.myapp.domain.Sponsor;
import com.mycompany.myapp.repository.SponsorRepository;
import com.mycompany.myapp.service.SponsorService;
import com.mycompany.myapp.service.dto.SponsorDTO;
import com.mycompany.myapp.service.mapper.SponsorMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SponsorResource} REST controller.
 */
@SpringBootTest(classes = MyappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SponsorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private SponsorMapper sponsorMapper;

    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSponsorMockMvc;

    private Sponsor sponsor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sponsor createEntity(EntityManager em) {
        Sponsor sponsor = new Sponsor()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE);
        return sponsor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sponsor createUpdatedEntity(EntityManager em) {
        Sponsor sponsor = new Sponsor()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);
        return sponsor;
    }

    @BeforeEach
    public void initTest() {
        sponsor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSponsor() throws Exception {
        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();
        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);
        restSponsorMockMvc.perform(post("/api/sponsors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isCreated());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeCreate + 1);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSponsor.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createSponsorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();

        // Create the Sponsor with an existing ID
        sponsor.setId(1L);
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSponsorMockMvc.perform(post("/api/sponsors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSponsors() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get all the sponsorList
        restSponsorMockMvc.perform(get("/api/sponsors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get the sponsor
        restSponsorMockMvc.perform(get("/api/sponsors/{id}", sponsor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sponsor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }
    @Test
    @Transactional
    public void getNonExistingSponsor() throws Exception {
        // Get the sponsor
        restSponsorMockMvc.perform(get("/api/sponsors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Update the sponsor
        Sponsor updatedSponsor = sponsorRepository.findById(sponsor.getId()).get();
        // Disconnect from session so that the updates on updatedSponsor are not directly saved in db
        em.detach(updatedSponsor);
        updatedSponsor
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE);
        SponsorDTO sponsorDTO = sponsorMapper.toDto(updatedSponsor);

        restSponsorMockMvc.perform(put("/api/sponsors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isOk());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSponsor.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsorMockMvc.perform(put("/api/sponsors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sponsorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeDelete = sponsorRepository.findAll().size();

        // Delete the sponsor
        restSponsorMockMvc.perform(delete("/api/sponsors/{id}", sponsor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
