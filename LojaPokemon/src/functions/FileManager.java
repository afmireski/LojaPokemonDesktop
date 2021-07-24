/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import tools.CopiarArquivos;
import tools.ManipulaArquivo;

/**
 *
 * @author afmireski
 */
public class FileManager {

    public void createDirectorys(String path, List<String> pastas) {
        ///MÉTODO PARA CRIAR PASTAS
        for (String dir : pastas) {
            String packagePath = path + dir;
            File pack = new File(packagePath);
            if (!pack.exists()) {
                new File(packagePath).mkdir(); //CRIA A PASTA
            }
        }
    }

    public void createSingleDirectory(String path, String pasta) {
        ///MÉTODO PARA CRIAR UMA ÚNICA PASTA
        String packagePath = path + pasta;
        File pack = new File(packagePath);
        if (!pack.exists()) {
            new File(packagePath).mkdir(); //CRIA A PASTA
        }
    }

    public void copyFiles(String destinyPath, String filesPath) {
        ///MÉTODO PARA COPIAR ARQUIVOS
        File dir = new File(filesPath);

        if (dir.exists()) {
            File[] dirFiles = dir.listFiles(); //LISTA TODOS OS ARQUIVOS
            CopiarArquivos copiarArquivos = new CopiarArquivos();

            for (File file : dirFiles) {
                String finalPath = destinyPath + "\\" + filesPath + "\\" + file.getName();
                copiarArquivos.copiar(file.getAbsolutePath(), finalPath);
            }
        }

    }

    public void armazenarDadosEmArquivoTxt(List<String> dados, String nomeArquivo) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        List<String> textoGravar = new ArrayList<>();
        for (String linha : dados) {
            if (!linha.trim().isEmpty()) {
                textoGravar.add(linha);
            }
        }
        String nomeFinal = nomeArquivo + ".txt";
        manipulaArquivo.salvarArquivo(nomeFinal, textoGravar);
    }

    public List<String> buscarDadosEmArquivoTxt(String nomeArquivo) {
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        String nomeFinal = nomeArquivo + ".txt";

        File file = new File(nomeFinal);

        List<String> dados = new ArrayList<>();

        if (file.exists()) {
            List<String> arquivoLido = manipulaArquivo.abrirArquivo(nomeFinal);
            for (String linha : arquivoLido) {
                if (!linha.trim().isEmpty()) {
                    dados.add(linha);
                }
            }

        }
        return dados;
    }

}
