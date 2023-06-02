package Frames;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.GameCalendar;
import models.Staff;
import models.Team;
import org.apache.log4j.Logger;
import org.example.Main;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;



public class Start {

    private static final Logger log = Logger.getLogger(Main.class);
    public void show(EntityManager em){
        JFrame start = new JFrame();
        log.info("Open window:Start window");
        start.setSize(400,400);
        start.setLocation(450,200);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamesList gl=new GamesList();
        PlayersList pl =new PlayersList();
        StaffList sl= new StaffList();
        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.setLayout(null);
        JButton players = new JButton("Check Footballers list");
        players.setBounds(100,30,200,50);
        players.addActionListener(e -> {
           if(pl.getFootballersList()== null) pl.show(em);
           else if (!pl.visible()) pl.setvisible();

        });
        JButton staff = new JButton("Check Staff list");
        staff.setBounds(100,120,200,50);
        staff.addActionListener(e -> {
            if(sl.getStaffList()== null) sl.show(em);
            else if (!sl.visible()) sl.setvisible();
        });
        JButton games = new JButton("Check Games list");
        games.setBounds(100,210,200,50);
        games.addActionListener(e -> {
            if(gl.getGamesList()== null) gl.show(em);
            else if (!gl.visible()) gl.setvisible();
        });

        JButton p =new JButton("Get PDF file");
        p.setBounds(100,300,200,50);
        p.addActionListener(e -> {
            String file="C:/Users/mihak/IdeaProjects/KursOOP/report.pdf";
            Document document=new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
            } catch (DocumentException | FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            document.open();

            PdfPTable table1 = new PdfPTable(9);
            table1.addCell("ID");
            table1.addCell("Name");
            table1.addCell("Surname");
            table1.addCell("Age");
            table1.addCell("Salary");
            table1.addCell("Position");
            table1.addCell("Rating");
            table1.addCell("Number_of_Games");
            table1.addCell("Number");
            Team t=new Team(em);
            t.setMembers();
            String[][] data =t.ListToString();
            for (String[] datum : data) {
                for (String s : datum) table1.addCell(s);
            }

            PdfPTable table2 = new PdfPTable(6);
            table2.addCell("ID");
            table2.addCell("Name");
            table2.addCell("Surname");
            table2.addCell("Age");
            table2.addCell("Salary");
            table2.addCell("Specialization");
            List<Staff> st=new Staff(em).GetAllStaff();
            for (Staff value : st) {
                table2.addCell(String.valueOf(value.getId()));
                table2.addCell(value.getName());
                table2.addCell(value.getSurname());
                table2.addCell(String.valueOf((value.getAge())));
                table2.addCell(String.valueOf((value.getSalary())));
                table2.addCell(value.getSpecialization());
            }
            PdfPTable table3=new PdfPTable(5);
            table3.addCell("ID");
            table3.addCell("Name_of_opponent");
            table3.addCell("Result");
            table3.addCell("Side");
            table3.addCell("Date");

            GameCalendar gc=new GameCalendar(em);
            gc.setGames();
            data=gc.ListToString();
            for (String[] datum : data) {
                for (String s : datum) table3.addCell(s);
            }
            try {
                document.add(new Paragraph("Footballers Table"));
                document.add(new Paragraph(" "));
                document.add(table1);
                document.add(new Paragraph("Staff Table"));
                document.add(new Paragraph(" "));
                document.add(table2);
                document.add(new Paragraph("Games Table"));
                document.add(new Paragraph(" "));
                document.add(table3);
            } catch (DocumentException ex) {
                throw new RuntimeException(ex);
            }
            document.close();
        });

        buttons.add(players);
        buttons.add(staff);
        buttons.add(games);
        buttons.add(p);

        start.setContentPane(buttons);
        start.setVisible(true);
    }
}