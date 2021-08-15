package screens;

import daos.DAOCartao;
import daos.DAOUsuario;
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
import models.Cartao;
import models.Usuario;

/**
 *
 * @author Matheus
 */
public class CartaoScreen extends JDialog {

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
    JPanel panL4C1 = new JPanel(); //Painel referente a posição da grade: Linha 4 - Coluna 1
    JPanel panL4C2 = new JPanel(); //Painel referente a posição da grade: Linha 4 - Coluna 2
    JPanel panL5C1 = new JPanel(); //Painel referente a posição da grade: Linha 5 - Coluna 1
    JPanel panL5C2 = new JPanel(); //Painel referente a posição da grade: Linha 5 - Coluna 2
    JPanel panFK = new JPanel(new GridLayout(2, 1));

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
    DAOCartao daoCartao = new DAOCartao();
    DAOUsuario daoUsuario = new DAOUsuario();

//INSTANCIA DOS LABELS
    JLabel lblId = new JLabel("ID");
    JLabel lblSaldo = new JLabel("SALDO");
    JLabel lblNome = new JLabel("NOME");  
    JLabel lblUsuarioID = new JLabel("USUARIO ID");

//INSTANCIA DOS TEXTFIELD
    JTextField txtId = new JTextField(10);
    JTextField txtSaldo = new JTextField(10);
    JTextField txtNome = new JTextField(15);   
    JTextField txtUsuarioID = new JTextField(10);
//INSTANCIA COMBOBOX 
    JComboBox usuarioBox; 

//INSTANCIA DAS ENTIDADES
    Cartao cartao = new Cartao();
//INSTANCIA DAS TABLE SCREENS
    CartaoTableScreen cartaoTableScreen;

    public CartaoScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - CARTAO");
    
        usuarioBox = components.createComboBox(daoUsuario.getFKList());
        
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
        panBody.setLayout(new GridLayout(4, 2));

        //Prenchimento por Linha
        panBody.add(panL1C1);
        panBody.add(panL1C2);
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);
        panBody.add(panL5C1);
        panBody.add(panL5C2);

        //Prenchimento Linha 1
        panL1C1.add(lblSaldo);
        panL1C2.add(txtSaldo);

        //Prenchimento Linha 2
        panL2C1.add(lblNome);
        panL2C2.add(txtNome);

        //Prenchimento Linha 3
        panFK.add(components.createChildPanel(txtUsuarioID, new FlowLayout(FlowLayout.CENTER)));
        panFK.add(components.createChildPanel(usuarioBox, new FlowLayout(FlowLayout.CENTER)));
        
        panL3C1.add(lblUsuarioID);
        panL3C2.add(panFK);

        //Prenchimento Linha 4        

        //Prenchimento Linha 5
        panL5C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtId.getText().trim().isEmpty()) {
                        if (!txtId.getText().matches("[0-9]*")) {
                            throw new Exception("Digite somente números!");
                        } 
                        if (txtId.getText().length() != 16) {
                            throw new Exception("O ID deve ter obrigatoriamente 16 digítos!");                            
                        }
                        
                        cartao = daoCartao.get(txtId.getText());
                        if (cartao != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtSaldo.setEditable(false);
                            txtNome.setEditable(false);                          
                            txtUsuarioID.setEditable(false);
                            usuarioBox.setEnabled(false);

                            txtId.setText(String.valueOf(cartao.getId()));
                            txtSaldo.setText(String.valueOf(cartao.getSaldo()));
                            txtNome.setText(String.valueOf(cartao.getNome()));
                            usuarioBox.setSelectedItem(cartao.getUsuarioID().toFK());
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtSaldo.setEditable(true);
                            txtNome.setEditable(true);
                            usuarioBox.setEnabled(true);
                            txtUsuarioID.setEditable(true);
                            txtSaldo.setText("");
                            txtNome.setText("");     
                            usuarioBox.setSelectedIndex(0);
                            txtUsuarioID.setText("");
                           
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
                txtSaldo.requestFocus();

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
                txtSaldo.setEditable(true);
                txtNome.setEditable(true);  
                usuarioBox.setEnabled(true);
                txtUsuarioID.setEditable(true);
                txtSaldo.requestFocus();

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
                        cartao = new Cartao();
                    }
                    if (txtSaldo.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } else {
                        cartao.setId(txtId.getText());
                        cartao.setSaldo(Double.valueOf(txtSaldo.getText()));
                        cartao.setNome(txtNome.getText());   
                        cartao.setDataCadastro(new Date());
                        cartao.setUsuarioID(selectUsuario());
                    }
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        System.out.println(cartao.toString());
                        daoCartao.insert(cartao);
                        System.out.println("CARTAO ADICIONADO!");
                    } else if (actionController.equalsIgnoreCase("UPDATE")) {                        
                        daoCartao.update(cartao);
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

                    txtId.setEditable(true);
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
                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = "DELETE";
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    txtId.setEditable(true);
                    textFieldInitialConfiguration();
                    txtId.requestFocus();

                    clearAllFields();
                    daoCartao.delete(cartao);

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "CARTAO EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("CARTAO EXCLUÍDO");
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
                    cartaoTableScreen.dispose();
                    listController = false;
                }
                List<Cartao> cartaos = daoCartao.list();
                cartaoTableScreen = new CartaoTableScreen(cartaos);
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

                txtId.requestFocus();

            }
        });

//Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    cartaoTableScreen.dispose();
                    listController = false;
                }               
                dispose();
            }
        });
        
         txtUsuarioID.getDocument().addDocumentListener(new DocumentListener() {
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

    private void buttonsInitialConfiguration() {	//BUTTONS INITIAL CONFIGURATIONS
        btnRetrieve.setEnabled(true);
        btnCancel.setVisible(false);
        btnList.setVisible(true);
        btnCreate.setEnabled(false);
        btnAction.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void textFieldInitialConfiguration() {	//TEXTFIELD INITIAL CONFIGURATIONS
        txtId.setEditable(true);
        txtSaldo.setEditable(false);
        txtNome.setEditable(false);      
        txtUsuarioID.setEditable(false);
        usuarioBox.setEnabled(false);
    }

    private void clearAllFields() {

        txtId.setText("");
        txtSaldo.setText("");
        txtNome.setText("");        
        txtUsuarioID.setText("");
        usuarioBox.setSelectedIndex(0);
    }
    
     private Usuario selectUsuario() {
        String fk = usuarioBox.getSelectedItem().toString();
        Integer id = Integer.valueOf(fk.split(" - ")[0]); 
        return daoUsuario.get(id);
    }

    private void changeSearch() {
        String search = txtUsuarioID.getText();

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> elements;

        if (search.trim().isEmpty()) {
            elements = daoUsuario.getFKList();

        } else {
            elements = daoUsuario.getSpecificFKList(daoUsuario.searchFast(search));
        }

        if (elements != null && !elements.isEmpty()) {
            for (String str : elements) {
                model.addElement(str);
            }
            usuarioBox.setModel(model);
        }
    }

}
