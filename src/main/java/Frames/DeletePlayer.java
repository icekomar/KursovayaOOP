package Frames;

import Exceptions.CheckTable;
import models.Footballers;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.swing.*;

import java.util.List;

public class DeletePlayer {
    private JFrame del;
    private static final Logger log = Logger.getLogger(DeletePlayer.class);
    public void show(EntityManager em){
        del=new JFrame("Delete");
        log.info("Open window:name " + del.getTitle());
        del.setSize(300 ,200);
        del.setLocation(300,200);
        del.setLayout(null);

        JLabel jid =new JLabel("Id:");

        JTextField id=new JTextField();

        jid.setBounds(20,60,20,30);
        id.setBounds(50,60,120,30);
        List idp=em.createQuery("Select id From Footballers p ").getResultList();
        JButton but=new JButton("Delete");
        but.addActionListener(e -> {
            try {
                new CheckTable().CheckId(id.getText(),idp,"");
                int n = JOptionPane.showConfirmDialog(
                        del,
                        "Are you sure?",
                        "",
                        JOptionPane.YES_NO_OPTION);
                if (n==0) {
                    if (!em.getTransaction().isActive()) em.getTransaction().begin();
                    em.remove(em.find(Footballers.class, Integer.parseInt(id.getText())));
                    em.getTransaction().commit();
                    del.dispose();
                }
            }catch (CheckTable ex){
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        });
        but.setBounds(200,130,80,30);
        del.add(jid);
        del.add(id);
        del.add(but);

        del.setVisible(true);
    }

}
