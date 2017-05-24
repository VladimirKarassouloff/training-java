package cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(schema = "computer-database-db", name = "computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "introduced")
    private Date introduced;

    @Column(name = "discontinued")
    private Date discontinued;

    /**
     * Bind a col number to a propert name.
     * @param colOrder to translate
     * @return name of property
     */
    public static String colToProperty(int colOrder) {
       switch (colOrder) {
           case 0:
               return "name";
           case 1:
               return "introduced";
           case 2:
               return "discontinued";
           case 3:
               return "company.name";
           default:
               return "id";
       }
    }


    public static class Builder {
        private Computer computer;

        /**
         * Constructor for builder pattern.
         */
        public Builder() {
            computer = new Computer();
        }

        /**
         * Setter builder.
         *
         * @param id property set
         * @return builder
         */
        public Builder withId(long id) {
            computer.setId(id);
            return this;
        }

        /**
         * Setter builder.
         *
         * @param name property set
         * @return builder
         */
        public Builder withName(String name) {
            computer.setName(name);
            return this;
        }

        /**
         * Setter builder.
         *
         * @param introduced property set
         * @return builder
         */
        public Builder withIntroduced(Date introduced) {
            computer.setIntroduced(introduced);
            return this;
        }

        /**
         * Setter builder.
         *
         * @param discontinued property set
         * @return builder
         */
        public Builder withDiscontinued(Date discontinued) {
            computer.setDiscontinued(discontinued);
            return this;
        }

        /**
         * Setter builder.
         *
         * @param company property set
         * @return builder
         */
        public Builder withCompany(Company company) {
            computer.setCompany(company);
            return this;
        }

        /**
         * Build.
         * @return computer built.
         */
        public Computer build() {
            return computer;
        }
    }


    /**
     * Constructor.
     */
    public Computer() {
    }

    /**
     * Constructor.
     *
     * @param company      attr
     * @param name         attr
     * @param introduced   attr
     * @param discontinued attr
     */
    public Computer(Company company, String name, Date introduced, Date discontinued) {
        super();
        this.company = company;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
    }

    /**
     * Constructor.
     *
     * @param id           attr
     * @param company      attr
     * @param name         attr
     * @param introduced   attr
     * @param discontinued attr
     */
    public Computer(int id, Company company, String name, Date introduced, Date discontinued) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
    }

    /**
     * Equality between 2 object.
     *
     * @param obj compared to
     * @return equality
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Computer) {
            Computer c = (Computer) obj;
            return c.getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "{ Computer : { id : " + id + ", company : " + company + ","
                + " name : " + name + ", introduced : " + introduced + ", discontinued : " + discontinued + " } }";
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Date introduced) {
        this.introduced = introduced;
    }

    public Date getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Date discontinued) {
        this.discontinued = discontinued;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
