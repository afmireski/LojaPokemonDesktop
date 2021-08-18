package screens;

import daos.DAOPedido;
import daos.DAOPedidoHasPokemon;
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
import javax.swing.JComboBox;
import models.PedidoHasPokemon;
import models.PedidoHasPokemonPK;

/**
 *
 * @author AFMireski
 */
public class PedidoHasPokemonScreen extends JDialog {

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

    //NORTH PANELS        
    JPanel panPK = new JPanel(new GridLayout(1, 2));
    JPanel panPoke = new JPanel();
    JPanel panSubPoke = new JPanel(new GridLayout(2, 1));

    JPanel panPedido = new JPanel();
    JPanel panSubPedido = new JPanel(new GridLayout(2, 1));

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
    DAOPedidoHasPokemon daoPedidoHasPokemon = new DAOPedidoHasPokemon();
    DAOPokemon daoPokemon = new DAOPokemon();
    DAOPedido daoPedido = new DAOPedido();
    DAOPreco daoPreco = new DAOPreco();

//INSTANCIA DOS LABELS
    JLabel lblPedido = new JLabel("PEDIDO");
    JLabel lblPokemon = new JLabel("POKEMON");
    JLabel lblQuantidade = new JLabel("QUANTIDADE");
    JLabel lblValorUnitario = new JLabel("VALOR UNITÁRIO");

//INSTANCIA DOS TEXTFIELD
    JTextField txtPedido = new JTextField(15);
    JTextField txtPokemon = new JTextField(15);
    JTextField txtQuantidade = new JTextField(10);
    JTextField txtValorUnitario = new JTextField(10);

//INSTANCIA DAS ENTIDADES
    PedidoHasPokemon pedidoHasPokemon = new PedidoHasPokemon();
    PedidoHasPokemonPK pk = new PedidoHasPokemonPK();

//INSTANCIA DOS COMBOBOX
    JComboBox pokemonBox;
    JComboBox pedidoBox;

//INSTANCIA DAS TABLE SCREENS
    PedidoHasPokemonTableScreen pedidoHasPokemonTableScreen;

    public PedidoHasPokemonScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - PEDIDOHASPOKEMON");

        pokemonBox = components.createComboBox(daoPokemon.getFKList());
        pedidoBox = components.createComboBox(daoPedido.getFKList());

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
        
        
        panPedido.add(lblPedido);
        panPedido.add(panSubPedido);
        panSubPedido.add(txtPedido);
        panSubPedido.add(pedidoBox);
        
        panPoke.add(lblPokemon);
        panPoke.add(panSubPoke);        
        panSubPoke.add(txtPokemon);
        panSubPoke.add(pokemonBox);        
        
        panPK.add(panPedido);
        panPK.add(panPoke);
        
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
        panBody.add(panL4C1);
        panBody.add(panL4C2);

        //Prenchimento Linha 1

        //Prenchimento Linha 2
        panL2C1.add(lblQuantidade);
        panL2C2.add(txtQuantidade);

        //Prenchimento Linha 4
        panL4C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getPK();
                    pedidoHasPokemon = daoPedidoHasPokemon.get(pk);
                    if (pedidoHasPokemon != null) {
                        btnCreate.setEnabled(false);
                        btnCreate.setVisible(true);
                        btnUpdate.setEnabled(true);
                        btnUpdate.setVisible(true);
                        btnDelete.setEnabled(true);

                        txtPokemon.setEditable(false);
                        txtQuantidade.setEditable(false);
                        txtValorUnitario.setEditable(false);

                        txtQuantidade.setText(String.valueOf(pedidoHasPokemon.getQuantidade()));
//                        txtValorUnitario.setText(String.valueOf(pedidoHasPokemon.getValorUnitario()));
                    } else {
                        btnCreate.setEnabled(true);
                        btnCreate.setVisible(true);
                        btnUpdate.setEnabled(false);
                        btnDelete.setEnabled(false);

                        txtPokemon.setEditable(true);
                        txtQuantidade.setEditable(true);
                        txtValorUnitario.setEditable(true);
                        txtPokemon.setText("");
                        txtQuantidade.setText("");
                        txtValorUnitario.setText("");
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

                txtPedido.setEditable(false);
                pedidoBox.setEnabled(false);
                
                txtPokemon.setEditable(false);
                pokemonBox.setEnabled(false);
                
                txtQuantidade.requestFocus();

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

                txtPedido.setEditable(false);
                pedidoBox.setEnabled(false);
                
                txtPokemon.setEditable(false);
                pokemonBox.setEnabled(false);
                txtQuantidade.setEditable(true);
                
//                txtValorUnitario.setEditable(true);
                txtQuantidade.requestFocus();

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
                        pedidoHasPokemon = new PedidoHasPokemon();
                    }
                    if (txtQuantidade.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } if (!txtQuantidade.getText().matches("[0-9]*")) {
                        throw new Exception("Digite somente números!");                        
                    } if (daoPokemon.get(pk.getPokemonID()).getEstoque() < Integer.valueOf(txtQuantidade.getText())) {                        
                        throw new Exception("A quantidade não pode ser superior ao estoque");                        
                    } else {
                        pedidoHasPokemon.setPedidoHasPokemonPK(pk);
                        pedidoHasPokemon.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
                        pedidoHasPokemon.setValorUnitario(daoPreco.getPrecoVigenteByPokemon(pk.getPokemonID()).getValor());
                    }
                    if (actionController.equals(CrudAction.CREATE)) {
                        daoPedidoHasPokemon.insert(pedidoHasPokemon);
                    } else if (actionController.equals(CrudAction.UPDATE)) {
                        daoPedidoHasPokemon.update(pedidoHasPokemon);
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

                    txtPedido.setEditable(true);
                    txtPedido.requestFocus();

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
                    daoPedidoHasPokemon.delete(pedidoHasPokemon);

                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);

                    actionController = CrudAction.DELETE;
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    txtPedido.setEditable(true);
                    textFieldInitialConfiguration();
                    txtPedido.requestFocus();

                    clearAllFields();

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "PEDIDOHASPOKEMON EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
                    System.out.println("PEDIDOHASPOKEMON EXCLUÍDO");
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
                    pedidoHasPokemonTableScreen.dispose();
                    listController = false;
                }
                List<PedidoHasPokemon> pedidoHasPokemons = daoPedidoHasPokemon.list();
                pedidoHasPokemonTableScreen = new PedidoHasPokemonTableScreen(pedidoHasPokemons);
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

                txtPedido.requestFocus();

            }
        });

//Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    pedidoHasPokemonTableScreen.dispose();
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
        txtPedido.setEditable(true);
        txtPokemon.setEditable(true);
        txtQuantidade.setEditable(false);
        pokemonBox.setEnabled(true);
        pedidoBox.setEnabled(true);
    }

    private void clearAllFields() {
        txtPedido.setText("");
        txtPokemon.setText("");
        txtQuantidade.setText("");
        pokemonBox.setSelectedIndex(0);
        pedidoBox.setSelectedIndex(0);
        
    }

    private void getPokemonID() {
        String fk = pokemonBox.getSelectedItem().toString();
        int id = Integer.valueOf(fk.split(" - ")[0]);

        pk.setPokemonID(id);
    }

    private void getPedidoID() {
        String fk = pedidoBox.getSelectedItem().toString();
        int id = Integer.valueOf(fk.split(" - ")[0]);

        pk.setPedidoID(id);
    }

    private void getPK() {
        getPedidoID();
        getPokemonID();
    }

}
