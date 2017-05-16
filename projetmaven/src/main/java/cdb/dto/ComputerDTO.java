package cdb.dto;

/**
 * Created by vkarassouloff on 14/04/17.
 */
public class ComputerDTO {

    private String name;
    private Integer id;
    private String introduced;
    private String discontinued;
    private String companyName;
    private Integer companyId;

    /**
     * Empty constructor.
     */
    public ComputerDTO() {

    }

    /**
     * Constructor.
     *
     * @param name         of the computerdto
     * @param id           of the computerdto
     * @param introduced   of the computerdto
     * @param discontinued of the computerdto
     * @param companyName  of the computerdto
     * @param companyId    of the computerdto
     */
    public ComputerDTO(String name, Integer id, String introduced, String discontinued, String companyName, Integer companyId) {
        this.name = name;
        this.id = id;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
        this.companyId = companyId;
    }


    public static class Builder {

        public ComputerDTO computer;

        /**
         * Builder for ComputerDTO.
         */
        public Builder() {
            computer = new ComputerDTO();
        }

        /**
         * Set id.
         *
         * @param id of computer
         * @return builder
         */
        public Builder withId(Integer id) {
            computer.setId(id);
            return this;
        }

        /**
         * Set name.
         *
         * @param name string
         * @return builder
         */
        public Builder withName(String name) {
            computer.name = name;
            return this;
        }

        /**
         * Set Introduced date.
         *
         * @param introduced string
         * @return builder
         */
        public Builder withIntroducedDate(String introduced) {
            computer.introduced = introduced;
            return this;
        }

        /**
         * Set Discontinued date.
         *
         * @param discontinued string
         * @return builder
         */
        public Builder withDiscontinuedDate(String discontinued) {
            computer.discontinued = discontinued;
            return this;
        }

        /**
         * Set company id.
         *
         * @param idCompany integer
         * @return builder
         */
        public Builder withCompanyId(Integer idCompany) {
            computer.companyId = idCompany;
            return this;
        }

        /**
         * Set company name.
         * @param nameCompany string
         * @return builder
         */
        public Builder withCompanyName(String nameCompany) {
            computer.companyName = nameCompany;
            return this;
        }

        /**
         * Build.
         *
         * @return computerDto
         */
        public ComputerDTO build() {
            return computer;
        }


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer comanyId) {
        this.companyId = comanyId;
    }
}
