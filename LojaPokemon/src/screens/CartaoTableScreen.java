package screens;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import models.Cartao;

/**
 *
 * @author Matheus 
 */
public class CartaoTableScreen extends JDialog{

//INSTANCIA DOS CONTAINERS
    Container container;

//INSTANCIA DOS PANELS
    //PANELS BASE
    JPanel panNorth = new JPanel();
    JPanel panSouth = new JPanel();
    JPanel panEast = new JPanel();
    JPanel panWest = new JPanel();
    JPanel panBody = new JPanel();

//TABELA
String colunas[] = new String[]{"ID", "SALDO", "NOME", "DATACADASTRO", "USUARIOID"};
String linhas[][] = new String[0][colunas.length];

DefaultTableModel tableModel = new DefaultTableModel(linhas, colunas);
    JTable listTable = new JTable(tableModel);

    private JScrollPane scrollTable = new JScrollPane();

//DADOS
    List<Cartao> cartaos;


public CartaoTableScreen(List<Cartao> cartaos) {
this.cartaos = cartaos;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - CARTAO");


	//CONTAINER CONFIGURATIONS
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panNorth, BorderLayout.NORTH);
        container.add(panSouth, BorderLayout.SOUTH);
        container.add(panEast, BorderLayout.EAST);
        container.add(panWest, BorderLayout.WEST);
        container.add(panBody, BorderLayout.CENTER);


panBody.setLayout(new GridLayout(1, 1));
//TABELA
String colunas[] = new String[]{"ID", "SALDO", "NOME", "DATACADASTRO", "USUARIOID"};
Object dados[][] = new Object[this.cartaos.size()][colunas.length];

String aux[];

        for (int i = 0; i < this.cartaos.size(); i++) {
            aux = this.cartaos.get(i).toString().split(";");
            for (int j = 0; j < colunas.length; j++) {
                dados[i][j] = aux[j];
            }
        }
        panBody.add(scrollTable);
        scrollTable.setViewportView(listTable);
        tableModel.setDataVector(dados, colunas);

	setModal(true);	pack();	setVisible(true);

}
}
