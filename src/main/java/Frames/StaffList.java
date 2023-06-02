package Frames;

import Exceptions.CheckHuman;
import models.Staff;
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


public class StaffList {
    private JFrame StaffList;
    private JTable staff1;
    private DefaultTableModel model;

    private static final Logger log = Logger.getLogger(Start.class);
    public void show(EntityManager em){
        StaffList= new JFrame("Staff list");
        log.info("Open window:name " + StaffList.getTitle());
        StaffList.setSize(840,460);
        StaffList.setLocation(100,100);


        JButton save = new JButton("Save");
        save.setToolTipText("Save list");
        save.addActionListener(e -> {
            List<Staff> sl = new ArrayList<>();
            try {
                if(!em.getTransaction().isActive()) em.getTransaction().begin();
                for(int i=0;i< model.getRowCount();i++){
                    if (model.getValueAt(i,0)==""){
                        Staff s=new Staff();
                        CheckHuman ch=new CheckHuman((Object)"none");
                        ch.CheckName(model.getValueAt(i,1));
                        ch.CheckSurName(model.getValueAt(i,2));
                        ch.CheckAge(model.getValueAt(i,3));
                        ch.CheckSalary(model.getValueAt(i,4));
                        ch.CheckSpecialization(model.getValueAt(i,5));
                        
                        s.setName((String) model.getValueAt(i,1));
                        s.setSalary(Integer.parseInt((String) model.getValueAt(i,4)));
                        s.setSurname((String) model.getValueAt(i,2));
                        s.setAge(Integer.parseInt((String)model.getValueAt(i,3)));
                        s.setSpecialization((String) model.getValueAt(i,5));
                        sl.add(s);
                    }else{
                        Staff s= em.find(Staff.class,model.getValueAt(i,0));
                        CheckHuman ch=new CheckHuman(model.getValueAt(i,0));
                        ch.CheckName(model.getValueAt(i,1));
                        ch.CheckSurName(model.getValueAt(i,2));
                        ch.CheckAge(model.getValueAt(i,3));
                        ch.CheckSalary(model.getValueAt(i,4));
                        ch.CheckSpecialization(model.getValueAt(i,5));

                        s.setName((String)model.getValueAt(i,1));
                        s.setSalary(Integer.parseInt(model.getValueAt(i,4).toString()));
                        s.setSurname((String) model.getValueAt(i,2));
                        s.setAge(Integer.parseInt(model.getValueAt(i,3).toString()));
                        s.setSpecialization((String) model.getValueAt(i,5));
                        em.merge(s);
                    }
                }
                for (Staff staff : sl) {
                    em.persist(staff);
                }
                em.getTransaction().commit();
                staff1.setEnabled(false);
            }catch (CheckHuman ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        JButton edit = new JButton("Edit");
        edit.setToolTipText("edit list");
        edit.addActionListener(e ->  staff1.setEnabled(true));

        JButton add = new JButton(" Add");
        add.setToolTipText(" Add new record in list");
        add.addActionListener(e -> new AddStaff().show(em));

        JButton delete = new JButton("Delete");
        delete.setToolTipText(" Delete record in list");
        delete.addActionListener(e -> {
            int[] indexs=staff1.getSelectedRows();
            int n = JOptionPane.showConfirmDialog(
                    StaffList,
                    "Are you sure?",
                    "",
                    JOptionPane.YES_NO_OPTION);
            if(n==0) {
                if (!em.getTransaction().isActive()) em.getTransaction().begin();
                for (int index : indexs) {
                    em.remove(em.find(Staff.class, model.getValueAt(index, 0)));
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
                    NodeList nlBooks = doc.getElementsByTagName("staff");
                    for (int temp = 0; temp < nlBooks.getLength(); temp++) {

                        Node elem = nlBooks.item(temp);

                        NamedNodeMap attrs = elem.getAttributes();

                        String n = attrs.getNamedItem("Name").getNodeValue();
                        String s = attrs.getNamedItem("Surname").getNodeValue();
                        String a = attrs.getNamedItem("Age").getNodeValue();
                        String sa = attrs.getNamedItem("Salary").getNodeValue();
                        String sp = attrs.getNamedItem("Specialization").getNodeValue();


                        model.addRow(new String[]{"", n, s, a, sa, sp});
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
                Node list=doc.createElement("stafflist");
                doc.appendChild(list);
// Создание дочерних элементов book и присвоение значений атрибутам
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    Element staff = doc.createElement("staff");
                    list.appendChild(staff);
                    staff.setAttribute("ID", model.getValueAt(i, 0).toString());
                    staff.setAttribute("Name", model.getValueAt(i, 1).toString());
                    staff.setAttribute("Surname", model.getValueAt(i, 2).toString());
                    staff.setAttribute("Age", model.getValueAt(i, 3).toString());
                    staff.setAttribute("Salary", model.getValueAt(i, 4).toString());
                    staff.setAttribute("Specialization", model.getValueAt(i, 5).toString());
                }
                try {
// Создание преобразователя документа
                    Transformer trans = TransformerFactory.newInstance().newTransformer();
// Создание файла с именем staffs.xml для записи документа
                    java.io.FileWriter fw = new FileWriter("staffs.xml");
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
        
        StaffList.setLayout(new BorderLayout());
        StaffList.add(toolBar, BorderLayout.NORTH);
        String [] columns={"ID", "Name","Surname", "Age" , "Salary", "Specialization"};
        List<Staff> st = new Staff(em).GetAllStaff();
        JButton refresh=new JButton("Refresh");
        refresh.addActionListener(e -> {
            List<Staff> st1 = new Staff(em).GetAllStaff();
            model=new DefaultTableModel(new Staff().ListToObject(st1),columns);
            staff1.setModel(model);
        });
        toolBar.add(refresh);
        model= new DefaultTableModel(new Staff().ListToObject(st),columns);
        staff1 = new JTable(model){


            public boolean isCellEditable(int row, int col)
            {
                return (col != 0);
            }
        };
        staff1.setSize(800,300);

        staff1.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(staff1);
        StaffList.add(scroll, BorderLayout.CENTER);
        StaffList.setVisible(true);


    }
    public boolean visible(){
        return StaffList.isVisible();
    }
    public void setvisible(){
        StaffList.setVisible(true);
    }

    public JFrame getStaffList() {
        return StaffList;
    }
}
