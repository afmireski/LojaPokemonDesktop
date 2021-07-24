/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author afmireski
 */
public class GenericComponents {

    ///SPINNERS
    public JSpinner createIntSpinner(int initialValue, int minValue, int maxValue, int passo) {
        SpinnerNumberModel model = new SpinnerNumberModel(
                initialValue,
                minValue,
                maxValue,
                passo);
        return new JSpinner(model);
    }

    public JSpinner createDoubleSpinner(double initialValue, double minValue, double maxValue, double passo) {
        SpinnerNumberModel model = new SpinnerNumberModel(
                initialValue,
                minValue,
                maxValue,
                passo);
        return new JSpinner(model);
    }

    public JSpinner createListSpinner(String list[], int width) {
        SpinnerListModel model = new SpinnerListModel(list);
        JSpinner jSpinner = new JSpinner(model);

        JComponent editor = jSpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(width); //DEFINE A LARGURA DO SPINER
        return jSpinner;
    }

    ///PANELS
    public JPanel createChildPanel(Component child, FlowLayout layout) {
        JPanel jPanel = new JPanel(layout);
        jPanel.add(child);
        return jPanel;
    }

    ///COMBO BOX
    public JComboBox createComboBox(List<String> list) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        JComboBox comboBox = new JComboBox(model);
        if (list != null) {
            for (String str : list) {
                model.addElement(str);
            }
        }
        return comboBox;
    }
    
    public JComboBox createComboBoxFromMaps(List<HashMap> list, Object key1, Object key2) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        JComboBox comboBox = new JComboBox(model);
        if (list != null) {
            for (HashMap<Object, Object> map : list) {
                model.addElement(map.get(key1) + " - " + map.get(key2));
            }
        }
        return comboBox;
    }

    ///FILE CHOOSER
    public JFileChooser createFileChooser(FileNameExtensionFilter filter, int selectedMode) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(filter);
        jFileChooser.setFileSelectionMode(selectedMode);

        return jFileChooser;
    }

    ///BUTTONS
    public JButton buttonWithIcon(String label, String iconPath) {
        JButton jButton;
        if (iconPath != null && !iconPath.trim().isEmpty()) {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource(iconPath));
            jButton = new JButton(label, icon);
        } else {
            jButton = new JButton(label);
        }
        return jButton;
    }

    public JButton iconButton(String iconPath) {
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource(iconPath));
        JButton jButton = new JButton(icon);
        return jButton;
    }

}
