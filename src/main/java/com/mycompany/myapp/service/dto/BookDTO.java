package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Book} entity.
 */
public class BookDTO implements Serializable {
    
    private Long id;

    private String title;

    private String description;

    private String content;

    private LocalDate releaseDate;

    private BigDecimal price;

    private Set<SponsorDTO> sponsors = new HashSet<>();

    private Long authorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<SponsorDTO> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<SponsorDTO> sponsors) {
        this.sponsors = sponsors;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDTO)) {
            return false;
        }

        return id != null && id.equals(((BookDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", content='" + getContent() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", price=" + getPrice() +
            ", sponsors='" + getSponsors() + "'" +
            ", authorId=" + getAuthorId() +
            "}";
    }
}
