package Exceptions;

import javax.swing.*;
import java.util.Objects;

public class CheckChoosen extends Exception{
    public CheckChoosen(){
        super("You didn't choose an id");
    }

    public void Checkchoose(JComboBox box) throws CheckChoosen {
        if (Objects.equals(box.getSelectedItem(), "Player Id")) throw new CheckChoosen();
    }
}
