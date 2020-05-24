package com.company;

import java.awt.*;

/*Класс описывает блок - Дерево. В даннном классе не загружаетс картинка. Графический объект рисуется циклом в paintComponent*/

public class Wood extends Blocks {

    public Wood(int myX,int myY) {
        super();
        super.setMyX(myX);
        super.setMyY(myY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(102,160,22,230));
        for(int i=0;i<getHeight();i++){
            for (int j=0;j<getWidth();j++){
                if (((i==0 | i==15 | i==25 | i==40) & (j==5 | j==30)) | ((i==5 | i==10 | i==30 |i ==35) & (j==0 | j==10 | j==25 | j==35))){
                    g.fillRect(j, i, 10, 5);
                }
                if (i==20 & j==20){
                    g.fillRect(j, i, 5, 5);
                }
            }
        }
    }
}

