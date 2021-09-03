package screens;

import daos.DAOPokemon;
import daos.DAOTipopokemon;
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
import javax.swing.ImageIcon;
import java.io.File;
import javax.swing.JFileChooser;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import tools.CaixaDeFerramentas;
import tools.Tools;
import tools.CopiarArquivos;
import tools.DiretorioDaAplicacao;
import tools.ImagemAjustada;
import enums.DialogMessageType;
import enums.DialogConfirmType;
import helpers.webhelpers.PathManager;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Pokemon;
import models.Tipopokemon;

/**
 *
 * @author AFMireski
 */
public class PokemonScreen extends JDialog {

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
    JPanel panEast = new JPanel(new BorderLayout());
    JPanel panWest = new JPanel();
    JPanel panBody = new JPanel();

//EAST SUB PANELS
    JPanel panSubEast1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel panSubEast2 = new JPanel(new GridLayout(1, 1));
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

    JPanel panFK = new JPanel(new GridLayout(2, 1)); //Painel referente a FK

//INSTANCIA DOS BUTTONS
    JButton btnCreate = components.buttonWithIcon("Create", "/icons/create.png");
    JButton btnRetrieve = components.buttonWithIcon("Retrieve", "/icons/retrieve.png");
    JButton btnUpdate = components.buttonWithIcon("Update", "/icons/update.png");
    JButton btnDelete = components.buttonWithIcon("Delete", "/icons/delete.png");
    JButton btnAction = new JButton("Add to List");
    JButton btnCancel = components.buttonWithIcon("Cancel", "/icons/cancel.png");
    JButton btnList = components.buttonWithIcon("List", "/icons/list.png");
    JButton btnSelectImage = new JButton("Select Image");
    JButton btnRemoveImage = new JButton("Remove Image");

//INSTANCIA DOS CONTROLLERS
    CrudAction actionController;
    boolean listController = false;
    boolean imageController = false;
    DAOPokemon daoPokemon = new DAOPokemon();
    DAOTipopokemon daoTipopokemon = new DAOTipopokemon();

//INSTANCIA DOS LABELS
    JLabel lblId = new JLabel("ID");
    JLabel lblNome = new JLabel("NOME");
    JLabel lblEstoque = new JLabel("ESTOQUE");
    JLabel lblTipoPokemonID = new JLabel("TIPO POKÉMON");
    JLabel lblImage = new JLabel();

//INSTANCIA DOS TEXTFIELD
    JTextField txtId = new JTextField(5);
    JTextField txtNome = new JTextField(15);
    JTextField txtEstoque = new JTextField(6);
    JTextField txtSearchFK = new JTextField(10);

//INSTANCIA DAS COMBO BOX
    JComboBox fkBox;

//INSTANCIA DAS ENTIDADES
    Pokemon pokemon = new Pokemon();

//INSTANCIA DAS TABLE SCREENS
    PokemonTableScreen pokemonTableScreen;

//PATHS
    String currentPath = dda.getDiretorioDaAplicacao();

//IMAGE CONFIGURATIONS
    int defaultHeight = 256;
    int defaultWidth = 256;
    String defaultImagePath = currentPath + "/src/images/default.png";
    String currentImage;

    public PokemonScreen() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - POKEMON");

        //IMAGE INITIAl CONFIGURATIONS
        setImage(defaultImagePath);
        currentImage = defaultImagePath;

        //FK CONFIGURATIONS
        fkBox = components.createComboBox(daoTipopokemon.getFKList());

        //BUTTONS INITIAL CONFIGURATIONS
        buttonsInitialConfiguration();

        //BUTTONS INITIAL CONFIGURATIONS
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

        //PAN EAST CONFIGURATIONS
        panEast.setBackground(Color.BLACK);
        panEast.add(panSubEast1, BorderLayout.NORTH);
        panEast.add(panSubEast2, BorderLayout.CENTER);

        panSubEast1.add(btnSelectImage);
        panSubEast1.add(btnRemoveImage);

        panSubEast2.add(lblImage);
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
        panL2C1.add(lblEstoque);
        panL2C2.add(txtEstoque);

        //Prenchimento Linha 3
        panFK.add(components.createChildPanel(txtSearchFK, new FlowLayout(FlowLayout.CENTER)));
        panFK.add(components.createChildPanel(fkBox, new FlowLayout(FlowLayout.CENTER)));

        panL3C1.add(lblTipoPokemonID);
        panL3C2.add(panFK);

        //Prenchimento Linha 5
        panL5C2.add(btnAction);
        //BTN RETRIEVE ACTION LISTENER
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtId.getText().trim().isEmpty()) {
                        pokemon = daoPokemon.get(Integer.valueOf(txtId.getText()));
                        if (pokemon != null) {
                            btnCreate.setEnabled(false);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(true);
                            btnUpdate.setVisible(true);
                            btnDelete.setEnabled(true);

                            txtNome.setEditable(false);
                            txtEstoque.setEditable(false);
                            txtSearchFK.setEditable(false);
                            fkBox.setEnabled(false);

                            txtId.setText(String.valueOf(pokemon.getId()));
                            txtNome.setText(String.valueOf(pokemon.getNome()));
                            txtEstoque.setText(String.valueOf(pokemon.getEstoque()));
                            fkBox.setSelectedItem(pokemon.getTipoPokemonID().toFK());
                            String image;
                            if (!pokemon.getImagem().trim().isEmpty()) {
                                image = currentPath + "/src" + pokemon.getImagem();
                                ImageIcon icon = imagemAjustada.getImagemAjustada(
                                        image,
                                        defaultHeight,
                                        defaultWidth);
                                lblImage.setIcon(icon);
                            } else {
                                image = defaultImagePath;
                                ImageIcon icon = imagemAjustada.getImagemAjustada(
                                        image,
                                        defaultHeight,
                                        defaultWidth);
                                lblImage.setIcon(icon);
                            }
                            currentImage = image;
                        } else {
                            btnCreate.setEnabled(true);
                            btnCreate.setVisible(true);
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);

                            txtNome.setEditable(true);
                            txtEstoque.setEditable(true);
                            txtSearchFK.setEditable(true);
                            fkBox.setEnabled(true);
                            txtNome.setText("");
                            txtEstoque.setText("");
                            txtSearchFK.setText("");
                            fkBox.setSelectedIndex(0);
                            ImageIcon icon = imagemAjustada.getImagemAjustada(
                                    defaultImagePath,
                                    defaultHeight,
                                    defaultWidth);
                            lblImage.setIcon(icon);
                            currentImage = defaultImagePath;
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
                btnSelectImage.setVisible(true);
                btnRemoveImage.setVisible(true);

                txtId.setEditable(false);
                txtNome.requestFocus();

                actionController = CrudAction.CREATE;
                imageController = false;
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
                btnSelectImage.setVisible(true);
                btnRemoveImage.setVisible(true);

                txtId.setEditable(false);
                txtNome.setEditable(true);
                txtEstoque.setEditable(true);
                txtSearchFK.setEditable(true);
                fkBox.setEnabled(true);
                txtNome.requestFocus();

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
                        pokemon = new Pokemon();
                    }
                    if (txtNome.getText().trim().isEmpty() || txtEstoque.getText().trim().isEmpty()) {
                        throw new Exception("Verifique se os seus campos estão preenchidos!");
                    } else if (currentImage.trim().isEmpty() || currentImage.contains("/src/images/default.png")) {
                        throw new Exception("Todo Pokémon deve ter uma imagem!");
                    } else {
                        pokemon.setId(Integer.valueOf(txtId.getText()));
                        pokemon.setNome(txtNome.getText());
                        pokemon.setEstoque(Integer.valueOf(txtEstoque.getText()));
                        pokemon.setTipoPokemonID(selectTipoPokemon());
                        pokemon.setDataCadastro(new Date());
                        if (actionController.equals(CrudAction.CREATE)) {
                            pokemon.setImagem("/images/" + daoPokemon.autoincrement() + ".png");
                        } else {
                            pokemon.setImagem("/images/" + txtId.getText() + ".png");
                        }
                        if (imageController) {
                            if (actionController.equals(CrudAction.UPDATE)) {
                                copiaFoto(txtId.getText());
                            } else {
                                copiaFoto(daoPokemon.autoincrement().toString());
                            }
                        }
                    }
                    if (actionController.equals(CrudAction.CREATE)) {
                        daoPokemon.insert(pokemon);
                        System.out.println("POKEMON ADICIONADO!");
                    } else if (actionController.equals(CrudAction.UPDATE)) {
                        daoPokemon.update(pokemon);
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
                    btnSelectImage.setVisible(false);
                    btnRemoveImage.setVisible(false);
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

                    File imageDesk = new File(currentImage.trim());
                    File imageWeb = new File(PathManager.WEB_PATH + "/src/java" + pokemon.getImagem());

                    if (imageDesk.exists()) {
                        if (!currentImage.equals(defaultImagePath)) {
                            imageDesk.delete();
                            if (imageWeb.exists()) {
                                imageWeb.delete();
                            }
                        }
                        setImage(defaultImagePath);
                    }

                    daoPokemon.delete(pokemon);

                    btnRetrieve.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnCreate.setEnabled(false);
                    btnSelectImage.setVisible(false);
                    btnRemoveImage.setVisible(false);

                    actionController = CrudAction.DELETE;
                    btnAction.setVisible(false);
                    btnRetrieve.setEnabled(true);

                    //BUTTONS INITIAL CONFIGURATIONS
                    textFieldInitialConfiguration();

                    txtId.setEditable(true);
                    txtId.requestFocus();

                    clearAllFields();

                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "POKEMON EXCLUÍDO COM SUCESSO",
                            "DELETE",
                            container);
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
                    pokemonTableScreen.dispose();
                    listController = false;
                }
                List<Pokemon> pokemons = daoPokemon.list();
                pokemonTableScreen = new PokemonTableScreen(pokemons);
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

                //BUTTONS INITIAL CONFIGURATIONS
                textFieldInitialConfiguration();

                clearAllFields();

                txtId.requestFocus();
            }
        });

        btnSelectImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = components.createFileChooser(
                        null,
                        JFileChooser.FILES_ONLY);
                if (fc.showOpenDialog(container) == JFileChooser.APPROVE_OPTION) {
                    File img = fc.getSelectedFile();
                    currentImage = img.getAbsolutePath();

                    try {
                        setImage(currentImage);
                        imageController = true;
                    } catch (Exception excep) {
                        messageDialog = new BuildMessageDialog(
                                DialogMessageType.ERROR,
                                "Falha a carregar imagem!",
                                "FALHA AO CARREGAR",
                                container);
                    }
                }
            }
        });

        btnRemoveImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDialog = new BuildConfirmDialog(
                        DialogConfirmType.YES_NO,
                        "Deseja realmente remover a imagem?",
                        "CONFIRMAR REMOÇÃO");

                int response = confirmDialog.getResponse();
                if (response == JOptionPane.YES_OPTION) {
                    File img = new File(currentImage);
                    if (img.exists()) {
                        if (!currentImage.equals(defaultImagePath)) {
                            img.delete();
                        }
                        currentImage = defaultImagePath;
                        setImage(defaultImagePath);
                        imageController = false;
                    }

                }
            }
        });

        //Close Window Action
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listController) {
                    //SE TABLE SCREEN ESTIVER ATIVA, A FECHA ANTES DE ABRIR NOVAMENTE.
                    pokemonTableScreen.dispose();
                    listController = false;
                }
                dispose();
            }
        });

        txtSearchFK.getDocument().addDocumentListener(new DocumentListener() {
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
        btnAction.setVisible(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSelectImage.setVisible(false);
        btnRemoveImage.setVisible(false);
    }

    private void textFieldInitialConfiguration() {
        //TEXTFIELD INITIAL CONFIGURATIONS
        txtId.setEditable(true);
        txtNome.setEditable(false);
        txtEstoque.setEditable(false);
        txtSearchFK.setEditable(false);
        fkBox.setEnabled(false);
    }

    private void clearAllFields() {
        txtId.setText("");
        txtNome.setText("");
        txtEstoque.setText("");
        txtSearchFK.setText("");
        fkBox.setSelectedIndex(0);

        currentImage = defaultImagePath;
        setImage(defaultImagePath);
    }

    private void setImage(String imagePath) {
        lblImage.setIcon(imagemAjustada.getImagemAjustada(
                imagePath, defaultHeight, defaultWidth));
    }

    private String getImage(String pk) {
        return currentPath + "/src/images/" + pk + ".png";
    }

    private void copiaFoto(String pk) {
        final String destinoDesktop = getImage(pk);
        final String destinoWeb = PathManager.WEB_PATH + "/src/java/images/" + pk + ".png";

        System.out.println(currentImage);
        System.out.println(destinoDesktop);
        System.out.println(destinoWeb);

        copiarArquivos.copiar(currentImage, destinoDesktop);
        copiarArquivos.copiar(currentImage, destinoWeb);
    }

    private Tipopokemon selectTipoPokemon() {
        String fk = fkBox.getSelectedItem().toString();
        Integer key = Integer.valueOf(fk.split(" - ")[0]);
        return daoTipopokemon.searchByID(key).get(0);
    }

    private void changeSearch() {
        String search = txtSearchFK.getText();

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> elements;

        if (search.trim().isEmpty()) {
            elements = daoTipopokemon.getFKList();

        } else {
            elements = daoTipopokemon.getEspecificFKList(daoTipopokemon.searchFast(search));
        }

        if (elements != null && !elements.isEmpty()) {
            for (String str : elements) {
                model.addElement(str);
            }
            fkBox.setModel(model);
        }
    }

}
