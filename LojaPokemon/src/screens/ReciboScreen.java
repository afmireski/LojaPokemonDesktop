/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import enums.DialogMessageType;
import functions.ConvertFromEnum;
import functions.ConvertToEnum;
import functions.VerifyPK;
import helpers.BuildConfirmDialog;
import helpers.BuildMessageDialog;
import helpers.ErrorTools;
import helpers.GenericComponents;
import helpers.Recibo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import tools.CaixaDeFerramentas;
import tools.Tools;

/**
 *
 * @author AFMireski
 */
public class ReciboScreen extends JDialog {

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

    //INSTANCIA DOS BUTTONS
    JButton btnGerarRecibo = components.buttonWithIcon("Gerar Recibo", "/icons/receipt.png");

    //INSTANCIA DOS LABELS
    JLabel lblRemetente = new JLabel("E-MAIL REMETENTE");
    JLabel lblSenhaRemetente = new JLabel("SENHA E-MAIL");

    //INSTANCIA DOS TEXTFIELD
    JTextField txtRemetente = new JTextField(20);
    JPasswordField txtSenha = new JPasswordField(10);

    public ReciboScreen(Integer pedidoID) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("GERAR RECIBO");

        //CONTAINER CONFIGURATIONS
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panNorth, BorderLayout.NORTH);
        container.add(panSouth, BorderLayout.SOUTH);
        container.add(panEast, BorderLayout.EAST);
        container.add(panWest, BorderLayout.WEST);
        container.add(panBody, BorderLayout.CENTER);

        panNorth.add(new JLabel("LOJA POKÉMON"));
        panSouth.add(btnGerarRecibo);

        //PAN BODY CONFIGURATIONS
        panBody.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        panBody.setLayout(new GridLayout(2, 2));

        //Prenchimento por Linha
        panBody.add(panL1C1);
        panBody.add(panL1C2);
        panBody.add(panL2C1);
        panBody.add(panL2C2);

        //Prenchimento Linha 1        
        panL1C1.add(lblRemetente);
        panL1C2.add(txtRemetente);

        //Prenchimento Linha 2        
        panL2C1.add(lblSenhaRemetente);
        panL2C2.add(txtSenha);

        btnGerarRecibo.addActionListener((ae) -> {
            try {
                if (txtRemetente.getText().isEmpty() || txtSenha.getPassword().length == 0) {
                    throw new Exception("Preencha os campos corretamente!!!");
                } else {
                    Recibo.emitirRecibo(pedidoID, txtRemetente.getText(), txtSenha.getPassword());
                    
                    dispose();
                    
                    messageDialog = new BuildMessageDialog(
                            DialogMessageType.SUCESS,
                            "Recibo gerado com sucesso",
                            "RECEIPT Generated",
                            container);
                }
            } catch (Exception e) {
                errorTools.showExceptionStackTrace(e);
                messageDialog = new BuildMessageDialog(
                        DialogMessageType.ERROR,
                        e.getMessage(),
                        "RECEIPT Error",
                        container);
            }

        });

        pack();
        setModal(true);
        setVisible(true);
    }

}
