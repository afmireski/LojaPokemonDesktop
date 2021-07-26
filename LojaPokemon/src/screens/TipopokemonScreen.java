package screens;

import daos.DAOTipopokemon;
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
import models.Tipopokemon;

/**
 *
 * @author Matheus
 */
public class TipopokemonScreen extends JDialog {

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
    String actionController;
    boolean listController = false;
    DAOTipopokemon daoTipopokemon = new DAOTipopokemon();

//INSTANCIA DOS LABELS
    JLabel lblId = new JLabel("ID");
    JLabel lblSigla = new JLabel("SIGLA");
    JLabel lblDescricao = new JLabel("DESCRICAO");

//INSTANCIA DOS TEXTFIELD
    JTextField txtId = new JTextField(5);
    JTextField txtSigla = new JTextField(5);
    JTextField txtDescricao = new JTextField(20);

//INSTANCIA DAS ENTIDADES
    Tipopokemon tipopokemon = new Tipopokemon();
//INSTANCIA DAS TABLE SCREENS
    TipopokemonTableScreen tipopokemonTableScreen;

    public TipopokemonScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - TIPO POKÉMON");

        //BUTTONS INITIAL CONFIGURATIONS
        buttonsInitialConfiguration();

        //TEXTFIELD INITIAL CONFIGURATIONS
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
        panNorth.add(lblId);
        panNorth.add(txtId);

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
        panBody.setLayout(new GridLayout(3, 2));

        //Prenchimento por Linha
        panBody.add(panL1C1);
        panBody.add(panL1C2);
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);

        //Prenchimento Linha 1
        panL1C1.add(lblSigla);
        panL1C2.add(txtSigla);

        //Prenchimento Linha 2
        panL2C1.add(lblDescricao);
        panL2C2.add(txtDescricao);

        //Prenchimento Linha 3
        panL3C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtId.getText().trim().isEmpty()) {
                        tipopokemon = daoTipopokemon.get(Integer.valueOf(txtId.getText()));
                        if (tipopokemon != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtSigla.setEditable(false);
                            txtDescricao.setEditable(false);

                            txtId.setText(String.valueOf(tipopokemon.getId()));
                            txtSigla.setText(String.valueOf(tipopokemon.getSigla()));
                            txtDescricao.setText(String.valueOf(tipopokemon.getDescricao()));
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtSigla.setEditable(true);
                            txtDescricao.setEditable(true);
                            txtSigla.setText("");
                            txtDescricao.setText("");
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

                txtId.setEditable(false);
                txtSigla.requestFocus();

                actionController = "CREATE";

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

                txtId.setEditable(false);
                txtSigla.setEditable(true);
                txtDescricao.setEditable(true);
                txtSigla.requestFocus();

                actionController = "UPDATE";

                btnAction.setText("Atualizar na Lista");
            }
        });

        //BTN ACTION ACTION LISTENER
        btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        tipopokemon = new Tipopokemon();
                    }
                    if (txtSigla.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } else {
                        tipopokemon.setId(Integer.valueOf(txtId.getText()));
                        tipopokemon.setSigla(txtSigla.getText());
                        tipopokemon.setDescricao(txtDescricao.getText());
                    }
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        daoTipopokemon.insert(tipopokemon);
                        System.out.println("TIPOPOKEMON ADICIONADO!");
                    } else if (actionController.equalsIgnoreCase("UPDATE")) {
                        daoTipopokemon.update(tipopokemon);
                        System.out.println("LISTA ATUALIZADA!");
                    } else {
                        throw new Exception("Falha ao executar a ação na lista");

                    }

                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);
                    btnUpdate.setVisible(true);
                    btnUpdate.setEnabled(false);
                    btnCreate.setVisible(true);
                    btnCreate.setEnabled(false);
                    btnCancel.setVisible(false);

                    textFieldInitialConfiguration();
                    txtId.requestFocus();
                    clearAllFields();

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
                    daoTipopokemon.delete(tipopokemon);
                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = "DELETE";
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    textFieldInitialConfiguration();
                    
                    txtId.requestFocus();
                    clearAllFields();
                    
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "TIPOPOKEMON EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("TIPOPOKEMON EXCLUÍDO");
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
                    tipopokemonTableScreen.dispose();
                    listController = false;
                }
                List<Tipopokemon> tipopokemons = daoTipopokemon.list();
                tipopokemonTableScreen = new TipopokemonTableScreen(tipopokemons);
                listController = true;
            }
        });
        //BTN CANCEL ACTION LISTENER
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //RETORNA A TELA AO ESTADO INICIAL
                //BUTTONS INITIAL CONFIGURATIONS
                buttonsInitialConfiguration();
                
                //TEXTFIELD INITIAL CONFIGURATIONS
                textFieldInitialConfiguration();
                
                clearAllFields();
                txtId.requestFocus();

            }
        });

//Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    tipopokemonTableScreen.dispose();
                    listController = false;
                }
                dispose();
            }
        });

        pack();
        setModal(true);
        setVisible(true);
    }

    private void buttonsInitialConfiguration() {
        btnRetrieve.setEnabled(true);
        btnCancel.setVisible(false);
        btnList.setVisible(true);
        btnCreate.setEnabled(false);
        btnAction.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void textFieldInitialConfiguration() {
        txtId.setEditable(true);
        txtSigla.setEditable(false);
        txtDescricao.setEditable(false);
    }

    private void clearAllFields() {
        txtId.setText("");
        txtSigla.setText("");
        txtDescricao.setText("");
    }
}
