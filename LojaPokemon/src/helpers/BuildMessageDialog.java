/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import enums.DialogMessageType;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author afmireski
 */
public class BuildMessageDialog {

    private DialogMessageType dialogType;
    private String dialogMessage;
    private String dialogTitle;
    private Component screen;

    public BuildMessageDialog() {
    }

    public BuildMessageDialog(DialogMessageType dialogType, String dialogMessage,
            String dialogTitle, Component comp) {
        this.dialogType = dialogType;
        this.dialogMessage = dialogMessage;
        this.dialogTitle = dialogTitle;
        this.screen = comp;

        switch (this.dialogType) {
            case ERROR:
                JOptionPane.showMessageDialog(
                        comp,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.ERROR_MESSAGE);
                break;
            case INFO:
                JOptionPane.showMessageDialog(
                        null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.INFORMATION_MESSAGE);
                break;
            case WARNING:
                JOptionPane.showMessageDialog(
                        null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.WARNING_MESSAGE);
                break;
            case QUESTION:
                JOptionPane.showMessageDialog(
                        null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.QUESTION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(
                        null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.PLAIN_MESSAGE);
        }

    }
}
