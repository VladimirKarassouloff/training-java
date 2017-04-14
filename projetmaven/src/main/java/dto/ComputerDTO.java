package dto;

/**
 * Created by vkarassouloff on 14/04/17.
 */
public class ComputerDTO {

    private String name;
    private int id;
    private String introduced;
    private String discontinued;
    private String companyName;
    private int companyId;


    public ComputerDTO() {

    }

    public ComputerDTO(String name, int id, String introduced, String discontinued, String companyName, int companyId) {
        this.name = name;
        this.id = id;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
        this.companyId = companyId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getComanyId() {
        return companyId;
    }

    public void setComanyId(int comanyId) {
        this.companyId = comanyId;
    }
}
