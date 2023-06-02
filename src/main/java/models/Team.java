package models;




import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class Team {
    @PersistenceContext
    private EntityManager em;
    private List<Footballers> members = new ArrayList<>();

    public List<Footballers> getMembers() {
        return members;
    }

    public void setMembers() {

        members = em.createQuery(
                "SELECT p FROM Footballers p").getResultList();

    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Object[][] ListToObject(){
       Object[][] str = new Object[members.size()][];

       for(int i=0; i<members.size();i++){

           str[i]= new Object[]{(members.get(i).getId()),members.get(i).getName(),members.get(i).getSurname(),
                   (members.get(i).getAge()),(members.get(i).getSalary()),members.get(i).getPosition(),
                   (members.get(i).getRating()),(members.get(i).getNumberOfMatches()),members.get(i).getNumber()};
       }
       return str;
    }
    public String[][] ListToString(){
        String[][] str = new String[members.size()][];

        for(int i=0; i<members.size();i++){

            str[i]= new String[]{String.valueOf(members.get(i).getId()),members.get(i).getName(),members.get(i).getSurname(),
                    String.valueOf((members.get(i).getAge())), String.valueOf((members.get(i).getSalary())),members.get(i).getPosition(),
                    String.valueOf((members.get(i).getRating())), String.valueOf((members.get(i).getNumberOfMatches())),members.get(i).getNumber()};
        }
        return str;
    }
    public void DeleteMember(Footballers member){

    }
    public Team(){
    }
    public Team(EntityManager em){
        setEm(em);
    }
}
