package Exceptions;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class CheckTable extends Exception{
    public CheckTable(String str){super(str);}
    public  CheckTable(){}
    public void CheckId(Object k, List id,String name) throws CheckTable{
        if (k==null) throw new CheckTable("No Id in field id "+name);
        String k1=k.toString();
        if(!NumberUtils.isNumber(k1)||k1.length()==0) throw new CheckTable("Not id in "+ name);
        else if (!id.contains(Integer.parseInt(k1))) throw new CheckTable("id does not exist in "+name+":"+k);
    }
    public void CheckRating(Object val)throws CheckTable{
        if(val==null) throw new CheckTable("No value in field rating");
        String k=val.toString();
        if (!NumberUtils.isNumber(k)) throw new NumberFormatException();
        else if(0>Float.parseFloat(k) || Float.parseFloat(k)>10) throw new CheckTable(("Rating can be from 0 to 10"));
    }

}
