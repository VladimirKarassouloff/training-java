package model;

public class Company {

	private int id;
	private String name;
	
	
	public Company() {
		super();
	}
	
	public Company(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Company) {
			Company c = (Company) obj;
			return c.getId() == this.getId();
		}
		return false;
	}

	@Override
	public String toString() {
		return "{ Company : { name:"+this.getName()+", id:"+this.getId()+" } }";
	}
	
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
