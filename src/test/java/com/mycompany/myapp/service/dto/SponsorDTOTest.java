package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SponsorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SponsorDTO.class);
        SponsorDTO sponsorDTO1 = new SponsorDTO();
        sponsorDTO1.setId(1L);
        SponsorDTO sponsorDTO2 = new SponsorDTO();
        assertThat(sponsorDTO1).isNotEqualTo(sponsorDTO2);
        sponsorDTO2.setId(sponsorDTO1.getId());
        assertThat(sponsorDTO1).isEqualTo(sponsorDTO2);
        sponsorDTO2.setId(2L);
        assertThat(sponsorDTO1).isNotEqualTo(sponsorDTO2);
        sponsorDTO1.setId(null);
        assertThat(sponsorDTO1).isNotEqualTo(sponsorDTO2);
    }
}
