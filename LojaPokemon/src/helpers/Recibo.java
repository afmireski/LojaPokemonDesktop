/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import daos.DAOPedido;
import daos.DAOPedidoHasPokemon;
import daos.DAOPokemon;
import java.io.FileOutputStream;
import java.util.List;
import models.Pedido;
import models.PedidoHasPokemon;
import models.Pokemon;
import models.Preco;
import tools.CaixaDeFerramentas;

/**
 *
 * @author AFMireski
 */
public class Recibo {
    private static final String MSG_FALHA_GERAR_RELATORIO = "Houve uma falha ao gerar o relatório da venda do pedido %d";
    private static final String MSG_FALHA_ENVIAR_RELATORIO = "Houve uma falha ao "
            + "enviarmos o relatório da venda do pedido %d para o e-mail %s";
    
    public static void generateReciboByPedido(Integer pedidoID) throws Exception {
        final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(89, 34, 0, 64));
        final Font enphasisFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(89, 34, 0, 14));
        final Font defaultFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
        final Font tableColHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new CMYKColor(89, 34, 0, 14));

        final DAOPedidoHasPokemon daoPHP = new DAOPedidoHasPokemon();
        final DAOPedido daoPedido = new DAOPedido();
        final DAOPokemon daoPokemon = new DAOPokemon();

        final CaixaDeFerramentas cf = new CaixaDeFerramentas();

        try {
            ///BUSCA OS DADOS DO PEDIDO
            final List<PedidoHasPokemon> phps = daoPHP.findAllPHPByPedidoID(pedidoID);
            final Pedido pedido = daoPedido.get(pedidoID);

            Document document = new Document();

            PdfWriter writer = PdfWriter.getInstance(
                    document,
                    new FileOutputStream(String.format("/pdfs/ReciboPedido%s", pedidoID)));
            document.open();

            Paragraph title = new Paragraph("Loja Pokémon", titleFont);
            document.add(title);

            Paragraph br1 = new Paragraph(" ");
            document.add(br1);

            Paragraph nPedido = new Paragraph(String.format("Nº do pedido: %d", pedidoID), enphasisFont);
            document.add(nPedido);

            Paragraph cliente = new Paragraph(
                    String.format(
                            "Cliente: %s",
                            pedido.getUsuarioID().getPessoaCPF().getNome()),
                    defaultFont);
            document.add(cliente);

            Paragraph cartao = new Paragraph(
                    String.format(
                            "Cartao: %s",
                            pedido.getCartaoID().getNome()),
                    defaultFont);
            document.add(cartao);

            Paragraph dataCompra = new Paragraph(
                    String.format(
                            "Data da compra: %s",
                            cf.converteDeDateParaString(pedido.getDataPedido())),
                    defaultFont);
            document.add(dataCompra);

            Paragraph br2 = new Paragraph(" ");
            document.add(br2);

            ///CRIA TABELA PARA DETALHAR A COMPOSIÇÂO DO PEDIDO
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingBefore(10f);

            float[] colWidths = {2f, 1f};
            table.setWidths(colWidths);

            PdfPCell pokeCol = new PdfPCell(new Paragraph("Pokémon", tableColHeaderFont));
            pokeCol.setBackgroundColor(new CMYKColor(89, 34, 0, 64));
            pokeCol.setPaddingLeft(10);
            pokeCol.setHorizontalAlignment(Element.ALIGN_CENTER);
            pokeCol.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell valorCol = new PdfPCell(new Paragraph("Valor (R$)", tableColHeaderFont));
            valorCol.setBackgroundColor(new CMYKColor(89, 34, 0, 64));
            valorCol.setPaddingLeft(10);
            valorCol.setHorizontalAlignment(Element.ALIGN_CENTER);
            valorCol.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(pokeCol);
            table.addCell(valorCol);
            
            double valor_total = 0.0;
            for (PedidoHasPokemon php : phps) {
                Pokemon pokemon = daoPokemon.get(php.getPedidoHasPokemonPK().getPokemonID());
                Preco preco = pokemon.getPrecoList().get(pokemon.getPrecoList().size() - 1);
                valor_total += preco.getValor();
                
                PdfPCell pokeNome = new PdfPCell(new Paragraph(pokemon.getNome(), defaultFont));
                PdfPCell pokeValor = new PdfPCell(new Paragraph(
                        String.format("%.2f", preco.getValor()), defaultFont));
                
                table.addCell(pokeNome);
                table.addCell(pokeValor);
            }
            
            document.add(table);
            
            Paragraph br3 = new Paragraph(" ");
            document.add(br3);
            
            
            ///MOSTRA O VALOR TOTAL DO PEDIDO
            Paragraph total = new Paragraph(String.format("Total: R$ %.2f", valor_total), titleFont);
            document.add(total);
            
            document.close();
            writer.close();
            
        } catch (Exception e) {
            throw new Exception(String.format(MSG_FALHA_GERAR_RELATORIO, pedidoID));
        }
    }
}
