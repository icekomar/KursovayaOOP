package Frames;

import Exceptions.CheckChoosen;
import Exceptions.CheckHuman;
import models.Footballers;
import models.Team;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayersList {
    private JFrame FootballersList;
    private DefaultTableModel model;
    private JTable players;
    private JComboBox Idplayer;
    private static final Logger log = Logger.getLogger(Start.class);
    public void show(EntityManager em){
        FootballersList= new JFrame("Footballers list");
        log.info("Open window:name " + FootballersList.getTitle());
        FootballersList.setSize(840,460);
        FootballersList.setLocation(100,100);

        JButton save = new JButton("Save");
        save.setToolTipText("Save list");
        save.addActionListener(e -> {
                    List<Footballers> fs = new ArrayList<>();
                    try {
                        if(!em.getTransaction().isActive()) em.getTransaction().begin();
                        for(int i=0;i< model.getRowCount();i++){
                            if (model.getValueAt(i,0)==""){
                                Footballers f=new Footballers();
                                CheckHuman ch=new CheckHuman((Object)"none");
                                ch.CheckName(model.getValueAt(i,1));
                                ch.CheckSurName(model.getValueAt(i,2));
                                ch.CheckAge(model.getValueAt(i,3));
                                ch.CheckSalary(model.getValueAt(i,4));
                                ch.CheckPosition(model.getValueAt(i,5));
                                ch.CheckRating(model.getValueAt(i,6));
                                ch.CheckNumberOfMatches(model.getValueAt(i,7));
                                ch.CheckNumber(model.getValueAt(i,8));
                                f.setName((String) model.getValueAt(i,1));
                                f.setSalary(Integer.parseInt((String) model.getValueAt(i,4)));
                                f.setSurname((String) model.getValueAt(i,2));
                                f.setAge(Integer.parseInt((String)model.getValueAt(i,3)));
                                f.setPosition((String) model.getValueAt(i,5));
                                f.setRating(Float.parseFloat((String) model.getValueAt(i,6)));
                                f.setNumberOfMatches(Integer.parseInt((String)model.getValueAt(i,7)));
                                f.setNumber((String)model.getValueAt(i,3));
                                fs.add(f);
                            }else{
                                Footballers f= em.find(Footballers.class,model.getValueAt(i,0));
                                CheckHuman ch=new CheckHuman(model.getValueAt(i,0));
                                ch.CheckName(model.getValueAt(i,1));
                                ch.CheckSurName(model.getValueAt(i,2));
                                ch.CheckAge(model.getValueAt(i,3));
                                ch.CheckSalary(model.getValueAt(i,4));
                                ch.CheckPosition(model.getValueAt(i,5));
                                ch.CheckRating(model.getValueAt(i,6));
                                ch.CheckNumberOfMatches(model.getValueAt(i,7));
                                ch.CheckNumber(model.getValueAt(i,8));
                                f.setName((String) model.getValueAt(i,1));
                                f.setSalary(Integer.parseInt(model.getValueAt(i,4).toString()));
                                f.setSurname((String) model.getValueAt(i,2));
                                f.setAge(Integer.parseInt(model.getValueAt(i,3).toString()));
                                f.setPosition((String) model.getValueAt(i,5));
                                f.setRating(Float.parseFloat(model.getValueAt(i,6).toString()));
                                f.setNumberOfMatches(Integer.parseInt(model.getValueAt(i,7).toString()));
                                f.setNumber((model.getValueAt(i,8)).toString());
                                em.merge(f);
                            }
                        }
                        for (Footballers f : fs) {
                            em.persist(f);
                        }
                        em.getTransaction().commit();
                        players.setEnabled(false);
            }catch (CheckHuman ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
        });

        JButton edit = new JButton("Edit");
        edit.setToolTipText("edit list");
        edit.addActionListener(e -> players.setEnabled(true));

        JButton add = new JButton(" Add");
        add.setToolTipText(" Add new record in list");
        add.addActionListener(e -> new AddPlayer().show(em));

        JButton delete = new JButton("Delete");
        delete.setToolTipText(" Delete record in list");
        delete.addActionListener(e -> {
            int[] indexs=players.getSelectedRows();
            int n = JOptionPane.showConfirmDialog(
                    FootballersList,
                    "Are you sure?",
                    "",
                    JOptionPane.YES_NO_OPTION);
            if(n==0) {
                if (!em.getTransaction().isActive()) em.getTransaction().begin();
                for (int index : indexs) {
                    em.remove(em.find(PlayersList.class, model.getValueAt(index, 0)));
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
                    NodeList nlBooks = doc.getElementsByTagName("player");
                    for (int temp = 0; temp < nlBooks.getLength(); temp++) {

                        Node elem = nlBooks.item(temp);

                        NamedNodeMap attrs = elem.getAttributes();

                        String n = attrs.getNamedItem("Name").getNodeValue();
                        String s = attrs.getNamedItem("Surname").getNodeValue();
                        String a = attrs.getNamedItem("Age").getNodeValue();
                        String sa = attrs.getNamedItem("Salary").getNodeValue();
                        String p = attrs.getNamedItem("Position").getNodeValue();
                        String r = attrs.getNamedItem("Rating").getNodeValue();
                        String nog = attrs.getNamedItem("Number_of_Games").getNodeValue();
                        String num = attrs.getNamedItem("Number").getNodeValue();


                        model.addRow(new String[]{"", n, s, a, sa, p, r, nog, num});
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
                Node list=doc.createElement("playerlist");
                doc.appendChild(list);
// Создание дочерних элементов book и присвоение значений атрибутам
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    Element player = doc.createElement("player");
                    list.appendChild(player);
                    player.setAttribute("ID", model.getValueAt(i, 0).toString());
                    player.setAttribute("Name", model.getValueAt(i, 1).toString());
                    player.setAttribute("Surname", model.getValueAt(i, 2).toString());
                    player.setAttribute("Age", model.getValueAt(i, 3).toString());
                    player.setAttribute("Salary", model.getValueAt(i, 4).toString());
                    player.setAttribute("Position", model.getValueAt(i, 5).toString());
                    player.setAttribute("Rating", model.getValueAt(i, 6).toString());
                    player.setAttribute("Number_of_Games", model.getValueAt(i, 7).toString());
                    player.setAttribute("Number", model.getValueAt(i, 8).toString());
                }
                try {
// Создание преобразователя документа
                    Transformer trans = TransformerFactory.newInstance().newTransformer();
// Создание файла с именем players.xml для записи документа
                    java.io.FileWriter fw = new FileWriter("players.xml");
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
        FootballersList.setLayout(new BorderLayout());
        FootballersList.add(toolBar, BorderLayout.NORTH);
        String [] columns={"ID","Name","Surname", "Age" , "Salary", "Position", "Rating", "Number Of Games" ,"Number"};
        Team team=new Team(em);
        team.setMembers();
        JButton refresh=new JButton("Refresh");
        refresh.addActionListener(e -> {
            team.setMembers();
            model=new DefaultTableModel(team.ListToObject(),columns);
            players.setModel(model);
        });
        toolBar.add(refresh);
        model= new DefaultTableModel(team.ListToObject(),columns);
        players = new JTable(model){

            public boolean isCellEditable(int row, int col)
            {
                return (col != 0);
            }
        };
        players.setEnabled(false);
        JScrollPane scroll = new JScrollPane(players);
        FootballersList.add(scroll, BorderLayout.CENTER);
        List<String> column = new ArrayList<>();
        column.add("Player Id");
        for (int i = 0; i < model.getRowCount(); i++) {
            column.add(model.getValueAt(i, 0).toString());
        }
        Idplayer=new JComboBox(column.toArray());
        JButton card = new JButton("Open");
        card.addActionListener(e -> {
            try {
                new CheckChoosen().Checkchoose(Idplayer);
                new Card().show(em.find(Footballers.class,Integer.parseInt(Idplayer.getSelectedItem().toString())),em);
            } catch (CheckChoosen ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }


        });
        JPanel filterPanel = new JPanel();
        filterPanel.add(Idplayer);
        filterPanel.add(card);
// Размещение панели поиска внизу окна
        FootballersList.add(filterPanel, BorderLayout.SOUTH);
// Визуализация экранной формы
        FootballersList.setVisible(true);

    }
    public boolean visible(){
        return FootballersList.isVisible();
    }
    public void setvisible(){
        FootballersList.setVisible(true);
    }
    public JFrame getFootballersList() {
        return FootballersList;
    }
}
