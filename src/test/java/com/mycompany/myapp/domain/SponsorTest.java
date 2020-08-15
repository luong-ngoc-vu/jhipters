package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SponsorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sponsor.class);
        Sponsor sponsor1 = new Sponsor();
        sponsor1.setId(1L);
        Sponsor sponsor2 = new Sponsor();
        sponsor2.setId(sponsor1.getId());
        assertThat(sponsor1).isEqualTo(sponsor2);
        sponsor2.setId(2L);
        assertThat(sponsor1).isNotEqualTo(sponsor2);
        sponsor1.setId(null);
        assertThat(sponsor1).isNotEqualTo(sponsor2);
    }
}
