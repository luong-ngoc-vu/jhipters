package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SponsorMapperTest {

    private SponsorMapper sponsorMapper;

    @BeforeEach
    public void setUp() {
        sponsorMapper = new SponsorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sponsorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sponsorMapper.fromId(null)).isNull();
    }
}
