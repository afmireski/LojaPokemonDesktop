package screens;

import daos.DAOCartao;
import daos.DAOPedido;
import daos.DAOUsuario;
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
import models.Cartao;
import models.Pedido;
import models.Usuario;

/**
 *
 * @author AFMireski
 */
public class PedidoScreen extends JDialog {

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
    
    JPanel panCartao = new JPanel(new GridLayout(2, 1));
    
    JPanel panUsuario = new JPanel(new GridLayout(2, 1));

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
    DAOPedido daoPedido = new DAOPedido();
    DAOUsuario daoUsuario = new DAOUsuario();
    DAOCartao daoCartao = new DAOCartao();

//INSTANCIA DOS LABELS
    JLabel lblId = new JLabel("ID");
    JLabel lblCartao = new JLabel("CARTÃO");
    JLabel lblUsuario = new JLabel("USUÁRIO");

//INSTANCIA DOS TEXTFIELD
    JTextField txtId = new JTextField(10);
    JTextField txtCartao = new JTextField(20);
    JTextField txtUsuario = new JTextField(15);
    
//INSTACIA DOS COMBO BOX
    JComboBox usuarioBox;
    JComboBox cartaoBox;

//INSTANCIA DAS ENTIDADES
    Pedido pedido = new Pedido();
//INSTANCIA DAS TABLE SCREENS
    PedidoTableScreen pedidoTableScreen;

    public PedidoScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - PEDIDO");
        
        usuarioBox = components.createComboBox(daoUsuario.getFKList());
        cartaoBox = components.createComboBox(daoCartao.toFKList());

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
        panBody.setLayout(new GridLayout(3, 2));

        //Prenchimento por Linha
        panBody.add(panL1C1);
        panBody.add(panL1C2);
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);

        //Prenchimento Linha 1
        panCartao.add(components.createChildPanel(txtCartao, new FlowLayout(FlowLayout.CENTER)));
        panCartao.add(components.createChildPanel(cartaoBox, new FlowLayout(FlowLayout.CENTER)));
        
        panL1C1.add(lblCartao);
        panL1C2.add(panCartao);

        //Prenchimento Linha 2
        panUsuario.add(components.createChildPanel(txtUsuario, new FlowLayout(FlowLayout.CENTER)));
        panUsuario.add(components.createChildPanel(usuarioBox, new FlowLayout(FlowLayout.CENTER)));
        
        panL2C1.add(lblUsuario);
        panL2C2.add(panUsuario);

        //Prenchimento Linha 3
        panL3C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtId.getText().trim().isEmpty()) {
                        pedido = daoPedido.get(Integer.valueOf(txtId.getText()));
                        if (pedido != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtCartao.setEditable(false);
                            cartaoBox.setEnabled(false);
                            txtUsuario.setEditable(false);
                            usuarioBox.setEnabled(false);

                            txtId.setText(String.valueOf(pedido.getId()));
                            cartaoBox.setSelectedItem(pedido.getCartaoID().toFK());
                            usuarioBox.setSelectedItem(pedido.getUsuarioID().toFK());
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtCartao.setEditable(true);
                            cartaoBox.setEnabled(true);
                            txtUsuario.setEditable(true);
                            usuarioBox.setEnabled(true);
                            
                            txtCartao.setText("");
                            cartaoBox.setSelectedIndex(0);
                            txtUsuario.setText("");
                            usuarioBox.setSelectedIndex(0);
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
                txtCartao.requestFocus();

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

                txtId.setEditable(false);
                txtCartao.setEditable(true);
                cartaoBox.setEnabled(true);
                txtUsuario.setEditable(true);
                usuarioBox.setEnabled(true);
                txtCartao.requestFocus();

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
                        pedido = new Pedido();
                    }

                    pedido.setId(Integer.valueOf(txtId.getText()));
                    pedido.setDataPedido(new Date());
                    pedido.setCartaoID(selectCartao());
                    pedido.setUsuarioID(selectUsuario());
                    if (actionController.equals(CrudAction.CREATE)) {
                        daoPedido.insert(pedido);
                    } else if (actionController.equals(CrudAction.UPDATE)) {
                        daoPedido.update(pedido);
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
                    daoPedido.delete(pedido);
                    
                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = CrudAction.DELETE;
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    txtId.setEditable(true);
                    textFieldInitialConfiguration();
                    txtId.requestFocus();

                    clearAllFields();

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "PEDIDO EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("PEDIDO EXCLUÍDO");
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
                    pedidoTableScreen.dispose();
                    listController = false;
                }
                List<Pedido> pedidos = daoPedido.list();
                pedidoTableScreen = new PedidoTableScreen(pedidos);
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
                    pedidoTableScreen.dispose();
                    listController = false;
                }
                dispose();
            }
        });
        
        txtUsuario.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                changeSearchUsuario();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                changeSearchUsuario();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }
        });

        txtCartao.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                changeSearchCartao();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                changeSearchCartao();
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
        txtId.setEditable(true);
        txtCartao.setEditable(false);
        cartaoBox.setEnabled(false);
        txtUsuario.setEditable(false);
        usuarioBox.setEnabled(false);
    }

    private void clearAllFields() {
        txtId.setText("");
        txtCartao.setText("");
        cartaoBox.setSelectedIndex(0);
        txtUsuario.setText("");
        usuarioBox.setSelectedIndex(0);
    }
    
    private Usuario selectUsuario() {
        String fk = usuarioBox.getSelectedItem().toString();
        Integer key = Integer.valueOf(fk.split(" - ")[0]);
        return daoUsuario.get(key);
    }
    
    private Cartao selectCartao() {
        String fk = cartaoBox.getSelectedItem().toString();
        String key = fk.split(" - ")[0];
        return daoCartao.get(key);
    }
    
    private void changeSearchUsuario() {
        String search = txtUsuario.getText();

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
    
    private void changeSearchCartao() {
        String search = txtCartao.getText();

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> elements;

        if (search.trim().isEmpty()) {
            elements = daoCartao.toFKList();

        } else {
            elements = daoCartao.toSpecificFKList(daoCartao.searchFast(search));
        }

        if (elements != null && !elements.isEmpty()) {
            for (String str : elements) {
                model.addElement(str);
            }
            cartaoBox.setModel(model);
        }
    }

}
