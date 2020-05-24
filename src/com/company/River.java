package com.company;

import javax.swing.*;

/*Класс описывает блок - Река*/

public class River extends Blocks {

    public River(int myX,int myY) {
        super();
        this.setIcon(new ImageIcon("C:\\Users\\HP\\IdeaProjects\\TanksAAA1\\Картинки\\Река.png"));
        super.setMyX(myX);
        super.setMyY(myY);
        setOpaque(true);
    }
}
