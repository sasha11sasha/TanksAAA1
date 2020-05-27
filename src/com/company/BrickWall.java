package com.company;

import javax.swing.*;

/*Класс описывает Блок - Кирпичная стена */

public class BrickWall extends Blocks {

    public BrickWall(int myX,int myY) {
        super();
        //Присваиваем необходимую картинку
        this.setIcon(new ImageIcon("Картинки\\Кирпичная стена.png"));
        //Задаем координаты расположения
        super.setMyX(myX);
        super.setMyY(myY);
        setOpaque(true);
    }
}
