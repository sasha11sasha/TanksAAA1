package com.company;

import javax.swing.*;

/*Класс описывает Блок - Железная мтена*/

public class IronWall extends Blocks {

    public IronWall(int myX,int myY) {
        super();
        this.setIcon(new ImageIcon("C:\\Users\\HP\\IdeaProjects\\TanksAAA1\\Картинки\\Железная стена.png"));
        super.setMyX(myX);
        super.setMyY(myY);
        setOpaque(true);
    }
}
