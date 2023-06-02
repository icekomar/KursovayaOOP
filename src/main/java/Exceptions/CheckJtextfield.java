package Exceptions;

import javax.swing.*;
import org.apache.commons.lang3.math.NumberUtils;
public class CheckJtextfield extends Exception{
    public CheckJtextfield(String str){
        super(str);
    }
    public CheckJtextfield(){

    }
    public void CheckFieldString(JLabel p,JTextField t) throws CheckJtextfield {
        if (t.getText().length()==0) throw new CheckJtextfield("Please enter in field"+" "+p.getText());
    }
    public void CheckFieldInt(JLabel p,JTextField t) throws CheckJtextfield {
        if (t.getText().length()==0) throw new CheckJtextfield("Please enter in field"+" "+p.getText());
        if (!NumberUtils.isNumber(t.getText())) throw new NumberFormatException();
    }
}
