package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {

    private final int WIDTH=464,HEIGHT=487;
    private JPanel mainPanel;
    private JLabel iconLabel;


    public MainFrame() throws HeadlessException, IOException {
        super("Танчики Dandy");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        drawingTheInterface();
        this.setVisible(true);
    }

    private void drawingTheInterface() throws IOException {
        iconLabel=new MyBufferedImage();

        mainPanel=new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setSize(getWidth(),getHeight());
        mainPanel.setOpaque(false);

        JPanel panel1=new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.X_AXIS));
        panel1.setOpaque(false);
        panel1.setBorder(BorderFactory.createEmptyBorder(12,12,12,24));

        ButtonPlay shop=new ButtonPlay(ImageIO.read(new File("Картинки\\Магазин.png")),new Color(210, 24, 24,100),new Color(29, 206, 42, 50),"<html><font color=white>Магазин</font></html>");
        ButtonPlay сustomization=new ButtonPlay(ImageIO.read(new File("Картинки\\Настройки.png")),new Color(210, 24, 24,100),new Color(29, 206, 42, 50),"<html><font color=white>Настройки</font></html>");

        panel1.add(shop);
        panel1.add(Box.createHorizontalGlue());
        panel1.add(сustomization);

        JPanel panel2=new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));
        panel2.setOpaque(false);

        ButtonPlay play=new ButtonPlay(ImageIO.read(new File("Картинки\\Запуск.png")),new Color(193, 173, 42,200),new Color(253, 239, 52, 206),"<html><h1><font color=white size=+4 >Играть</font></h1></html>");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                FrameSelectLevel frameSelectLevel=new FrameSelectLevel();
                frameSelectLevel.close(MainFrame.this);
            }
        });

        panel2.add(Box.createHorizontalStrut(100));
        panel2.add(play);
        panel2.add(Box.createHorizontalStrut(100));
        panel2.add(iconLabel);

        mainPanel.add(panel1);
        mainPanel.add(panel2);

        setContentPane(iconLabel);
        getLayeredPane().add(mainPanel,1,0);
    }

    class MyBufferedImage extends JLabel{

        private ImageIcon icon;

        public MyBufferedImage() throws IOException {
            this.icon =new ImageIcon("Картинки/Заставка на главное окно.png");//ImageIO.read(new File("C:\\Users\\HP\\IdeaProjects\\TanksAAA1\\Картинки\\Заставка на главное окно.png"));//
            this.setIcon(this.icon);
            this.setOpaque(true);
            this.setSize(getWidth(),getHeight());
            this.setBackground(Color.BLACK);
        }
    }
}
