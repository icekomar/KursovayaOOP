package Frames;
import models.Footballers;
import models.TeamRating;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class Card {

    private static final Logger log = Logger.getLogger(PlayersList.class);

    public void show(Footballers f, EntityManager em){
        JFrame card = new JFrame("card");
        log.info("Open window:name" + card.getTitle()+" "+f.getName()+" "+f.getSurname());
        card.setSize(840,460);
        card.setLocation(100,100);
        JLabel name = new JLabel("Full name:" + f.getName() + " " + f.getSurname());
        JLabel age = new JLabel("Age:" + f.getAge());
        JLabel rating = new JLabel("Rating:" + f.getRating());
        JLabel position = new JLabel("Position:" + f.getPosition());
        JLabel nog = new JLabel("Number of Games:" + f.getNumberOfMatches());
        JLabel salary = new JLabel("Salary:" + f.getSalary());
        JLabel number = new JLabel("Number:" + f.getNumber());
        JPanel panel = new JPanel();
        panel.setLayout(null);
        String [] columns={"Game_ID", "Rating"};
        List<TeamRating> tm = em.createQuery("Select p From TeamRating p Where p.player=:k" ).setParameter("k",f).getResultList();
        Object[][] data =new Object[tm.size()][];
        for(int i=0;i< tm.size();i++){
            data[i]=new Object[]{tm.get(i).getGame().getId(),tm.get(i).getRating()};
        }
        DefaultTableModel model= new DefaultTableModel(data,columns);
        JTable game_rat = new JTable(model);
        name.setBounds(100,10,300,30);
        age.setBounds(100,50,300,30);
        rating.setBounds(100,90,300,30);
        salary.setBounds(100,130,300,30);
        position.setBounds(500,30,300,30);
        nog.setBounds(500,70,300,30);
        number.setBounds(500,100,300,30);
        JScrollPane scroll = new JScrollPane(game_rat);
        scroll.setBounds(100,200,600,300);

        panel.add(name);
        panel.add(age);
        panel.add(rating);
        panel.add(salary);
        panel.add(position);
        panel.add(nog);
        panel.add(number);
        panel.add(scroll);
        card.setContentPane(panel);

        card.setVisible(true);

    }
}
