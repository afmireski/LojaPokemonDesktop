/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

/**
 *
 * @author afmireski
 */
public class VerifyPK {

    public boolean pkIsAInt(Object obj) {
        try {
            if (obj.toString().contains(",") || obj.toString().contains(".")) {
                throw new Exception("É Double");
            } else {
                int x = Integer.valueOf(obj.toString());
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
            return false;
        }
    }

    public boolean pkString(Object obj) {
        try {
            if (obj.toString().trim().isEmpty()) {
                throw new Exception("String Vazia");
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
            return false;
        }
    }

}
