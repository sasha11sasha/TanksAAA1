package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*Данный класс описывает Пулю и ее таймер*/

public class Bullet extends Model {

    //Поля размер,шаг производимый пулей за одну итерацию
    private final int SIZE = 5, shag = 5;
    //Поле для хранения стороны в которую двигается пуля
    private int bufStorona;
    //Поле говорящее надо или не надо удалять пулю
    private boolean myRemoveBullet;
    //Объектная переменная таймера, выполняющего полет пули в отдельном потоке.
    private Timer timer;

    public boolean isMyRemoveBullet() {
        return myRemoveBullet;
    }

    //Конструктор пули
    public Bullet(int myX, int myY, int bufStorona) {
        //У пули не будет картинки, т.к. она в по углам вверху должна быть прозрачна. Это из-за того что она пролетает над речкой.
        //А саму пулю отрисуем в том же самом методе paintComponent().
        super(null);
        this.setSize(SIZE, SIZE);
        this.setLocation(myX, myY);
        this.myRemoveBullet=true;
        super.setSIZE(this.SIZE);
        super.setShag(this.shag);
        this.bufStorona = bufStorona;
        this.setOpaque(false);
    }

    //Метод для создания объекта таймера и его запуска. Также при созданиии объекта BulletFlight мы передаем ему аргументом
    //форму на которой существуют эти пули, это необходимо из-за потребности их удаления оттуда.
    protected void start(MyFrame myFrame) {
        timer = new Timer(20, new BulletFlight(myFrame));
        timer.start();
    }

    //Переопределяем метод ,т.к. есть необходимость во вращении и в отрисовки графического объекта пули
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.myPaintComponent(g, this.bufStorona);
        g.setColor(Color.RED);
        g.fillRect(0,3,5,2);
        g.setColor(Color.orange);
        g.fillRect(0,2,5,1);
        g.setColor(Color.YELLOW);
        g.fillRect(1,1,3,1);
        g.fillRect(2,0,1,1);

//        g.setColor(Color.RED);
//        g.fillRect(1,12,13,1);
//        g.fillRect(0,13,15,2);
//        g.setColor(Color.orange);
//        g.fillRect(2,11,11,1);
//        g.fillRect(1,10,13,1);
//        g.fillRect(0,8,15,2);
//        g.setColor(Color.YELLOW);
//        for (int i=0,j=7,k=15;i<8;i++,j--,k-=2){
//            g.fillRect(i,j,k,1);
//        }
    }

    //Внутренний класс содержащий слушателя для выполнения его в таймере.
    class BulletFlight implements ActionListener {

        //Поле фрейма для хранения его и последующего удаления.
        private MyFrame myFrame;

        //конструктор объекта слушателя полета пули
        public BulletFlight(MyFrame myFrame) {
            this.myFrame = myFrame;
        }

        //Переопределяем метод actionPerformed
        @Override
        public void actionPerformed(ActionEvent e) {
            //Сначала проверяем сторону в которую должна двигаться пуля, затем проверяем возможность ее дальнейшего движения
            //и после этого только вызываем соответствующий метод передвижения. В случае если дальнейший полет пули невозможен
            //то мы вызываем метод удаления ее отовсюду
            if (bufStorona == 1) {
                if (stop(bufStorona,myFrame) == true) {
                    up();
                } else remove();
            }
            if (bufStorona == 2) {
                if (stop(bufStorona,myFrame) == true) {
                    down();
                } else remove();
            }
            if (bufStorona == 3) {
                if (stop(bufStorona,myFrame) == true) {
                    right();
                } else remove();
            }
            if (bufStorona == 4) {
                if (stop(bufStorona,myFrame) == true) {
                    left();
                } else remove();
            }
        }

        //Метод зачистки информации о пули и удаление ее самой.
        private void remove() {
            //Присваиваем соответствующей пули поле отвечающее за необходимость удаления.
            myRemoveBullet=false;
            //Перебираем ArrayList со всеми пулями
            for (int i = 0; i < myFrame.getAmmunition().size(); i++) {
                if (myFrame.getAmmunition().get(i).isMyRemoveBullet()==false) {
                    // И все пули с полем False последовательно останавливаем у них таймер
                    myFrame.getAmmunition().get(i).timer.stop();
                    //Затем удаляем ее с формы и перерисовываем ее
                    myFrame.getLayeredPane().remove(myFrame.getAmmunition().get(i));
                    //Затем удаляем из ArrayList
                    myFrame.getAmmunition().remove(i);
                    myFrame.getLayeredPane().repaint();
                }
            }
        }
    }
}
