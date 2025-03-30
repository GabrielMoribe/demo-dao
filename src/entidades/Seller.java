package entidades;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String email;
    private Date birthdate;
    private Double baseSalary;
    // DEPENDENCIA
    private Department department;


    public Seller(){}
    public Seller(Integer id, String name, String email, Date birthdate, Double baseSalary, Department department) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.birthdate = birthdate;
        this.baseSalary = baseSalary;
        this.department = department;
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
    public String getEmail(String email) {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public Double getBaseSalary(Double baseSalary) {
        return baseSalary;
    }
    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }


    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Seller other = (Seller) obj;
        if (id == null) {
            if(other.id != null)
                return false;
        }
        else if(!id.equals(other.id)){
            return false;
        }
        return true;
    }


    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Seller [name=").append(name).append(", id=").append(id).append(", email=").append(email)
                .append(", birthdate=").append(birthdate).append(", baseSalary=").append(baseSalary).append("]");
        return sb.toString();
    }
}


