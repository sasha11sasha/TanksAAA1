package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*Данный класс отвечает за описания всего что объединяет подвижные объекты:
* за их движение, за одинаковые поля, отрисовки и прочее*/

public class Model extends JLabel implements Models {

    //Объектные переменные отвечающие за размер,шаг движения за один раз, и последнюю сторону движения соответственно.
    private int SIZE,shag,bufStorona=1;
    //Объектная переменная которая отвечает за картинку расположенную сверху объекта
    private BufferedImage icon;

    public void setBufStorona(int bufStorona) {
        this.bufStorona = bufStorona;
    }

    public int getBufStorona() {
        return bufStorona;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public void setShag(int shag) {
        this.shag = shag;
    }

    //конструктор класса
    public Model(BufferedImage icon) {
        super();
        this.icon=icon;
        //Нам как бы и не нужно что бы эти объекты были видимы(т.к. на них есть картинка), но невидимые объекты требуют больше производительности
        this.setOpaque(true);
    }

    //Метод движения вверх
    @Override
    public void up() {
            this.setLocation(this.getX(), this.getY() - this.shag);
    }

    //Метод движения вниз
    @Override
    public void down() {
            this.setLocation(this.getX(),this.getY()+this.shag);
    }

    //Метод движения влево
    @Override
    public void left() {
            this.setLocation(this.getX() - this.shag, this.getY());
    }

    //Метод движения вправо
    @Override
    public void right() {
            this.setLocation(this.getX() + this.shag, this.getY());
    }

    //Метод который определяет правила при которых объекту запрещенно дальнейшее движение, ему передается аргументом bufStorona,
    //т.к. у пули своя И
    protected Boolean stop(int bufStorona,MyFrame myFrame){
        //В данном методе мы в зависимости от стороны движения проверяем необходимые условия, которые определяют возможность дальнейшего движения
        if (bufStorona==1) {
            return stopUp(myFrame);
        }
        if(bufStorona==2){
            return stopDown(myFrame);
        }
        if (bufStorona==3){
            if (this.getX() + this.SIZE >= 450) return false;
        }
        if (bufStorona==4){
            if (this.getX() <= 0) return false;
        }
        return  true;
    }

    private boolean stopUp (MyFrame myFrame){
        if (this.getY() <= 0) {
            return false;
        }
        for (int i = 0; i < myFrame.getMap().size(); i++) {
                if (myFrame.getMap().get(i).getMyY()<this.getY()+this.getHeight() & myFrame.getMap().get(i).getMyY() + myFrame.getMap().get(i).getHeight() >= this.getY() &
                        myFrame.getMap().get(i).getMyX()<this.getX()+this.getWidth() & myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()>this.getX()) {
                    return individualStop(myFrame.getMap().get(i));
                }
            }
        return true;
    }

    private Boolean stopDown(MyFrame myFrame){
        if (this.getY()+this.SIZE  >= 450) {
            return false;
        }
        for (int i = 0; i < myFrame.getMap().size(); i++) {
            if (myFrame.getMap().get(i).getMyY()<=this.getY()+this.getHeight() & myFrame.getMap().get(i).getMyY()+myFrame.getMap().get(i).getHeight()>this.getY() &
                    myFrame.getMap().get(i).getMyX()<this.getX()+this.getWidth() & myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()>this.getX()) {
                return individualStop(myFrame.getMap().get(i));
            }
        }
        return true;
    }

    private Boolean individualStop(Blocks blocks){
        if (blocks.getIcon()==null){
            if (this.getClass().getName().equals("com.company.MyTanks")) {
                return true;
            }
        }
        else{
            if (this.getClass().getName().equals("com.company.Bullet")){
                StringBuffer nameIcon= new StringBuffer(blocks.getIcon().toString());
                String nameRiver=nameIcon.substring(nameIcon.length()-8,nameIcon.length());
                if (nameRiver.equals("Река.png")){
                    return true;
                }
            }
        }
        return false;
    }

    //Метод который вызывается в переопределенных методах paintComponent, отвечает за поворот картинки в нужную сторону при движении
    protected void myPaintComponent(Graphics g,int bufStorona){
        Graphics2D g2d=(Graphics2D)g;
        if (bufStorona==1){
            g2d.rotate(4*Math.PI/2);
        }

        if (bufStorona==2){
            g2d.rotate(Math.PI);
        }
        if (bufStorona==3){
            g2d.rotate(Math.PI/2);
        }
        if (bufStorona==4){
            g2d.rotate(3*Math.PI/2);
        }
        g2d.translate(g2d.getClipBounds().x,g2d.getClipBounds().y);
        g2d.drawImage(icon,0,0,null);
    }
}
