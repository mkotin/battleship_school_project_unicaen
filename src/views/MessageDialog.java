package views;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/** A class for showing message dialog in the gui */
public class MessageDialog {
    /** The message to show */
    private String message;

    /** The type of the message */
    private int type;

    public MessageDialog(String message, int type) {
        // Load font for emojis
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);

        this.message = message;
        this.type = type;
    }

    /**
     * Shows a simple message dialog
     */
    public void showMessageDialog() {
        switch (type) {
            case JOptionPane.ERROR_MESSAGE:
                JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
                break;
            case JOptionPane.INFORMATION_MESSAGE:
                JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
                break;
            case JOptionPane.WARNING_MESSAGE:
                JOptionPane.showMessageDialog(null, message, "Avertissement", JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, message);
                break;
        }
        loadDefaultFont();
    }

     /**
     * Shows a message dialog with confimation buttons
     */
    public int showConfirmationMessageDialog(String okButtonLabel, String cancelButtonLabel) {
        int res = JOptionPane.showOptionDialog(null, message, "",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[] { okButtonLabel, cancelButtonLabel }, JOptionPane.OK_OPTION);

        loadDefaultFont();

        return res;
    }

    /** Load default font */
    public void loadDefaultFont() {
        // Get default system font name
        String defaultLAF = UIManager.getSystemLookAndFeelClassName();

        // Set default swing apperance
        try {
            UIManager.setLookAndFeel(defaultLAF);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Restore default font
        UIManager.put("OptionPane.messageFont", new Font(Font.DIALOG, Font.PLAIN, 12));
    }
}
