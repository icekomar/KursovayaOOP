package Frames;

import Exceptions.CheckHuman;

import models.Footballers;
import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import javax.swing.*;

public class AddPlayer {
    private JFrame add;
    private static final Logger log = Logger.getLogger(PlayersList.class);
    public void show(EntityManager em){

        add=new JFrame("Add player");
        log.info("Open window:name" + add.getTitle());
        add.setSize(510 ,510);
        add.setLocation(300,200);
        add.setLayout(null);
        JButton button=new JButton("Add");

        JLabel jname= new JLabel("Name:");
        
        JLabel jsname =new JLabel("Surname:");
        
        JLabel jage =new JLabel("Age:");
        
        JLabel jsalary =new JLabel("Salary:");

        JLabel jrating = new JLabel("Rating:");
        
        JLabel jnog= new JLabel("Number of Games:");
        
        JLabel jposition= new JLabel("Position:");
        
        JLabel jnumber= new JLabel("Number:");
        
        JTextField name=new JTextField();
        JTextField sname=new JTextField();
        JTextField age=new JTextField();
        JTextField salary=new JTextField();
        JTextField rating=new JTextField();
        JTextField nog=new JTextField();
        JComboBox position=new JComboBox(new String[] {"LB","GK","RB","CB","LWB","RWB","CDM","CM","CAM", "RM","LM","RW","LW","ST","RF","CF","LF"});
        JTextField number=new JTextField();
        

        button.addActionListener(e -> {
            try{
                if(!em.getTransaction().isActive()) em.getTransaction().begin();
                Footballers f=new Footballers();
                CheckHuman ch=new CheckHuman((Object)"none Id");
                ch.CheckName(name.getText());
                ch.CheckSurName(sname.getText());
                ch.CheckAge(age.getText());
                ch.CheckSalary(salary.getText());
                ch.CheckPosition(position.getSelectedItem());
                ch.CheckRating(rating.getText());
                ch.CheckNumberOfMatches(nog.getText());
                ch.CheckNumber(number.getText());
                f.setName(name.getText());
                System.out.println(name.getText());
                f.setSalary(Integer.parseInt(salary.getText()));
                f.setSurname(sname.getText());
                f.setAge(Integer.parseInt(age.getText()));
                f.setPosition((String)position.getSelectedItem());
                f.setRating(Float.parseFloat(rating.getText()));
                f.setNumberOfMatches(Integer.parseInt(nog.getText()));
                f.setNumber(number.getText());
                em.persist(f);
                em.getTransaction().commit();
                add.dispose();
            } catch (CheckHuman ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        jname.setBounds(20,30,120,30);
        name.setBounds(145,30,200,30);
        jsname.setBounds(20,80,120,30);
        sname.setBounds(145,80,200,30);
        jage.setBounds(20,130,120,30);
        age.setBounds(145,130,200,30);
        jsalary.setBounds(20,180,120,30);
        salary.setBounds(145,180,200,30);
        jrating.setBounds(20,230,120,30);
        rating.setBounds(145,230,200,30);
        jnog.setBounds(20,280,120,30);
        nog.setBounds(145,280,200,30);
        jposition.setBounds(20,330,120,30);
        position.setBounds(145,330,200,30);
        jnumber.setBounds(20,380,120,30);
        number.setBounds(145,380,200,30);
        button.setBounds(205,420,100,50);
        add.add(jname);
        add.add(name);
        add.add(jsname);
        add.add(sname);
        add.add(jage);
        add.add(age);
        add.add(jsalary);
        add.add(salary);
        add.add(jnumber);
        add.add(number);
        add.add(jnog);
        add.add(nog);
        add.add(jrating);
        add.add(rating);
        add.add(jposition);
        add.add(position);
        add.add(button);
        
        add.setVisible(true);
    }
}


