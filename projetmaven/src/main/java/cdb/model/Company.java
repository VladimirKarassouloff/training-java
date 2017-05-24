package cdb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(schema = "computer-database-db", name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Computer> computers;

    /**
     * Constructor.
     */
    public Company() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id   of the company
     * @param name of the company
     */
    public Company(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor.
     *
     * @param name of the company
     */
    public Company(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Company) {
            Company c = (Company) obj;
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
        return "{ Company : { name:" + this.getName() + ", id:" + this.getId() + " } }";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }
}
