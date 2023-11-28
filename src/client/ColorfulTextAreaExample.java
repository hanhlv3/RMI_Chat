package client;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

public class ColorfulTextAreaExample  {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Multiple Links Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setText("<html><body><div style='text-align:right;'>" +
                "<a href='#link1' style='color: black;'>&#128513;</a><br>" +
                "<a href='#link2' style='text-decoration: underline;'>Link 2</a><br>" +
                "<a href='#link3' style='text-decoration: underline;'>Link 3</a><br>" +
                "</div></body></html>");

        textPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    String href = e.getDescription();
                    if ("#link1".equals(href)) {
                        JOptionPane.showMessageDialog(frame, "Link 1 clicked!");
                    } else if ("#link2".equals(href)) {
                        JOptionPane.showMessageDialog(frame, "Link 2 clicked!");
                    } else if ("#link3".equals(href)) {
                        JOptionPane.showMessageDialog(frame, "Link 3 clicked!");
                    }
                }
            }
        });

        frame.getContentPane().add(new JScrollPane(textPane));
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
