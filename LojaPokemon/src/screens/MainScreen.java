/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import functions.ConvertFromEnum;
import functions.ConvertToEnum;
import functions.FileManager;
import functions.VerifyPK;
import helpers.BuildConfirmDialog;
import helpers.BuildMessageDialog;
import helpers.ErrorTools;
import helpers.GenericComponents;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import tools.CaixaDeFerramentas;
import tools.CopiarArquivos;
import tools.DiretorioDaAplicacao;
import tools.ImagemAjustada;
import tools.Tools;

/**
 *
 * @author AFMireski
 */
public class MainScreen extends JFrame {
    
    //INSTANCIA DOS HELPERS
    GenericComponents components = new GenericComponents();
    BuildMessageDialog messageDialog;
    BuildConfirmDialog confirmDialog;
    ErrorTools errorTools = new ErrorTools();

//INSTANCIA DOS FUNCTIONS
    ConvertFromEnum convertFromEnum = new ConvertFromEnum();
    ConvertToEnum convertToEnum = new ConvertToEnum();
    VerifyPK verifyPK = new VerifyPK();
    FileManager fileManager = new FileManager();

//INSTANCIA DAS TOOLS
    final private CaixaDeFerramentas cf = new CaixaDeFerramentas();
    final private ImagemAjustada imagemAjustada = new ImagemAjustada();
    final private DiretorioDaAplicacao dda = new DiretorioDaAplicacao();
    final private CopiarArquivos copiarArquivos = new CopiarArquivos();
    Tools tools = new Tools();

    //INSTANCIA DOS CONTAINERS
    Container container;

//INSTANCIA DOS PANELS
    //PANELS BASE
    JPanel panNorth = new JPanel();
    JPanel panSouth = new JPanel();
    JPanel panEast = new JPanel();
    JPanel panWest = new JPanel();
    JPanel panBody = new JPanel();
    
//INSTANCIA DOS LABELS
    JLabel img = new JLabel();

//INSTANCIA DOS MENUS
    JMenu menu = new JMenu("Cadastros");
    JMenuBar menuBar = new JMenuBar();
    JMenuItem[] menuItens = {
        createMenuItem("Endereço", (ae) -> {
            EnderecoScreen enderecoScreen = new EnderecoScreen();  
        }),            
        createMenuItem("Tipo Pokémon", (ae) -> {
            TipopokemonScreen tipopokemonScreen = new TipopokemonScreen();
        }),        
        createMenuItem("Novidades", (ae) -> {
            NovidadesScreen novidadesScreen = new NovidadesScreen();
        }),
        createMenuItem("Pokémon", (ae) -> {
            PokemonScreen pokemonScreen = new PokemonScreen();
        }),
        createMenuItem("Preço", (ae) -> {
            PrecoScreen precoScreen = new PrecoScreen();
        }),
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
    
    private void setImage() {
        String path = dda.getDiretorioDaAplicacao() + "/src/images/loja_pokemon.png";
        System.out.println(path);
        ImageIcon icon = imagemAjustada.getImagemAjustada(path, 500, 500);
        img.setIcon(icon);
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

        panNorth.add(new JLabel("LOJA POKÉMON"));
        
        setImage();
        
        panBody.add(img);

        //MENU CONFIGURATIONS
        createMenu();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {                
                dispose();
            }
        });
        
        pack();
        setVisible(true);
    }

}
