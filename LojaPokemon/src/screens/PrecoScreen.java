package screens;

import daos.DAOPokemon;
import daos.DAOPreco;
import enums.CrudAction;
import functions.ConvertToEnum;
import functions.ConvertFromEnum;
import functions.VerifyPK;
import helpers.BuildConfirmDialog;
import helpers.BuildMessageDialog;
import helpers.ErrorTools;
import helpers.GenericComponents;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import tools.CaixaDeFerramentas;
import tools.Tools;
import enums.DialogMessageType;
import enums.DialogConfirmType;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Preco;
import models.PrecoPK;
import tools.DateTextField;

/**
 *
 * @author AFMireski
 */
public class PrecoScreen extends JDialog {

//INSTANCIA DOS HELPERS
    GenericComponents components = new GenericComponents();
    BuildMessageDialog messageDialog;
    BuildConfirmDialog confirmDialog;
    ErrorTools errorTools = new ErrorTools();

//INSTANCIA DOS FUNCTIONS
    ConvertFromEnum convertFromEnum = new ConvertFromEnum();
    ConvertToEnum convertToEnum = new ConvertToEnum();
    VerifyPK verifyPK = new VerifyPK();

//INSTANCIA DAS TOOLS
    final private CaixaDeFerramentas cf = new CaixaDeFerramentas();
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
    
    //NORTH PANELS
    JPanel panPK = new JPanel(new GridLayout(1, 2));
    JPanel panPoke = new JPanel();
    JPanel panSubPoke = new JPanel(new GridLayout(2, 1));
    JPanel panData = new JPanel();

//BODY PANELS
    JPanel panL1C1 = new JPanel(); //Painel referente a posição da grade: Linha 1 - Coluna 1
    JPanel panL1C2 = new JPanel(); //Painel referente a posição da grade: Linha 1 - Coluna 2
    JPanel panL2C1 = new JPanel(); //Painel referente a posição da grade: Linha 2 - Coluna 1
    JPanel panL2C2 = new JPanel(); //Painel referente a posição da grade: Linha 2 - Coluna 2
    JPanel panL3C1 = new JPanel(); //Painel referente a posição da grade: Linha 3 - Coluna 1
    JPanel panL3C2 = new JPanel(); //Painel referente a posição da grade: Linha 3 - Coluna 2

//INSTANCIA DOS BUTTONS
    JButton btnCreate = components.buttonWithIcon("Create", "/icons/create.png");
    JButton btnRetrieve = components.buttonWithIcon("Retrieve", "/icons/retrieve.png");
    JButton btnUpdate = components.buttonWithIcon("Update", "/icons/update.png");
    JButton btnDelete = components.buttonWithIcon("Delete", "/icons/delete.png");
    JButton btnAction = new JButton("Add to List");
    JButton btnCancel = components.buttonWithIcon("Cancel", "/icons/cancel.png");
    JButton btnList = components.buttonWithIcon("List", "/icons/list.png");

//INSTANCIA DOS CONTROLLERS
    CrudAction actionController;
    boolean listController = false;
    DAOPreco daoPreco = new DAOPreco();
    DAOPokemon daoPokemon = new DAOPokemon();

//INSTANCIA DOS LABELS
    JLabel lblPokemonID = new JLabel("POKÉMON");
    JLabel lblDataVigencia = new JLabel("DATA VIGÊNCIA");
    JLabel lblValor = new JLabel("VALOR");

//INSTANCIA DOS TEXTFIELD
    JTextField txtSearchPokemon = new JTextField(10);
    DateTextField txtDataVigencia = new DateTextField();
    JTextField txtValor = new JTextField(10);
    
//INSTANCIA DOS COMBOBOX
    JComboBox pokemonBox;

//INSTANCIA DAS ENTIDADES
    Preco preco = new Preco();
    PrecoPK pk = new PrecoPK();
    
//INSTANCIA DAS TABLE SCREENS
    PrecoTableScreen precoTableScreen;

    public PrecoScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - PREÇO");
        
        pokemonBox = components.createComboBox(daoPokemon.getFKList());

        buttonsInitialConfiguration();
        textFieldInitialConfiguration();

        //CONTAINER CONFIGURATIONS
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panNorth, BorderLayout.NORTH);
        container.add(panSouth, BorderLayout.SOUTH);
        container.add(panEast, BorderLayout.EAST);
        container.add(panWest, BorderLayout.WEST);
        container.add(panBody, BorderLayout.CENTER);

        //PAN NORTH CONFIGURATIONS
        panNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //PAN PK CONFIGURATIONS
        panPoke.add(lblPokemonID);
        panPoke.add(panSubPoke);
        
        panSubPoke.add(txtSearchPokemon);
        panSubPoke.add(pokemonBox);
        
        panData.add(lblDataVigencia);
        panData.add(txtDataVigencia);
        
        panPK.add(panPoke);
        panPK.add(panData);
        
        panNorth.add(panPK);

        panNorth.add(btnRetrieve);
        panNorth.add(btnCreate);
        panNorth.add(btnUpdate);
        panNorth.add(btnDelete);
        panNorth.add(btnList);
        panNorth.add(btnCancel);
        //PAN EAST CONFIGURATIONS
        //PAN WEST CONFIGURATIONS

        //PAN BODY CONFIGURATIONS
        panBody.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        panBody.setLayout(new GridLayout(2, 2));

        //Prenchimento por Linha
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);


        //Prenchimento Linha 2
        panL2C1.add(lblValor);
        panL2C2.add(txtValor);

        //Prenchimento Linha 3
        panL3C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (pokemonBox.getSelectedItem() != null && txtDataVigencia.getDate() != null) {
                        pk.setDataVigencia(txtDataVigencia.getDate());
                        getPokemonID();
                        
                        preco = daoPreco.get(pk);
                        if (preco != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);
;
                            txtValor.setEditable(false);

                            txtValor.setText(String.valueOf(preco.getValor()));
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtValor.setEditable(true);
                            txtValor.setText("");
                        }
                    }
                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "Data Error",
                            container);
                }
            }
        });

        //BTN CREATE ACTION LISTENER
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRetrieve.setEnabled(false);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                btnCreate.setVisible(false);
                btnCancel.setVisible(true);
                btnAction.setVisible(true);

                txtSearchPokemon.setEditable(false);
                pokemonBox.setEnabled(false);
                txtDataVigencia.setEditable(false);
                        
                txtValor.requestFocus();

                actionController = CrudAction.CREATE;

                btnAction.setText("Adicionar à Lista");
            }
        });

        //BTN UPDATE ACTION LISTENER
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRetrieve.setEnabled(false);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                btnCreate.setVisible(false);
                btnCancel.setVisible(true);
                btnAction.setVisible(true);

                txtSearchPokemon.setEditable(false);
                pokemonBox.setEnabled(false);
                txtDataVigencia.setEditable(false);
                txtValor.setEditable(true);
                txtValor.requestFocus();

                actionController = CrudAction.UPDATE;

                btnAction.setText("Atualizar na Lista");
            }
        });

        //BTN ACTION ACTION LISTENER
        btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (actionController.equals(CrudAction.CREATE)) {
                        preco = new Preco();
                    }
                    if (txtDataVigencia.getDate() == null || txtValor.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } else {
                        pk.setDataVigencia(txtDataVigencia.getDate());
                        getPokemonID();                        
                        preco.setPrecoPK(pk);
                        preco.setValor(Double.valueOf(txtValor.getText()));
                    }
                    if (actionController.equals(CrudAction.CREATE)) {
                        daoPreco.insert(preco);
                        System.out.println("PRECO ADICIONADO!");
                    } else if (actionController.equals(CrudAction.UPDATE)) {
                        daoPreco.update(preco);
                        System.out.println("LISTA ATUALIZADA!");
                    } else {

                        throw new Exception("Falha ao executar a ação no banco");

                    }

                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);
                    btnUpdate.setVisible(true);
                    btnUpdate.setEnabled(false);
                    btnCreate.setVisible(true);
                    btnCreate.setEnabled(false);
                    btnCancel.setVisible(false);
                    textFieldInitialConfiguration();

                    txtSearchPokemon.setEditable(true);
                    txtSearchPokemon.requestFocus();

                    clearAllFields();
                    
                    pk = new PrecoPK();

                } catch (Exception excep) {
                    errorTools.showExceptionStackTrace(excep);
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.ERROR,
                            excep.getMessage(),
                            "ACTION Error",
                            container);
                }
            }
        });

        //BTN DELETE ACTION LISTENER
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDialog = new BuildConfirmDialog(
                        DialogConfirmType.YES_NO,
                        "Deseja realmente excluir estes dados da lista?",
                        "Confirmar Exclusão");

                int response = confirmDialog.getResponse();

                if (response == JOptionPane.YES_OPTION) {
                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = CrudAction.DELETE;
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    txtSearchPokemon.setEditable(true);
                    textFieldInitialConfiguration();
                    txtSearchPokemon.requestFocus();

                    clearAllFields();
                    daoPreco.delete(preco);

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "PRECO EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("PRECO EXCLUÍDO");
                }
            }
        });

        //BTN LIST ACTION LISTENER
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // A JANELA TABELA SÓ PODE SER ABERTA SE TABLESCREEN NÃO ESTIVER ATIVA.
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    precoTableScreen.dispose();
                    listController = false;
                }
                List<Preco> precos = daoPreco.list();
                precoTableScreen = new PrecoTableScreen(precos);
                listController = true;
            }
        });
        //BTN CANCEL ACTION LISTENER
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //RETORNA A TELA AO ESTADO INICIAL
                buttonsInitialConfiguration();
                textFieldInitialConfiguration();
                clearAllFields();

                txtSearchPokemon.requestFocus();

            }
        });

        //Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    precoTableScreen.dispose();
                    listController = false;
                }
                dispose();
            }
        });
        
        txtSearchPokemon.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                changeSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                changeSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {                
            }
        });

        pack();
        setModal(true);
        setVisible(true);
    }

    private void buttonsInitialConfiguration() {
        //BUTTONS INITIAL CONFIGURATIONS
        btnRetrieve.setEnabled(true);
        btnCancel.setVisible(false);
        btnList.setVisible(true);
        btnCreate.setEnabled(false);
        btnAction.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void textFieldInitialConfiguration() {	
        //TEXTFIELD INITIAL CONFIGURATIONS
        txtSearchPokemon.setEditable(true);
        pokemonBox.setEnabled(true);
        txtDataVigencia.setEditable(true);
        txtValor.setEditable(false);
    }

    private void clearAllFields() {

        txtSearchPokemon.setText("");
        pokemonBox.setSelectedIndex(0);
        txtDataVigencia.setDate(new Date());
        txtValor.setText("");
    }
    
    private void getPokemonID() {
        String fk = pokemonBox.getSelectedItem().toString();
        int id = Integer.valueOf(fk.split(" - ")[0]);
        
        pk.setPokemonID(id);
    }
    
    private void changeSearch() {
        String search = txtSearchPokemon.getText();
        
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> elements;

        if (search.trim().isEmpty()) {
            elements = daoPokemon.getFKList();

        } else {
            elements = daoPokemon.getEspecificFKList(daoPokemon.searchFast(search));
        }

        if (elements != null && !elements.isEmpty()) {
            for (String str : elements) {
                model.addElement(str);
            }
            
            pokemonBox.setModel(model);
        }

    }
    

}
