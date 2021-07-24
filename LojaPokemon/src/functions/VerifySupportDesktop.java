/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.awt.Desktop;

/**
 *
 * @author afmireski
 */
public class VerifySupportDesktop {

    public boolean verifySupportDesktopAPI() {
        //VERIFICA SE A API DESKTOP É SUPORTADA
        if (Desktop.isDesktopSupported()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifySupportDesktopOpen(Desktop desktop) {
        //VERIFICA SE A API DESKTOP É SUPORTADA
        if (desktop.isSupported(Desktop.Action.OPEN)) {
            return true;
        } else {
            return false;
        }
    }
}
