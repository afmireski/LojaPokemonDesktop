package helpers;

import enums.DialogConfirmType;
import javax.swing.JOptionPane;

/**
 *
 * @author afmireski
 */
public class BuildConfirmDialog {

    private DialogConfirmType dialogType;
    private String dialogMessage;
    private String dialogTitle;
    private int response;

    public BuildConfirmDialog() {
    }

    public BuildConfirmDialog(DialogConfirmType dialogType, String dialogMessage, String dialogTitle) {
        this.dialogType = dialogType;
        this.dialogMessage = dialogMessage;
        this.dialogTitle = dialogTitle;

        switch (this.dialogType) {
            case YES_NO:
                response = JOptionPane.showConfirmDialog(null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.YES_NO_OPTION);
                break;
            case YES_NO_CANCEL:
                response = JOptionPane.showConfirmDialog(null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.YES_NO_CANCEL_OPTION);
                break;
            case OK:
                response = JOptionPane.showConfirmDialog(null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.OK_OPTION);
                break;
            case OK_CANCEL:
                response = JOptionPane.showConfirmDialog(null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.OK_CANCEL_OPTION);
                break;
            default:
                response = JOptionPane.showConfirmDialog(null,
                        this.dialogMessage,
                        this.dialogTitle,
                        JOptionPane.DEFAULT_OPTION);
                break;
        }
    }

    public int getResponse() {
        return response;
    }

}
