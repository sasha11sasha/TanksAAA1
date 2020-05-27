package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*Класс описывает работ с танком пользователя*/

public class MyTanks extends Model {

    //Поля отвечающие за размер и шаг танка
    private final int SIZE = 45, shag = 5;
    //Переменная отвечающая за координаты квадратиков ,появляющихся постепенно при перезарядке танка.И одновременно не позволяющая производить выстрел.
    private int startX=-1;
    //Таймер отвечающий за иметацию загрузки перезарядки
    private Timer timer;

    public int getSIZE() {
        return SIZE;
    }

    //Конструктор танка.
    public MyTanks() throws IOException {
        super(ImageIO.read(new File("Картинки\\Мой Танк.png")));
        this.setBackground(Color.BLUE);
        this.setSize(SIZE, SIZE);
        super.setSIZE(this.SIZE);
        super.setShag(this.shag);
    }

    //Метод отвечающий за определение может ли танк стрелять или нет
    protected boolean recharge(){
        //Если танк может стрелять то он
        if (startX==-1){
            //Создаем и запускает таймер выполняющий действия по перезарядке.
            timer =new Timer (66,new Recharge());
            startX++;
            timer.start();
            return true;
        }
        //И пока startX снова не будет равен -1 то танк не сможет стрелять.
        return false;
    }

    //Переопределяем метод по перерисовке картинки и ее вращения на танке
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.myPaintComponent(g, super.getBufStorona());
    }

    //Внутреннй класс реализующий слушателя для реализации полосы перезарядки на танке
    class Recharge implements ActionListener{

        //Поле для хранения панелек загрузки.
        ArrayList<JPanel> bufPanel=new ArrayList<>();

        @Override
        public void actionPerformed(ActionEvent e) {
            //Создаем красную панельку 3*3
            JPanel pan=new JPanel();
            pan.setBackground(Color.RED);
            pan.setSize(3,3);
            //Задаем панельке необходимые координаты и добавляем на танк
            pan.setLocation(startX,0);
            add(pan,4,0);
            //Добавляем панельку в ArrayList
            bufPanel.add(pan);
            //Перерисовываем все область по переиметру танка шириной равной ширине панельки. Для экономии производительности
            repaint(0,0,getWidth(),pan.getHeight());
            repaint(getWidth()-pan.getWidth(),0,pan.getWidth(),getHeight());
            repaint(0,getHeight()-pan.getHeight(),getWidth(),pan.getHeight());
            repaint(0,0,pan.getWidth(),getHeight());
            //Будем прибовлять к координате Х по 3 и таким образом постепенно заполнять полосу
            if (startX<getSIZE()){
                startX+=3;
            }
            //Как только координаты будут меньше размера танка, т.е. полоса полностью заполнится
            else{
                //Сообщаем что выстрел снова может быть произведен
                startX=-1;
                //Вызываем метод по зачистки полосы перезарядки
                myRemove();
                //И останавливаем таймер
                timer.stop();
            }
        }

        //метод по зачистке полосы перезарядки
        private void myRemove(){
            while(bufPanel.size()!=0){
                MyTanks.this.remove(bufPanel.get(0));
                this.bufPanel.remove(0);
            }
            repaint(0,0,getWidth(),3);
        }
    }
}
