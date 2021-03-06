package screens;

import daos.DAOEndereco;
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
import java.util.Arrays;
import javax.swing.JComboBox;
import models.Endereco;
import models.EnderecoPK;

/**
 *
 * @author afmireski
 */
public class EnderecoScreen extends JDialog {

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
    JPanel panPKcep = new JPanel();
    JPanel panPKn = new JPanel();

    //BODY PANELS
    JPanel panL1C1 = new JPanel(); //Painel referente a posi????o da grade: Linha 1 - Coluna 1
    JPanel panL1C2 = new JPanel(); //Painel referente a posi????o da grade: Linha 1 - Coluna 2
    JPanel panL2C1 = new JPanel(); //Painel referente a posi????o da grade: Linha 2 - Coluna 1
    JPanel panL2C2 = new JPanel(); //Painel referente a posi????o da grade: Linha 2 - Coluna 2
    JPanel panL3C1 = new JPanel(); //Painel referente a posi????o da grade: Linha 3 - Coluna 1
    JPanel panL3C2 = new JPanel(); //Painel referente a posi????o da grade: Linha 3 - Coluna 2
    JPanel panL4C1 = new JPanel(); //Painel referente a posi????o da grade: Linha 4 - Coluna 1
    JPanel panL4C2 = new JPanel(); //Painel referente a posi????o da grade: Linha 4 - Coluna 2
    JPanel panL5C1 = new JPanel(); //Painel referente a posi????o da grade: Linha 5 - Coluna 1
    JPanel panL5C2 = new JPanel(); //Painel referente a posi????o da grade: Linha 5 - Coluna 2
    JPanel panL6C1 = new JPanel(); //Painel referente a posi????o da grade: Linha 6 - Coluna 1
    JPanel panL6C2 = new JPanel(); //Painel referente a posi????o da grade: Linha 6 - Coluna 2

//INSTANCIA DOS BUTTONS
    JButton btnCreate = components.buttonWithIcon("Create", "/icons/create.png");
    JButton btnRetrieve = components.buttonWithIcon("Retrieve", "/icons/retrieve.png");
    JButton btnUpdate = components.buttonWithIcon("Update", "/icons/update.png");
    JButton btnDelete = components.buttonWithIcon("Delete", "/icons/delete.png");
    JButton btnSave = components.buttonWithIcon("Save", "/icons/save.png");
    JButton btnCancel = components.buttonWithIcon("Cancel", "/icons/cancel.png");
    JButton btnList = components.buttonWithIcon("List", "/icons/list.png");

//INSTANCIA DOS CONTROLLERS
    String actionController;
    boolean listController = false;
    DAOEndereco daoEndereco = new DAOEndereco();

//INSTANCIA DOS LABELS
    JLabel lblCep = new JLabel("CEP");
    JLabel lblNCasa = new JLabel("N?? CASA");
    JLabel lblNome = new JLabel("NOME");
    JLabel lblCidade = new JLabel("CIDADE");
    JLabel lblUf = new JLabel("UF");
    JLabel lblUfDescricao = new JLabel("UF DESCRI????O");

//INSTANCIA DOS TEXTFIELD
    JTextField txtCep = new JTextField(9);
    JTextField txtNCasa = new JTextField(5);
    JTextField txtNome = new JTextField(15);
    JTextField txtCidade = new JTextField(15);
    JComboBox txtUf;

//INSTANCIA DAS ENTIDADES
    Endereco endereco = new Endereco();
    EnderecoPK pk = new EnderecoPK();
    
//INSTANCIA DAS TABLE SCREENS
    EnderecoTableScreen enderecoTableScreen;
    
//INSTANCIA DOS MAP
    String[] desc = {"ACRE", "ALAGOAS", "AMAP??", "AMAZONAS", "BAHIA", "CEAR??",
        "ESP??RITO SANTO", "GO??AS", "MARANH??O", "MATO GROSSO", "MATO GROSSO DO SUL",
        "MINAS GERAIS", "PAR??", "PARA??BA", "PARAN??", "PERNAMBUCO", "PIAU??", "RIO DE JANEIRO",
        "RIO GRANDE DO NORTE", "RIO GRANDE DO SUL", "ROND??NIA", "RORAIMA", "SANTA CATARINA",
        "S??O PAULO", "SERGIPE", "TOCANTINS", "DISTRITO FEDERAL"};

    
    public EnderecoScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - ENDERE??O");

        txtUf = components.createComboBox(Arrays.asList(desc));
        
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
        
        
        panPKcep.add(lblCep);
        panPKcep.add(txtCep);        
        panPKn.add(lblNCasa);
        panPKn.add(txtNCasa);
        
        panPK.add(panPKcep);
        panPK.add(panPKn);
        
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
        panBody.setLayout(new GridLayout(4, 2));

        //Prenchimento por Linha
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);
        panBody.add(panL4C1);
        panBody.add(panL4C2);
        panBody.add(panL6C1);
        panBody.add(panL6C2);

        //Prenchimento Linha 2
        panL2C1.add(lblNome);
        panL2C2.add(txtNome);

        //Prenchimento Linha 3
        panL3C1.add(lblCidade);
        panL3C2.add(txtCidade);

        //Prenchimento Linha 4
        panL4C1.add(lblUf);
        panL4C2.add(txtUf);

        //Prenchimento Linha 6
        panL6C2.add(btnSave);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtCep.getText().trim().isEmpty() && !txtNCasa.getText().trim().isEmpty()) {
                        pk.setCep(txtCep.getText().trim());
                        pk.setNCasa(Integer.valueOf(txtNCasa.getText()));
                        endereco = daoEndereco.get(pk);
                        if (endereco != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtNome.setEditable(false);
                            txtCidade.setEditable(false);
                            txtUf.setEnabled(false);

                            txtCep.setText(String.valueOf(endereco.getEnderecoPK().getCep()));
                            txtNCasa.setText(String.valueOf(endereco.getEnderecoPK().getNCasa()));
                            txtNome.setText(String.valueOf(endereco.getNome()));
                            txtCidade.setText(String.valueOf(endereco.getCidade()));
                            txtUf.setSelectedIndex(endereco.getUf() - 1);
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                            
                            txtNome.setEditable(true);
                            txtCidade.setEditable(true);
                            txtUf.setEnabled(true);
                            txtNome.setText("");
                            txtCidade.setText("");
                            txtUf.setSelectedIndex(0);
                        }
                    } else {
                        throw new Exception("CEP e N?? n??o podem estar vazios.");
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
                btnSave.setVisible(true);

                txtCep.setEditable(false);
                txtNCasa.setEditable(false);
                txtNome.requestFocus();

                actionController = "CREATE";
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
                btnSave.setVisible(true);

                txtCep.setEditable(false);
                txtNCasa.setEditable(false);
                txtNome.setEditable(true);
                txtCidade.setEditable(true);
                txtUf.setEnabled(true);
                txtNome.requestFocus();

                actionController = "UPDATE";
            }
        });

        //BTN ACTION ACTION LISTENER
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        endereco = new Endereco();
                    }
                    if (txtNome.getText().trim().isEmpty() || 
                            txtCidade.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos est??o preenchidos!");
                    } else {
                        pk.setCep(txtCep.getText().trim());
                        pk.setNCasa(Integer.valueOf(txtNCasa.getText()));
                        endereco.setEnderecoPK(pk);
                        endereco.setNome(txtNome.getText());
                        endereco.setCidade(txtCidade.getText());
                        endereco.setUf(txtUf.getSelectedIndex()+1);
                        endereco.setUfDescricao(desc[txtUf.getSelectedIndex()]);
                    }
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        System.out.println(endereco.toString());
                        daoEndereco.insert(endereco);
                        System.out.println("ENDERECO ADICIONADO NO BANCO!");
                    } else if (actionController.equalsIgnoreCase("UPDATE")) {
                        daoEndereco.update(endereco);
                        System.out.println("BANCO ATUALIZADO!");
                    } else {
                        throw new Exception("Falha ao executar a a????o no banco");

                    }

                    btnSave.setVisible(false);
                    btnRetrieve.setEnabled(true);
                    btnUpdate.setVisible(true);
                    btnUpdate.setEnabled(false);
                    btnCreate.setVisible(true);
                    btnCreate.setEnabled(false);
                    btnCancel.setVisible(false);

                    textFieldInitialConfiguration();
                    
                    txtCep.requestFocus();
                    
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
                        "Confirmar Exclus??o");

                int response = confirmDialog.getResponse();

                if (response == JOptionPane.YES_OPTION) {
                    daoEndereco.delete(endereco);
                    
                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = "DELETE";
                    btnSave.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    textFieldInitialConfiguration();
                    
                    txtCep.requestFocus();

                    clearAllFields();

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "ENDERECO EXCLU??DO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("ENDERECO EXCLU??DO");
                }
            }
        });

        //BTN LIST ACTION LISTENER
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // A JANELA TABELA S?? PODE SER ABERTA SE TABLESCREEN N??O ESTIVER ATIVA.
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    enderecoTableScreen.dispose();
                    listController = false;
                }
                List<Endereco> enderecos = daoEndereco.list();
                enderecoTableScreen = new EnderecoTableScreen(enderecos);
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
                txtCep.requestFocus();

                

            }
        });

        //Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    enderecoTableScreen.dispose();
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
        btnSave.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    private void textFieldInitialConfiguration() {
        txtCep.setEditable(true);
        txtNCasa.setEditable(true);
        txtNome.setEditable(false);
        txtCidade.setEditable(false);
        txtUf.setEnabled(false);
    }
    
    private void clearAllFields() {
                txtCep.setText("");
                txtNCasa.setText("");
                txtNome.setText("");
                txtCidade.setText("");
                txtUf.setSelectedIndex(0);
    }
}
