package screens;

import daos.DAOEndereco;
import daos.DAOPessoa;
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
import java.util.Date;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Endereco;
import models.EnderecoPK;
import models.Pessoa;
import tools.DateTextField;

/**
 *
 * @author Matheus
 */
public class PessoaScreen extends JDialog {

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
    JButton btnSave = components.buttonWithIcon("Save", "/icons/save.png");
    JButton btnCancel = components.buttonWithIcon("Cancel", "/icons/cancel.png");
    JButton btnList = components.buttonWithIcon("List", "/icons/list.png");

//INSTANCIA DOS CONTROLLERS
    String actionController;
    boolean listController = false;
    DAOPessoa daoPessoa = new DAOPessoa();
    DAOEndereco daoEndereco = new DAOEndereco();

//INSTANCIA DOS LABELS
    JLabel lblCpf = new JLabel("CPF");
    JLabel lblDataNascimento = new JLabel("DATA DE NASCIMENTO");
    JLabel lblNome = new JLabel("NOME");
    JLabel lblSexo = new JLabel("SEXO");
    JLabel lblEndereco = new JLabel("ENDEREÇO");

//INSTANCIA DOS TEXTFIELD
    JTextField txtCpf = new JTextField(12);
    JTextField txtNome = new JTextField(20);
    JTextField txtEndereco = new JTextField(15);
    DateTextField txtDataNascimento = new DateTextField();
    
//INSTANCIA DOS COMBOBOXES
    String[] sexos = {"Masculino", "Feminino"}; 
    JComboBox sexoBox;
    JComboBox enderecoBox;
    
//INSTANCIA DAS ENTIDADES
    Pessoa pessoa = new Pessoa();
//INSTANCIA DAS TABLE SCREENS
    PessoaTableScreen pessoaTableScreen;

    public PessoaScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - PESSOA");
        
        
        sexoBox = components.createComboBox(Arrays.asList(sexos)); 
        enderecoBox = components.createComboBox(daoEndereco.getFKList()); 

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
        panNorth.add(lblCpf);
        panNorth.add(txtCpf);

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
        panBody.setLayout(new GridLayout(5, 2));

        //Prenchimento por Linha
        panBody.add(panL1C1);
        panBody.add(panL1C2);
        panBody.add(panL2C1);
        panBody.add(panL2C2);
        panBody.add(panL3C1);
        panBody.add(panL3C2);
        panBody.add(panL4C1);
        panBody.add(panL4C2);
        panBody.add(panL5C1);
        panBody.add(panL5C2);

        //Prenchimento Linha 1
        panL1C1.add(lblNome);
        panL1C2.add(txtNome);

        //Prenchimento Linha 2
        panL2C1.add(lblSexo);
        panL2C2.add(sexoBox);

        //Prenchimento Linha 3
        
        panFK.add(components.createChildPanel(txtEndereco, new FlowLayout(FlowLayout.CENTER)));
        panFK.add(components.createChildPanel(enderecoBox, new FlowLayout(FlowLayout.CENTER)));
                
        panL3C1.add(lblEndereco);
        panL3C2.add(panFK);

        //Prenchimento Linha 4
        panL4C1.add(lblDataNascimento);
        panL4C2.add(txtDataNascimento);
        
        //Prenchimento Linha 5
        panL5C2.add(btnSave);
        
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtCpf.getText().trim().isEmpty()) {
                        pessoa = daoPessoa.get(txtCpf.getText());
                        if (pessoa != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtNome.setEditable(false);
                            sexoBox.setEnabled(false);
                            enderecoBox.setEnabled(false);
                            txtEndereco.setEditable(false);
                            txtDataNascimento.setEditable(false);

                            txtCpf.setText(String.valueOf(pessoa.getCpf()));
                            txtNome.setText(String.valueOf(pessoa.getNome()));
                            enderecoBox.setSelectedItem(pessoa.getEndereco().toFK());
                            sexoBox.setSelectedItem(pessoa.getSexoDescricao());                          
                            txtDataNascimento.setDate(pessoa.getDataNascimento());
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtNome.setEditable(true);
                            sexoBox.setEnabled(true);
                            enderecoBox.setEnabled(true);
                            txtEndereco.setEditable(true);
                            txtDataNascimento.setEditable(true);
                            txtNome.setText("");
                            sexoBox.setSelectedIndex(0);
                            enderecoBox.setSelectedIndex(0);
                            txtEndereco.setText("");
                            txtDataNascimento.setDate(new Date());
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
                btnSave.setVisible(true);
                txtDataNascimento.setEditable(true);
                txtCpf.setEditable(false);
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

                txtCpf.setEditable(false);
                txtNome.setEditable(true);
                sexoBox.setEnabled(true);
                enderecoBox.setEnabled(true);
                txtEndereco.setEditable(true);
                txtDataNascimento.setEditable(true);
                
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
                        pessoa = new Pessoa();
                    }
                    if (txtNome.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } else {
                        pessoa.setCpf(txtCpf.getText());
                        pessoa.setNome(txtNome.getText());
                        pessoa.setSexo(sexoBox.getSelectedItem().toString().substring(0,1));
                        pessoa.setSexoDescricao(sexoBox.getSelectedItem().toString());
                        pessoa.setDataNascimento(txtDataNascimento.getDate());
                        pessoa.setEndereco(selectEndereco());
                    }
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        daoPessoa.insert(pessoa);
                        System.out.println("PESSOA ADICIONADO!");
                    } else if (actionController.equalsIgnoreCase("UPDATE")) {
                        daoPessoa.update(pessoa);
                        System.out.println("LISTA ATUALIZADA!");
                    } else {

                        throw new Exception("Falha ao executar a ação na lista");

                    }

                    btnSave.setVisible(false);
                    btnRetrieve.setEnabled(true);
                    btnUpdate.setVisible(true);
                    btnUpdate.setEnabled(false);
                    btnCreate.setVisible(true);
                    btnCreate.setEnabled(false);
                    btnCancel.setVisible(false);

                    textFieldInitialConfiguration();
                   
                    txtCpf.requestFocus();
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
                    daoPessoa.delete(pessoa);
                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = "DELETE";
                    btnSave.setVisible(false);
                    btnRetrieve.setEnabled(true);
                    
                    textFieldInitialConfiguration();                    
                    txtCpf.requestFocus();
                    clearAllFields();
                  
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "PESSOA EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("PESSOA EXCLUÍDO");
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
                    pessoaTableScreen.dispose();
                    listController = false;
                }
                List<Pessoa> pessoas = daoPessoa.list();
                pessoaTableScreen = new PessoaTableScreen(pessoas);
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
                txtCpf.requestFocus();

            }
        });

        //Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    pessoaTableScreen.dispose();
                    listController = false;
                }
                dispose();
            }
        });
        
        txtEndereco.getDocument().addDocumentListener(new DocumentListener() {
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
        btnRetrieve.setEnabled(true);
        btnCancel.setVisible(false);
        btnList.setVisible(true);
        btnCreate.setEnabled(false);
        btnSave.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void textFieldInitialConfiguration() {
        txtCpf.setEditable(true);
        txtNome.setEditable(false);
        sexoBox.setEnabled(false);
        enderecoBox.setEnabled(false);
        txtEndereco.setEditable(false);
        txtDataNascimento.setEditable(false);
    }

    private void clearAllFields() {
        txtCpf.setText("");
        txtNome.setText("");
        sexoBox.setSelectedIndex(0);
        enderecoBox.setSelectedIndex(0);
        txtEndereco.setText("");
        txtDataNascimento.setDate(new Date());
    }
    
    private Endereco selectEndereco(){
        String fk = enderecoBox.getSelectedItem().toString(); 
        EnderecoPK pk = new EnderecoPK(fk.split(" - ")[0], Integer.valueOf(fk.split(" - ")[1])); 
        return daoEndereco.get(pk); 
    }
    
    private void changeSearch(){
        String search = txtEndereco.getText();

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> elements;

        if (search.trim().isEmpty()) {
            elements = daoEndereco.getFKList();

        } else {
            elements = daoEndereco.getSpecificFKList(daoEndereco.searchFast(search));
        }

        if (elements != null && !elements.isEmpty()) {
            for (String str : elements) {
                model.addElement(str);
            }
            enderecoBox.setModel(model);
        }
    }
}
