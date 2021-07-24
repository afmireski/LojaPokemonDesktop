/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author AFMireski
 */
public class MainScreen extends JDialog {

    //INSTANCIA DOS CONTAINERS
    Container container;

//INSTANCIA DOS PANELS
    //PANELS BASE
    JPanel panNorth = new JPanel();
    JPanel panSouth = new JPanel();
    JPanel panEast = new JPanel();
    JPanel panWest = new JPanel();
    JPanel panBody = new JPanel();

//INSTANCIA DOS MENUS
    JMenu menu = new JMenu("Cadastros");
    JMenuBar menuBar = new JMenuBar();
    JMenuItem[] menuItens = {
        createMenuItem("Endereço", (ae) -> {
            EnderecoScreen enderecoScreen = new EnderecoScreen();  
        }),            
        createMenuItem("Tipo Pokémon", (ae) -> {
            ///TODO: INSTACIAR TIPO POKÉMON SCREEN
        }),        
        createMenuItem("Novidades", (ae) -> {
            ///TODO: INSTACIAR NOVIDADES SCREEN
        })
    };

    private void createMenu() {
        List<JMenuItem> itens = Arrays.asList(menuItens);

        itens.forEach((it) -> {
            menu.add(it);
        });

        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String name, ActionListener al) {
        JMenuItem mi = new JMenuItem(name);
        mi.addActionListener(al);

        return mi;
    }

    public MainScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Loja Pokémon - Painel Administrativo");

        //CONTAINER CONFIGURATIONS
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panNorth, BorderLayout.NORTH);
        container.add(panSouth, BorderLayout.SOUTH);
        container.add(panEast, BorderLayout.EAST);
        container.add(panWest, BorderLayout.WEST);
        container.add(panBody, BorderLayout.CENTER);


        //MENU CONFIGURATIONS
        createMenu();

        pack();
        setModal(true);
        setVisible(true);
    }

}
