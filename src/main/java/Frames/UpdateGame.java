package Frames;

import Exceptions.CheckJtextfield;
import Exceptions.CheckTable;
import models.Footballers;
import models.Game;
import models.TeamRating;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class UpdateGame {
    public  static List idlist;
    public void show(EntityManager em){
        JFrame ug=new JFrame("Update game");
        ug.setSize(600,500);
        JLabel jidg=new JLabel("Id Game");
        JTextField idg=new JTextField();
        DefaultTableModel model =new DefaultTableModel(new Object[][]{{}},new Object[]{"Player Id","Rating"});
        JTable pr=new JTable(model);
        JButton add =new JButton("Add row");
        JScrollPane scroll=new JScrollPane(pr);
        add.addActionListener(e -> model.addRow(new Vector<>()));
        JButton del=new JButton("Delete row");
        del.addActionListener(e ->  model.removeRow(model.getRowCount()-1));
        JButton end=new JButton("Ok");
        idlist=em.createQuery("Select id From Footballers p ").getResultList();
        List gidlist=em.createQuery("Select id From Game p ").getResultList();
        end.addActionListener(e -> {
            try{
                new CheckJtextfield().CheckFieldInt(jidg,idg);
                new CheckTable().CheckId(idg.getText(),gidlist,"Game");
                for(int i=0;i<model.getRowCount();i++){
                    new CheckTable().CheckId(model.getValueAt(i,0),idlist,"Footballers");
                    new CheckTable().CheckRating(model.getValueAt(i,1));
                }
                Game g=em.find(Game.class,Integer.parseInt(idg.getText()));
                em.getTransaction().begin();
                for(int i=0;i<model.getRowCount();i++){
                    Footballers f = em.find(Footballers.class,Integer.parseInt((String) model.getValueAt(i,0)));
                    f.updateRating(Float.parseFloat((String)model.getValueAt(i,1)));
                    em.merge(f);
                    TeamRating tr=new TeamRating(Float.parseFloat((String)model.getValueAt(i,1)),g,f);
                    em.persist(tr);
                }
                em.getTransaction().commit();
                ug.dispose();
            } catch (CheckJtextfield | CheckTable ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                pr.setEnabled(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter number in Id Game");
                pr.setEnabled(true);
            }

        });
        ug.setLayout(null);
        jidg.setBounds(30,40,80,40);
        idg.setBounds(120,40,100,40);
        scroll.setBounds(60,90,480,300);
        del.setBounds(420,40,100,40);
        add.setBounds(300,40,100,40);
        end.setBounds(440,420,100,40);
        ug.add(del);
        ug.add(jidg);
        ug.add(idg);
        ug.add(scroll);
        ug.add(add);
        ug.add(end);
        ug.setVisible(true);

    }
}
