package com.company;



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonPlay  extends JButton {
    Color colorWhenClicked,color;
    BufferedImage icon;

    public ButtonPlay(BufferedImage icon, Color colorWhenClicked, Color color,String txt) {
        super ();
        this.colorWhenClicked=colorWhenClicked;
        this.color=color;
        this.icon=icon;
        this.setText(txt);

        this.setMargin(new Insets(this.icon.getHeight()+1, 0, 0, 0));

        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().isArmed()) {
            g.setColor(this.colorWhenClicked);
        } else {
            g.setColor(this.color);
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 100, 100);

        int j=0;
        for (int i=0;i<this.icon.getWidth() & j<this.icon.getHeight();i++){
            Color color=new Color(this.icon.getRGB(i,j));
            int red=color.getRed();
            int green=color.getGreen();
            int blue=color.getBlue();
            if (red+green+blue>750){
                Color newColor=new Color(red,green,blue,0);
                icon.setRGB(i,j,newColor.getRGB());
            }
            if (i==this.icon.getWidth()-1){
                j++;
                i=-1;
            }
        }
        g.drawImage(this.icon,this.getWidth()/2-this.icon.getWidth()/2,5,null);
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
        g.setColor(Color.WHITE);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 100,100);
    }
}
