package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private JButton gestionDeMachinesButton;
    private JButton gestionDeSallesButton;
    private JButton ajouterSalleAUneButton;





    public Main(){
        setLayout(new FlowLayout()); // You can choose another layout manager as needed

        // Create buttons and add them to the frame
        gestionDeMachinesButton = new JButton("Gestion de Machines");
        gestionDeSallesButton = new JButton("Gestion de Salles");
        ajouterSalleAUneButton = new JButton("Ajouter Salle à Une");

        add(gestionDeMachinesButton);
        add(gestionDeSallesButton);
        add(ajouterSalleAUneButton);

        // Set frame properties
        setTitle("Gestion des machines et salles");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame o

        gestionDeMachinesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                MachineForm machineForm = new MachineForm();
                machineForm.setVisible(true);
            }
        });
        gestionDeSallesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SalleForm salleForm = new SalleForm();
                salleForm.setVisible(true);
            }
        });
        ajouterSalleAUneButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SalleMachine salleForm = new SalleMachine();
                salleForm.setVisible(true);
            }
        });
    }


    public static void main(String[] args) {

        Main mainForm = new Main();
        mainForm.setVisible(true);

    }
}
