package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
public class Staff extends Human {
    private int Id;
    private String Specialization;
    private EntityManager em;

    public Staff() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSpecialization() {
        return Specialization;
    }
    public List<Staff> GetAllStaff(){
       return em.createQuery("SELECT p FROM Staff p").getResultList(); 
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Object[][] ListToObject(List<Staff> staff) {
        Object[][] str = new Object[staff.size()][];

        for (int i = 0; i < staff.size(); i++) {

            str[i] = new Object[]{(staff.get(i).getId()), staff.get(i).getName(),staff.get(i).getSurname()
                    , staff.get(i).getAge(), staff.get(i).getSalary(), staff.get(i).getSpecialization()};
        }
        return str;
    }

    public String[][] ListToString(List<Staff> staff) {
        String[][] str = new String[staff.size()][];

        for (int i = 0; i < staff.size(); i++) {

            str[i] = new String[]{String.valueOf((staff.get(i).getId())), staff.get(i).getName(),staff.get(i).getSurname()
                    , String.valueOf(staff.get(i).getAge()), String.valueOf(staff.get(i).getSalary()), staff.get(i).getSpecialization()};
        }
        return str;
    }
    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }
    public  Staff(EntityManager em){
        setEm(em);
    }
    public Staff(String name,String Surname, int age,int salary,String spec){
        setName(name);
        setSalary(salary);
        setSurname(Surname);
        setAge(age);
        setSpecialization(spec);
    }
}
