package com.company;

import javax.swing.*;

/*Класс описывает поля которые будут общими для всех Блоков(Препятствий) на карте.
Свойство Opaque будем присваивать в каждом блоке отдельно ,т.к. дерево должно быть прозрачным.
 */

public class Blocks extends JLabel {

    //Размер Блоков
    private int SIZE=45;
    //Расположение на карте
    private int myX,myY;

    public int getSIZE() {
        return SIZE;
    }

    public void setMyX(int myX) {
        this.myX = myX;
    }

    public void setMyY(int myY) {
        this.myY = myY;
    }

    public int getMyX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }

    //Конструктор класс
    public Blocks() {
        super();
        setSize(SIZE,SIZE);
    }
}
