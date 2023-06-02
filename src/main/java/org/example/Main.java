package org.example;


import org.apache.log4j.Logger;
import Frames.*;
import javax.persistence.*;
import javax.swing.*;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        EntityManager entityManager;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
        entityManager = factory.createEntityManager();
        log.info("begin work");
        new Start().show(entityManager);
        do{
        }while(JFrame.getWindows()!=null);
        entityManager.close();
        factory.close();
    }
}