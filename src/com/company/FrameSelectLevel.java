package com.company;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class FrameSelectLevel extends JFrame {

    private final int WIDTH=464,HEIGHT=487;

    public FrameSelectLevel() throws HeadlessException {
        super("Танчики Dandy");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.initComponent();
        this.setVisible(true);

    }

    private void initComponent(){

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(5));
        panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        panel.setBackground(Color.BLACK);

        for (int i=1;i<11;i++){
            JButton level=new JButton("<html><h1><font color=white size=+4 >" +i+ "</font></h1></html>");
            level.setPreferredSize(new Dimension(80,80));
            level.setFocusPainted(false);
            level.setBackground(Color.CYAN);
            level.setIcon(new ImageIcon("Картинки/Уровни"));
            int finalI = i;
            level.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nameXML="xml/xml"+Integer.toString(finalI)+".xml";
                    setVisible(false);
                    try {
                        MyFrame objMyFrame=new MyFrame(nameXML);
                        objMyFrame.close(FrameSelectLevel.this);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            });
            panel.add(level);
        }

        setContentPane(panel);
    }

    void close(MainFrame objMainFrame){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                objMainFrame.setVisible(true);
            }
        });
    }
}
