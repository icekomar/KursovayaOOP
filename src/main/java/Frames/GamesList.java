package Frames;

import Exceptions.CheckGames;

import models.Game;
import models.GameCalendar;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamesList {
    private JFrame GamesList;
    private DefaultTableModel model;
    private JTable games;
    private static final Logger log = Logger.getLogger(Start.class);
    public void show(EntityManager em){
        GamesList= new JFrame("Games list");
        log.info("Open window:name " + GamesList.getTitle());
        GamesList.setSize(840,460);
        GamesList.setLocation(100,100);

        JButton save = new JButton("Save");
        save.setToolTipText("Save list");
        save.addActionListener(e -> {
            List<Game> gs = new ArrayList<>();
            try {
                if(!em.getTransaction().isActive()) em.getTransaction().begin();
                List<String> Listdates=new ArrayList<>();
                for(int i=0;i< model.getRowCount();i++){
                    if (model.getValueAt(i,0)==""){
                        Game g= new Game();
                        CheckGames cg=new CheckGames((Object) "none");
                        cg.CheckNameOfOpponent(model.getValueAt(i,1));
                        cg.CheckResult(model.getValueAt(i,2));
                        cg.CheckSide(model.getValueAt(i,3));
                        cg.CheckDate(model.getValueAt(i,4),Listdates);
                        g.setNameOpponent((String) model.getValueAt(i,1));
                        g.setDate((String) model.getValueAt(i,4));
                        g.setResult((String) model.getValueAt(i,2));
                        g.setSide((String) model.getValueAt(i,3));
                        gs.add(g);
                    }else{
                        Game g= em.find(Game.class,model.getValueAt(i,0));
                        CheckGames cg=new CheckGames(model.getValueAt(i,0));
                        cg.CheckNameOfOpponent(model.getValueAt(i,1));
                        cg.CheckResult(model.getValueAt(i,2));
                        cg.CheckSide(model.getValueAt(i,3));
                        cg.CheckDate(model.getValueAt(i,4),Listdates);
                        g.setNameOpponent((String) model.getValueAt(i,1));
                        g.setDate((String) model.getValueAt(i,4));
                        g.setResult((String) model.getValueAt(i,2));
                        g.setSide((String) model.getValueAt(i,3));
                        em.merge(g);
                    }
                    Listdates.add((String)model.getValueAt(i,4));
                }
                for (Game g : gs) {
                    em.persist(g);
                }
                em.getTransaction().commit();
                games.setEnabled(false);
            }catch (CheckGames ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        JButton edit = new JButton("Edit");
        edit.setToolTipText("edit list");
        edit.addActionListener(e -> games.setEnabled(true));

        JButton add = new JButton(" Add");
        add.setToolTipText(" Add new record in list");
        add.addActionListener(e -> new AddGame().show(em));

        JButton delete = new JButton("Delete");
        delete.setToolTipText(" Delete record in list");
        delete.addActionListener(e -> {
            //new DeleteGame().show(em);
            int[] indexs=games.getSelectedRows();
            int n = JOptionPane.showConfirmDialog(
                    GamesList,
                    "Are you sure?",
                    "",
                    JOptionPane.YES_NO_OPTION);
            if(n==0) {
                if (!em.getTransaction().isActive()) em.getTransaction().begin();
                for (int index : indexs) {
                    em.remove(em.find(Game.class, model.getValueAt(index, 0)));
                }
                em.getTransaction().commit();
            }
        });

        JButton imp=new JButton("Import");
        imp.addActionListener(e -> {
            try {
                JFileChooser fileopen = new JFileChooser();
                fileopen.setFileFilter(new FileNameExtensionFilter("xml","xml"));
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    DocumentBuilder dBuilder =
                            DocumentBuilderFactory.newInstance().newDocumentBuilder();

                    Document doc = dBuilder.parse(fileopen.getSelectedFile());
// Нормализация документа
                    doc.getDocumentElement().normalize();
                    NodeList nlBooks = doc.getElementsByTagName("game");
                    for (int temp = 0; temp < nlBooks.getLength(); temp++) {

                        Node elem = nlBooks.item(temp);

                        NamedNodeMap attrs = elem.getAttributes();

                        String n = attrs.getNamedItem("Name_of_opponent").getNodeValue();
                        String r = attrs.getNamedItem("Result").getNodeValue();
                        String s = attrs.getNamedItem("Side").getNodeValue();
                        String d = attrs.getNamedItem("Date").getNodeValue();


                        model.addRow(new String[]{"", n, r, s, d});
                    }
                }
            }
            catch (ParserConfigurationException | SAXException | IOException ex) { ex.printStackTrace(); }
            catch (NullPointerException ex){JOptionPane.showMessageDialog(null,"The wrong format xml file");}

        });

        JButton exp=new JButton("Export");
        exp.addActionListener(e -> {
            try {
                DocumentBuilder builder =
                        DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.newDocument();
                Node list=doc.createElement("gameslist");
                doc.appendChild(list);
// Создание дочерних элементов book и присвоение значений атрибутам
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    Element game = doc.createElement("game");
                    list.appendChild(game);
                    game.setAttribute("ID", model.getValueAt(i, 0).toString());
                    game.setAttribute("Name_of_opponent", model.getValueAt(i, 1).toString());
                    game.setAttribute("Result", model.getValueAt(i, 2).toString());
                    game.setAttribute("Side", model.getValueAt(i, 3).toString());
                    game.setAttribute("Date", model.getValueAt(i, 4).toString());

                }
                try {
// Создание преобразователя документа
                    Transformer trans = TransformerFactory.newInstance().newTransformer();
// Создание файла с именем games.xml для записи документа
                    java.io.FileWriter fw = new FileWriter("games.xml");
// Запись документа в файл
                    trans.transform(new DOMSource(doc), new StreamResult(fw));

                } catch (TransformerException ex) { ex.printStackTrace(); } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } catch (ParserConfigurationException ex) { ex.printStackTrace(); }

        });


        JToolBar toolBar = new JToolBar("Панель инструментов");
        toolBar.add(save);
        toolBar.add(edit);
        toolBar.add(add);
        toolBar.add(delete);
        toolBar.add(imp);
        toolBar.add(exp);

        GamesList.setLayout(new BorderLayout());
        GamesList.add(toolBar, BorderLayout.NORTH);

        String [] columns={"ID", "Name of opponent", "Result" , "Side", "Date"};
        GameCalendar Games=new GameCalendar(em);
        Games.setGames();
        JButton refresh=new JButton("Refresh");
        refresh.addActionListener(e -> {
            Games.setGames();
            model=new DefaultTableModel(Games.ListToObject(Games.getGames()),columns);
            games.setModel(model);
        });
        toolBar.add(refresh);
        model= new DefaultTableModel(Games.ListToObject(Games.getGames()),columns);
        games = new JTable(model){

            public boolean isCellEditable(int row, int col)
            {
                return (col != 0);
            }
        };
        games.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(games);
        GamesList.add(scroll, BorderLayout.CENTER);

        JButton all = new JButton("Show all games");
        all.addActionListener(e -> games.setModel(new DefaultTableModel(Games.ListToObject(Games.getGames()),columns)));
        JButton played = new JButton("Show played games");
        played.addActionListener(e -> games.setModel(new DefaultTableModel(Games.ListToObject(Games.getPlayedGames()),columns)));

        JButton upcoming = new JButton("Show upcoming games");
        upcoming.addActionListener(e -> games.setModel(new DefaultTableModel(Games.ListToObject(Games.getUpcomingGames()),columns)));
        JPanel filterPanel = new JPanel();
        filterPanel.add(all);
        filterPanel.add(played);
        filterPanel.add(upcoming);
        JButton ug=new JButton("Set rating in game");
// Размещение панели поиска внизу окна
        filterPanel.add(ug);
        GamesList.add(filterPanel, BorderLayout.SOUTH);
        ug.addActionListener(e -> new UpdateGame().show(em));
// Визуализация экранной формы
        GamesList.setVisible(true);

    }
    public boolean visible(){
        return GamesList.isVisible();
    }
    public void setvisible(){
        GamesList.setVisible(true);
    }

    public JFrame getGamesList() {
        return GamesList;
    }
}
