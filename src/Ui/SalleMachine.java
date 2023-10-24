package Ui;

import dao.IDao;
import entities.Machine;
import entities.Salle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class SalleMachine extends JFrame {
    private JComboBox salleCombo;
    private JTable MachinesTable;
    private JPanel pn;
    IDao<Salle> sallesDAO;
    List<Salle> mesSalles;
    IDao<Machine> machinesDAO;
    List<Machine> mesMachines;
    DefaultTableModel model = null;



public SalleMachine(){
    setSize(500,300);

    setTitle("Gestion de machine");
    setContentPane(pn);
    MachinesTable.setModel(new DefaultTableModel(
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
}

    private void initForm() {
    loadSalles();
    loadTable();
    }


    public void loadTable(){
        try{
            machinesDAO= (IDao<Machine>) Naming.lookup("rmi://localhost:1099/machine");
            mesMachines = machinesDAO.findAll();
            model = (DefaultTableModel) MachinesTable.getModel();
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
            salleCombo.addItem(m);
        }

    }

}
