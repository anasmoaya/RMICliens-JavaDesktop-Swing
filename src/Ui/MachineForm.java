package Ui;

import dao.IDao;
import entities.Machine;
import entities.Salle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineForm  extends JFrame{
    private JPanel panel1;
    private JTextField refText;
    private JTextField MarqueText;
    private JTextField PrixText;
    private JComboBox SalleCombo;
    private JPanel Actions;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JTable table1;
    private  IDao<Salle> sallesDAO;
    private  IDao<Machine> machinesDAO;
    List<Salle> mesSalles ;
    List<Machine> mesMachines ;

    DefaultTableModel model = null;
int id;


    public MachineForm(){

       setSize(500,300);

        setTitle("Gestion de machine");
        setContentPane(panel1);
        table1.setModel(new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Id", "Référence", "Marque", "Prix", "Salle"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        initForm();

        ajouterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Salle salle =(Salle) SalleCombo.getSelectedItem();
                Machine machine = new Machine(refText.getText() , MarqueText.getText() ,
                        Float.parseFloat( PrixText.getText()), salle);
                try {
                    machinesDAO.create(machine);
                    loadTable();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                loadTable();

            }
        });
        supprimerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
       table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = Integer.parseInt(model.getValueAt(table1.getSelectedRow(), 0).toString());
                refText.setText(model.getValueAt(table1.getSelectedRow(), 1).toString());
                MarqueText.setText(model.getValueAt(table1.getSelectedRow(), 2).toString());
                PrixText.setText(model.getValueAt(table1.getSelectedRow(), 3).toString());
                SalleCombo.setSelectedItem(model.getValueAt(table1.getSelectedRow(), 4));
            }
        });
        supprimerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Machine m =machinesDAO.findById(id);
                    machinesDAO.delete(m);
                    loadTable();

                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        modifierButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Salle salle =(Salle) SalleCombo.getSelectedItem();
                    Machine machine = new Machine(refText.getText() , MarqueText.getText() ,
                            Float.parseFloat( PrixText.getText()), salle);
                    machinesDAO.update(machine);
                    loadTable();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private  void initForm(){
            loadTable();
             loadSalles();
    }


    public void loadTable(){
        try{
            machinesDAO= (IDao<Machine>) Naming.lookup("rmi://localhost:1099/machine");
            mesMachines = machinesDAO.findAll();
            model = (DefaultTableModel) table1.getModel();
        }catch (NotBoundException | MalformedURLException | RemoteException e){
            System.out.println(e.getMessage() +"\n"+ e.getCause());
        }
        model.setRowCount(0);
            for(Machine m :mesMachines)
                model.addRow(new Object[]{
                        m.getId(),
                        m.getRef(),
                        m.getMarque(),
                        m.getPrix(),
                        m.getSalle()
                });


    }
    private void loadSalles() {
        try{
            sallesDAO= (IDao<Salle>) Naming.lookup("rmi://localhost:1099/salle");
            mesSalles =  sallesDAO.findAll();

        }catch (NotBoundException | MalformedURLException | RemoteException e){
            System.out.println(e.getMessage() +"\n"+ e.getCause());
        }

       for(Salle m : mesSalles){
           SalleCombo.addItem(m);
       }

    }







}
