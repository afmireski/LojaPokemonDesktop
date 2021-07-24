package screens;

import functions.ConvertToEnum;
import functions.ConvertFromEnum;
import java.util.ArrayList;
import java.util.List;
import tools.CaixaDeFerramentas;
import tools.ManipulaArquivo;
import functions.VerifyPK;
import helpers.BuildConfirmDialog;
import helpers.BuildMessageDialog;
import helpers.ErrorTools;
import helpers.GenericComponents;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
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
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import tools.CaixaDeFerramentas;
import tools.Tools;
import enums.DialogMessageType;
import enums.DialogConfirmType;
import controllers.EnderecoController;
import models.Endereco;

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
    JPanel panL6C1 = new JPanel(); //Painel referente a posição da grade: Linha 6 - Coluna 1
    JPanel panL6C2 = new JPanel(); //Painel referente a posição da grade: Linha 6 - Coluna 2

//INSTANCIA DOS BUTTONS
    JButton btnCreate = new JButton("Create");
    JButton btnRetrieve = new JButton("Retrieve");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");
    JButton btnAction = new JButton("Add to List");
    JButton btnRemove = new JButton("Remove");
    JButton btnCancel = new JButton("Cancel");
    JButton btnList = new JButton("List");

//INSTANCIA DOS CONTROLLERS
    String actionController;
    boolean listController = false;
    EnderecoController enderecoController = new EnderecoController();

//INSTANCIA DOS LABELS
    JLabel lblCep = new JLabel("CEP");
    JLabel lblNCasa = new JLabel("NCASA");
    JLabel lblNome = new JLabel("NOME");
    JLabel lblCidade = new JLabel("CIDADE");
    JLabel lblUf = new JLabel("UF");
    JLabel lblUfDescricao = new JLabel("UFDESCRICAO");

//INSTANCIA DOS TEXTFIELD
    JTextField txtCep = new JTextField(9);
    JTextField txtNCasa = new JTextField(5);
    JTextField txtNome = new JTextField(15);
    JTextField txtCidade = new JTextField(15);
    JTextField txtUf = new JTextField(5);
    JTextField txtUfDescricao = new JTextField(10);

//INSTANCIA DAS ENTIDADES
    Endereco endereco = new Endereco();
//INSTANCIA DAS TABLE SCREENS
    EnderecoTableScreen enderecoTableScreen;

    public EnderecoScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - ENDERECO");

        //LOAD DATA
        final String path = "Endereco.csv";
        enderecoController.loadData(path);

        //BUTTONS INITIAL CONFIGURATIONS
        btnRetrieve.setEnabled(true);
        btnCancel.setVisible(false);
        btnList.setVisible(true);
        btnCreate.setEnabled(false);
        btnAction.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        //TEXTFIELD INITIAL CONFIGURATIONS
        txtCep.setEnabled(true);
        txtNCasa.setEnabled(false);
        txtNome.setEnabled(false);
        txtCidade.setEnabled(false);
        txtUf.setEnabled(false);
        txtUfDescricao.setEnabled(false);

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
        panNorth.add(lblCep);
        panNorth.add(txtCep);

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
        panBody.setLayout(new GridLayout(6, 2));

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
        panBody.add(panL6C1);
        panBody.add(panL6C2);

        //Prenchimento Linha 1
        panL1C1.add(lblNCasa);
        panL1C2.add(txtNCasa);

        //Prenchimento Linha 2
        panL2C1.add(lblNome);
        panL2C2.add(txtNome);

        //Prenchimento Linha 3
        panL3C1.add(lblCidade);
        panL3C2.add(txtCidade);

        //Prenchimento Linha 4
        panL4C1.add(lblUf);
        panL4C2.add(txtUf);

        //Prenchimento Linha 5
        panL5C1.add(lblUfDescricao);
        panL5C2.add(txtUfDescricao);

        //Prenchimento Linha 6
        panL6C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtCep.getText().trim().isEmpty()) {
                        endereco = enderecoController.retrieve(txtCep.getText());
                        if (endereco != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtNCasa.setEnabled(false);
                            txtNome.setEnabled(false);
                            txtCidade.setEnabled(false);
                            txtUf.setEnabled(false);
                            txtUfDescricao.setEnabled(false);

                            txtCep.setText(String.valueOf(endereco.getCep()));
                            txtNCasa.setText(String.valueOf(endereco.getNCasa()));
                            txtNome.setText(String.valueOf(endereco.getNome()));
                            txtCidade.setText(String.valueOf(endereco.getCidade()));
                            txtUf.setText(String.valueOf(endereco.getUf()));
                            txtUfDescricao.setText(String.valueOf(endereco.getUfDescricao()));
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtNCasa.setEnabled(true);
                            txtNome.setEnabled(true);
                            txtCidade.setEnabled(true);
                            txtUf.setEnabled(true);
                            txtUfDescricao.setEnabled(true);
                            txtNCasa.setText("");
                            txtNome.setText("");
                            txtCidade.setText("");
                            txtUf.setText("");
                            txtUfDescricao.setText("");
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

                txtCep.setEnabled(false);
                txtNCasa.requestFocus();

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

                txtCep.setEnabled(false);
                txtNCasa.setEnabled(true);
                txtNome.setEnabled(true);
                txtCidade.setEnabled(true);
                txtUf.setEnabled(true);
                txtUfDescricao.setEnabled(true);
                txtNCasa.requestFocus();

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
                        endereco = new Endereco();
                    }
                    Endereco enderecoAntigo = endereco;
                    if (txtNCasa.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty() || txtCidade.getText().trim().isEmpty() || txtUf.getText().trim().isEmpty() || txtUfDescricao.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } else {
                        endereco.setCep(txtCep.getText());
                        endereco.setNCasa(Integer.valueOf(txtNCasa.getText()));
                        endereco.setNome(txtNome.getText());
                        endereco.setCidade(txtCidade.getText());
                        endereco.setUf(Integer.valueOf(txtUf.getText()));
                        endereco.setUfDescricao(Integer.valueOf(txtUfDescricao.getText()));
                    }
                    if (actionController.equalsIgnoreCase("CREATE")) {
                        System.out.println(endereco.toString());
                        enderecoController.create(endereco);
                        System.out.println("ENDERECO ADICIONADO!");
                    } else if (actionController.equalsIgnoreCase("UPDATE")) {
                        System.out.println("ENDERECO => " + endereco.toString());
                        System.out.println("ENDERECO ANTIGO => " + enderecoAntigo.toString());
                        enderecoController.update(enderecoAntigo, endereco);
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

                    txtCep.setEnabled(true);
                    txtCep.setText("");
                    txtCep.requestFocus();

                    txtNCasa.setEnabled(false);
                    txtNome.setEnabled(false);
                    txtCidade.setEnabled(false);
                    txtUf.setEnabled(false);
                    txtUfDescricao.setEnabled(false);
                    txtNCasa.setText("");
                    txtNome.setText("");
                    txtCidade.setText("");
                    txtUf.setText("");
                    txtUfDescricao.setText("");

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
                    btnOpen.setEnabled(false);

                    actionController = "DELETE";
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    txtCep.setEnabled(true);
                    txtCep.setText("");
                    txtCep.requestFocus();

                    txtNCasa.setEnabled(false);
                    txtNome.setEnabled(false);
                    txtCidade.setEnabled(false);
                    txtUf.setEnabled(false);
                    txtUfDescricao.setEnabled(false);
                    txtNCasa.setText("");
                    txtNome.setText("");
                    txtCidade.setText("");
                    txtUf.setText("");
                    txtUfDescricao.setText("");
                    enderecoController.delete(endereco);

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "ENDERECO EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("ENDERECO EXCLUÍDO");
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
                    enderecoTableScreen.dispose();
                    listController = false;
                }
                List<Endereco> enderecos = enderecoController.listar();
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
                btnRetrieve.setEnabled(true);
                btnCancel.setVisible(false);
                btnList.setVisible(true);
                btnCreate.setEnabled(false);
                btnAction.setVisible(false);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                btnOpen.setEnabled(false);

                //TEXTFIELD INITIAL CONFIGURATIONS
                txtCep.setEnabled(true);
                txtNCasa.setEnabled(false);
                txtNome.setEnabled(false);
                txtCidade.setEnabled(false);
                txtUf.setEnabled(false);
                txtUfDescricao.setEnabled(false);

                txtCep.setText("");
                txtCep.requestFocus();

                txtNCasa.setText("");
                txtNome.setText("");
                txtCidade.setText("");
                txtUf.setText("");
                txtUfDescricao.setText("");

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
                enderecoController.saveData(path);
                dispose();
            }
        });

        pack();
        setModal(true);
        setVisible(true);
    }
}
