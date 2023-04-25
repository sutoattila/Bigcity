package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConflictualDestructionDialog extends OKCancelDialog {

    public ConflictualDestructionDialog(JFrame jframe, String name,
            String message) {

        super(jframe, name);
        setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(300, 100));
        textPanel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(message);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        textPanel.add(textArea, BorderLayout.CENTER);

        add(textPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    @Override
    protected boolean processOK() {
        return true;
    }

    @Override
    protected void processCancel() {

    }

}
