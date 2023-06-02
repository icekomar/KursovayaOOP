package Exceptions;


import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;

public class CheckGames extends Exception{
    String id;
    public CheckGames(Object id){this.id=id.toString();}
    public CheckGames(String str){super(str);}
    public void CheckResult(Object val) throws CheckGames{
        if(val!=""){
            String k=val.toString();
            if(k.length()!=0) {
                if (k.indexOf('-') == -1)
                    throw new CheckGames("The result should be written via - in the row with id " + id);
                else {
                    String[] parts = k.split("-");
                    if (parts.length != 2) throw new CheckGames("The result  written wrong  in the row with id " + id);
                    else if (!NumberUtils.isNumber(parts[0]) || !NumberUtils.isNumber(parts[1]))
                        throw new CheckGames("The result  must be in numbers  in the row with id " + id);
                }
            }
        }
    }
    public void CheckNameOfOpponent(Object val) throws CheckGames{
        if(val==null) throw new CheckGames("No value in name of opponent in row with id "+id);
        String k=val.toString();
        if (k.length()==0) throw new CheckGames("No value in name of opponent in row with id "+id);
    }
    public void CheckSide(Object val) throws CheckGames{
        if(val==null) throw new CheckGames("No value in side in row with id "+id);
        else{
            String k=val.toString();
            if (k.length()==0) throw new CheckGames("No value in side in row with id "+id);
            String[] str={"Home","Guess"};
            if(Arrays.stream(str).noneMatch(k::equals)) throw new CheckGames("Side can be only Home and Guess in row with id "+id);
        }
    }
    public void CheckDate(Object val, List dates) throws CheckGames{
        if(val==null) throw new CheckGames("No value in date in row with id "+id);
            else{
            String k=val.toString();
            if (k.length()==0) throw new CheckGames("No value in date in row with id "+id);
            if (k.indexOf('.')==-1) throw new CheckGames("The date should be written via . in the row with id "+id);
            else{
                if(dates.contains(k)) throw new CheckGames("The game in this date already have in the row with id "+id);
                String[] parts = k.split("\\.");
                if(parts.length!=3) throw new CheckGames("The date  written wrong  in the row with id "+id);
                else if(!NumberUtils.isNumber(parts[0])||!NumberUtils.isNumber(parts[1])||!NumberUtils.isNumber(parts[2]))  throw new CheckGames("The result  must be in numbers  in the row with id "+id);
                else if(Integer.parseInt(parts[0])<0||Integer.parseInt(parts[0])>31) throw new CheckGames("The day can be from 1 to 31  in the row with id "+id);
                else if(Integer.parseInt(parts[1])<0||Integer.parseInt(parts[1])>12) throw new CheckGames("The month can be from 1 to 12  in the row with id "+id);
            }
        }
    }
}
