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
public class ConvertFromEnum {

    public String stringFromFormatoLivro(FormatoLivro formato) {
        //Converte um FormatoLivro com um padrão especifico em String
        switch (formato) {
            case E_BOOK:
                return "E_BOOK";
            case DIGITAL:
                return "DIGITAL";
            case FISICO:
            default:
                return "FISICO";

        }
    }

    public String stringFromGeneroLivro(GeneroLivro genero) {
        //Converte um GeneroLivro para String;
        switch (genero) {
            case FICCAO_CIENTIFICA:
                return "FICCAO_CIENTIFICA";
            case AVENTURA:
                return "AVENTURA";
            case DRAMA:
                return "DRAMA";
            case SUSPENSE:
                return "SUSPENSE";
            case TERROR:
                return "TERROR";
            case FANTASIA:
                return "FANTASIA";
            case ROMANCE:
            default:
                return "ROMANCE";

        }
    }

    public String stringFromFormatoArquivo(FormatoArquivo formatoArquivo) {
        //Converte um FormatoArquivo para String;
        switch (formatoArquivo) {
            case TXT:
                return "TXT";
            case COMPACTO:
                return "COMPACTO";
            case DOCUMENTO:
                return "DOCUMENTO";
            case ARQUIVO_WEB:
                return "ARQUIVO_WEB";
            case IMAGEM:
                return "IMAGEM";
            case CODIGO:
                return "CODIGO";
            case PDF:
                return "PDF";
            case OUTROS:
            default:
                return "OUTROS";
        }

    }
}
