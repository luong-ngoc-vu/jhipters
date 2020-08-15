package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SponsorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sponsor} and its DTO {@link SponsorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SponsorMapper extends EntityMapper<SponsorDTO, Sponsor> {


    @Mapping(target = "books", ignore = true)
    @Mapping(target = "removeBook", ignore = true)
    Sponsor toEntity(SponsorDTO sponsorDTO);

    default Sponsor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sponsor sponsor = new Sponsor();
        sponsor.setId(id);
        return sponsor;
    }
}
