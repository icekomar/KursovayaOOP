package Exceptions;

import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;

public class CheckHuman extends Exception{
    private Object id;
    public CheckHuman(Object val){this.id=val;}
    public CheckHuman(String str){super(str);}
    public void CheckName(Object val) throws CheckHuman{
        if(val==null) throw new CheckHuman("No value in field name in row with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field name in row with id "+id.toString());
        else if(!Character.isUpperCase(k.codePointAt(0))) throw new CheckHuman("Name must start in upper case in row with id "+id.toString());
    }
    public void CheckSurName(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in field surname in row with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field surname in row with id "+id.toString());
        else if(!Character.isUpperCase(k.codePointAt(0))) throw new CheckHuman("Surname must start in upper case in row with id "+id.toString());
    }
    public void CheckAge(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in field age in row with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field age in row with id "+id.toString());
        else if(!NumberUtils.isNumber(k)) throw new CheckHuman("Not number in field age in row with id "+id.toString());
        else if(Integer.parseInt(k)<0||Integer.parseInt(k)>100) throw new CheckHuman("Wrong input age in row with id "+id.toString());
    }
    public void CheckSalary(Object val) throws CheckHuman {
        if (val == null) throw new CheckHuman("No value in field salary in row with id " + id.toString());
        String k = val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field age in row with id "+id.toString());
        else if (!NumberUtils.isNumber(k))
            throw new CheckHuman("Not number in field salary in row with id " + id.toString());
        else if(Integer.parseInt(k) <0) throw new CheckHuman("Negative number in field salary in row with id " + id.toString());
    }

    public void CheckRating(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in field rating in player with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field rating in row with id "+id.toString());
        else if (!NumberUtils.isNumber(k)) throw new CheckHuman("Not number in field rating in player with id "+id.toString());
        else if(0>Float.parseFloat(k) || Float.parseFloat(k)>10) throw new CheckHuman(("Rating can be from 0 to 10 in player with id "+id.toString()));

    }
    public void CheckPosition(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in field rating in player with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field position in row with id "+id.toString());
        String[] str={"LB","GK","RB","CB","LWB","RWB","CDM","CM","CAM", "RM","LM","RW","LW","ST","RF","CF","LF"};
        if(Arrays.stream(str).noneMatch(k::equals)) throw new CheckHuman("Wrong value in position in player with id "+id.toString()+"\n Can be:"+ Arrays.toString(str));
    }
    public void CheckNumberOfMatches(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in field Number Of Games in player with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field name in row with id "+id.toString());
        else if(!NumberUtils.isNumber(k)) throw new CheckHuman("Not number in field Number Of Games in player with id "+id.toString());
        else if(Integer.parseInt(k) <0) throw new CheckHuman("Negative number in field Number of Matches in row with id " + id.toString());
    }
    public void CheckNumber(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in field Number in player with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field number in row with id "+id.toString());
        else if(!NumberUtils.isNumber(k)) throw new CheckHuman("Not number in field Number in player with id "+id.toString());
        else if(Integer.parseInt(k) <0) throw new CheckHuman("Negative number in field number in row with id " + id.toString());
    }
    public void CheckSpecialization(Object val) throws CheckHuman{
        if(val==null||val=="") throw new CheckHuman("No value in specialization in row with id "+id.toString());
        String k=val.toString();
        if(k.length()==0) throw new CheckHuman("No value in field specialization in row with id "+id.toString());
        else if (NumberUtils.isNumber(k)) throw new CheckHuman("Wrong value in specialization in row with id "+id.toString());
    }
}
