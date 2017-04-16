package model;

import java.util.Date;

public class Computer {


    private int id;
    private Company company;
    private String name;
    private Date introduced;
    private Date discontinued;

    /**
     * Constructor.
     */
    public Computer() {
    }

    /**
     * Constructor.
     * @param company attr
     * @param name attr
     * @param introduced attr
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
     * @param id attr
     * @param company attr
     * @param name attr
     * @param introduced attr
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
