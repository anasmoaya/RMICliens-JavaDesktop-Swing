package Ui;

import dao.IDao;
import entities.Machine;
import entities.Salle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class SalleForm extends JFrame {
    private JTextField code;
    private JButton ajouterButton;
    private JButton supprimerButton;
    private JButton modifierButton;
    private JTable SalleTable;
    private JPanel SalleFrame;
    private IDao<Salle> SalleDAO;
    private List<Salle> salles;
    DefaultTableModel model;
    int id;

    public SalleForm(){
        setSize(500,300);

        setTitle("Gestion de Salle");
        setContentPane(SalleFrame);
        SalleTable.setModel(new DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "id",
                        "Code"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false,false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        loadSalles();


        ajouterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Salle salle = new Salle(code.getText());
                try {
                    SalleDAO.create(salle);
                    loadSalles();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        SalleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = Integer.parseInt(model.getValueAt(SalleTable.getSelectedRow(), 0).toString());
                code.setText(model.getValueAt(SalleTable.getSelectedRow(), 1).toString());
            }
        });
        supprimerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    SalleDAO.delete(SalleDAO.findById(id));
                    loadSalles();
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
                    SalleDAO.update(SalleDAO.findById(id));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    void loadSalles(){
        try{
            SalleDAO= (IDao<Salle>) Naming.lookup("rmi://localhost:1099/salle");
            salles = SalleDAO.findAll();
            model = (DefaultTableModel) SalleTable.getModel();
        }catch (NotBoundException | MalformedURLException | RemoteException e){
            System.out.println(e.getMessage() +"\n"+ e.getCause());
        }
        model.setRowCount(0);
        for(Salle s :salles)
            model.addRow(new Object[]{
                    s.getId(),
                    s.getCode()
            });


    }
}
