/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author afmireski
 */
public class Capitalize {

    public String capitalizeTextUpper(String str) {
        //DEIXA A PRIMEIRA LETRA DE CADA PALAVRA MAIÚSCULA
        String result = "";
        for (int i = 1; i < str.length(); i++) {
            String letra = str.substring(i - 1, i);
            if (i == 1) {
                letra = letra.toUpperCase();
            } else if (letra.trim().isEmpty() && i != str.length() - 1) {
                letra = str.substring(i, i + 1).toUpperCase();
            }

            result += letra;
        }
        return result += str.substring(str.length() - 1);
    }

    public String capitalizeVarUpper(String str) {
        String result = "";
        String aux[] = str.split(" ");
        for (String string : aux) {
            String inicial = string.substring(0, 1).toUpperCase();
            String resto = string.substring(1);
            result += inicial + resto;

        }
        return result;
    }

    public String capitalizeVarLower(String str) {
        String result = "";
        String aux[] = str.split(" ");
        int cont = 0;
        for (String string : aux) {
            String inicial;
            if (aux.length > 1) {
                if (cont < 1) {
                    inicial = string.substring(0, 1).toLowerCase();
                } else {
                    inicial = string.substring(0, 1).toUpperCase();
                }
            } else {
                inicial = string.substring(0, 1).toLowerCase();
            }
            String resto = string.substring(1);
            if (aux.length > 1) {
                resto = resto.toLowerCase();
            }
            result += inicial + resto;
            cont++;

        }
        return result;
    }

    public String capitalizeClass(String str) {
        String result = "";
        String aux[] = str.split(" ");
        for (String string : aux) {
            String inicial = string.substring(0, 1).toUpperCase();
            String resto = string.substring(1);
            if (aux.length > 1) {
                resto = resto.toLowerCase();
            }
            result += inicial + resto;
        }
        return result;
    }

    public String capitalizeTextLower(String str) {
        String result = "";
        for (int i = 1; i < str.length(); i++) {
            String letra = str.substring(i - 1, i);
            if (i == 1) {
                letra = letra.toLowerCase();
            } else if (letra.trim().isEmpty() && i != str.length() - 1) {
                letra = str.substring(i, i + 1).toUpperCase();
            }

            result += letra;
        }
        return result += str.substring(str.length() - 1);
    }

    public String removerAcentos(String str) {
        String result;
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        result = pattern.matcher(nfdNormalizedString).replaceAll("");
        return result;
    }

}
