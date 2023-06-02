package Frames;

import Exceptions.CheckGames;
import models.Game;
import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import javax.swing.*;

public class AddGame {
    private JFrame add;
    private static final Logger log = Logger.getLogger(GamesList.class);
    public void show(EntityManager em){
        add=new JFrame("Add game");
        log.info("Open window:name" + add.getTitle());
        add.setSize(500 ,400);
        add.setLocation(300,200);
        add.setLayout(null);
        JButton button=new JButton("Add");

        JLabel jname= new JLabel("Name of opponent:");
        JLabel jside =new JLabel("Side:");
        JLabel jresult =new JLabel("Result:");
        JLabel jdate =new JLabel("Data:");
        JTextField name=new JTextField();
        JComboBox side=new JComboBox(new String[] {"Home","Guess"});
        JTextField result=new JTextField();
        JTextField date=new JTextField();

        button.addActionListener(e -> {
            try{
                if(!em.getTransaction().isActive()) em.getTransaction().begin();
                CheckGames cg=new CheckGames((Object) "not id");
                cg.CheckResult(result.getText());
                cg.CheckDate(date.getText(),em.createQuery("Select date From Game p ").getResultList());
                cg.CheckNameOfOpponent(name.getText());
                cg.CheckSide(side.getSelectedItem());
                Game addg=new Game(result.getText(),name.getText(),date.getText(),(String)side.getSelectedItem());
                em.persist(addg);
                em.getTransaction().commit();
                add.dispose();
            } catch (CheckGames ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        jname.setBounds(20,40,120,30);
        name.setBounds(145,40,200,30);
        jside.setBounds(20,100,120,30);
        side.setBounds(145,100,200,30);
        jresult.setBounds(20,160,120,30);
        result.setBounds(145,160,200,30);
        jdate.setBounds(20,220,120,30);
        date.setBounds(145,220,200,30);
        button.setBounds(200,300,100,50);
        add.add(jname);
        add.add(name);
        add.add(jside);
        add.add(side);
        add.add(jresult);
        add.add(result);
        add.add(jdate);
        add.add(date);
        add.add(button);
        add.setVisible(true);
    }
}
