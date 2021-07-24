/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import enums.DialogMessageType;
import helpers.BuildMessageDialog;
import helpers.ErrorTools;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author afmireski
 */
public class DesktopActions {

    public void openFile(Desktop desk, File file) {
        try {
            desk.open(file);
        } catch (IOException ioe) {
            ErrorTools errorTools = new ErrorTools();
            errorTools.showIOExceptionStackTrace(ioe);
            BuildMessageDialog buildMessageDialog = new BuildMessageDialog(
                    DialogMessageType.ERROR,
                    ioe.getMessage(),
                    "DESKTOP ERROR",
                    null);
        }
    }

}
