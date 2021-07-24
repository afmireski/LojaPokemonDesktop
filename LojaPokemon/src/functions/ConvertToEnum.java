/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import enums.FormatoArquivo;
import enums.FormatoLivro;
import enums.GeneroLivro;

/**
 *
 * @author afmireski
 */
public class ConvertToEnum {

    public FormatoLivro stringToFormatoLivro(String formato) {
        //Converte uma String com um padrão especifico em FormatoLivro
        switch (formato) {
            case "E_BOOK":
                return FormatoLivro.E_BOOK;
            case "DIGITAL":
                return FormatoLivro.DIGITAL;
            case "FISICO":
            default:
                return FormatoLivro.FISICO;
        }
    }

    public GeneroLivro stringToGeneroLivro(String genero) {
        //Converte uma String com um padrão especifico em GeneroLivro
        switch (genero) {
            case "FICCAO_CIENTIFICA":
                return GeneroLivro.FICCAO_CIENTIFICA;
            case "AVENTURA":
                return GeneroLivro.AVENTURA;
            case "DRAMA":
                return GeneroLivro.DRAMA;
            case "SUSPENSE":
                return GeneroLivro.SUSPENSE;
            case "TERROR":
                return GeneroLivro.TERROR;
            case "FANTASIA":
                return GeneroLivro.SUSPENSE;
            case "ROMANCE":
            default:
                return GeneroLivro.ROMANCE;

        }
    }

    public FormatoArquivo stringToFormatoArquivo(String formato) {
        switch (formato) {
            case "TXT":
                return FormatoArquivo.TXT;
            case "COMPACTO":
                return FormatoArquivo.COMPACTO;
            case "DOCUMENTO":
                return FormatoArquivo.DOCUMENTO;
            case "ARQUIVO_WEB":
                return FormatoArquivo.ARQUIVO_WEB;
            case "IMAGEM":
                return FormatoArquivo.IMAGEM;
            case "CODIGO":
                return FormatoArquivo.CODIGO;
            case "PDF":
                return FormatoArquivo.PDF;
            case "OUTROS":
            default:
                return FormatoArquivo.OUTROS;
        }
    }

}
