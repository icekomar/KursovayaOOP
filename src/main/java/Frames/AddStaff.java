package Frames;

import Exceptions.CheckHuman;
import models.Staff;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.swing.*;

public class AddStaff {

    private JFrame add;
    private static final Logger log = Logger.getLogger(StaffList.class);
    public void show(EntityManager em){
        add=new JFrame("Add staff");
        log.info("Open window:name" + add.getTitle());
        add.setSize(510 ,400);
        add.setLocation(300,200);
        add.setLayout(null);
        JButton button=new JButton("Add");

        JLabel jname= new JLabel("Name:");

        JLabel jsname =new JLabel("Surname:");

        JLabel jage =new JLabel("Age:");

        JLabel jsalary =new JLabel("Salary:");

        

        JLabel jspecialization= new JLabel("Specialization:");

        JTextField name=new JTextField();
        JTextField sname=new JTextField();
        JTextField age=new JTextField();
        JTextField salary=new JTextField();
       
        JTextField specialization=new JTextField();


        button.addActionListener(e -> {
            try{
                if(!em.getTransaction().isActive()) em.getTransaction().begin();
                Staff s=new Staff();
                CheckHuman ch=new CheckHuman((Object) "none Id");
                ch.CheckName(name.getText());
                ch.CheckSurName(sname.getText());
                ch.CheckAge(age.getText());
                ch.CheckSalary(salary.getText());
                ch.CheckSpecialization(specialization.getText());
                s.setName(name.getText());
                s.setSalary(Integer.parseInt(salary.getText()));
                s.setSurname(sname.getText());
                s.setAge(Integer.parseInt(age.getText()));
                s.setSpecialization(specialization.getText());
                em.persist(s);
                em.getTransaction().commit();
                add.dispose();
            } catch (CheckHuman ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        jname.setBounds(30,30,130,30);
        name.setBounds(155,30,300,30);
        jsname.setBounds(30,80,130,30);
        sname.setBounds(155,80,300,30);
        jage.setBounds(30,130,130,30);
        age.setBounds(155,130,300,30);
        jsalary.setBounds(30,180,130,30);
        salary.setBounds(155,180,300,30);
     
        jspecialization.setBounds(30,230,130,30);
        specialization.setBounds(155,230,300,30);
        button.setBounds(205,290,100,50);
        add.add(jname);
        add.add(name);
        add.add(jsname);
        add.add(sname);
        add.add(jage);
        add.add(age);
        add.add(jsalary);
        add.add(salary);
        add.add(jspecialization);
        add.add(specialization);
       
        add.add(button);

        add.setVisible(true);
    }
}


