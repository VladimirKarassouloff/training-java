package cdb.dto;

import cdb.model.Company;

/**
 * Created by vkarassouloff on 06/06/17.
 */
public class CompanyDTO {

    private Long id;
    private String name;

    /**
     * Def const.
     */
    public CompanyDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder{
        private CompanyDTO company;

        /**
         * Def const.
         */
        public Builder() {
            company = new CompanyDTO();
        }

        /**
         * Builder.
         * @param name attr
         * @return builder
         */
        public Builder withName(String name) {
            company.setName(name);
            return this;
        }

        /**
         * Builder.
         * @param id new attr
         * @return builder
         */
        public Builder withId(Long id) {
            company.setId(id);
            return this;
        }

        /**
         * Get the company.
         * @return company built
         */
        public CompanyDTO build() {
            return company;
        }
    }

}
