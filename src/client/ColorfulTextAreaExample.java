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
        textPane.setText(
        		"<div style=\"display: inline-block;\">\r\n"
        		+ "      <div style=\"float: right; background-color: rgb(52, 52, 229); max-width: fit-content; display: block; padding: 5px 10px; border-radius: 5px radius; color: white;\">\r\n"
        		+ "        <span>Hi</span>\r\n"
        		+ "      </div>\r\n"
        		+ "    </div>"
        );

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
