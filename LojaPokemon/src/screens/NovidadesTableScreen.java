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
import models.Novidades;

/**
 *
 * @author Matheus 
 */
public class NovidadesTableScreen extends JDialog{

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
String colunas[] = new String[]{"ID", "TITULO", "DESCRICAO"};
String linhas[][] = new String[0][colunas.length];

DefaultTableModel tableModel = new DefaultTableModel(linhas, colunas);
    JTable listTable = new JTable(tableModel);

    private JScrollPane scrollTable = new JScrollPane();

//DADOS
    List<Novidades> novidadess;


public NovidadesTableScreen(List<Novidades> novidadess) {
this.novidadess = novidadess;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("CRUD - NOVIDADES");


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
String colunas[] = new String[]{"ID", "TITULO", "DESCRICAO"};
Object dados[][] = new Object[this.novidadess.size()][colunas.length];

String aux[];

        for (int i = 0; i < this.novidadess.size(); i++) {
            aux = this.novidadess.get(i).toString().split(";");
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
