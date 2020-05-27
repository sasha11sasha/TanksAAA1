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
            return stopRight(myFrame);
        }
        if (bufStorona==4){
            return stopLeft(myFrame);
        }
        return  true;
    }

    private boolean stopUp (MyFrame myFrame){
        if (this.getY() <= 0) {
            return false;
        }
        for (int i = 0; i < myFrame.getMap().size(); i++) {
                if (myFrame.getMap().get(i).getMyY()<this.getY()+this.getHeight() & myFrame.getMap().get(i).getMyY() + myFrame.getMap().get(i).getHeight() >= this.getY() &
                        myFrame.getMap().get(i).getMyX()<this.getX()+this.getWidth() & myFrame.getMap().get(i).getMyX()+ myFrame.getMap().get(i).getWidth()>this.getX()) {
                    if (myFrame.getMap().get(i).getIcon()==null){
                        for (int j = 0; j < myFrame.getMap().size(); j++){
                            if (myFrame.getMap().get(i).getMyY()==myFrame.getMap().get(j).getMyY()+myFrame.getMap().get(j).getHeight()
                                    & myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()==myFrame.getMap().get(j).getMyX() & myFrame.getMap().get(j).getIcon()!=null){
                                if (this.getX()+this.getWidth()>myFrame.getMap().get(j).getMyX() & this.getY()<=myFrame.getMap().get(j).getMyY()+myFrame.getMap().get(j).getHeight()){
                                    return false;
                                }
                            }
                        }
                    }
                    else {
                        return individualStop(myFrame.getMap().get(i));
                    }
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
                if (myFrame.getMap().get(i).getIcon()==null){
                    for (int j = 0; j < myFrame.getMap().size(); j++){
                        if(myFrame.getMap().get(i).getMyY()+myFrame.getMap().get(i).getHeight()==myFrame.getMap().get(j).getMyY() &
                                myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()==myFrame.getMap().get(j).getMyX() & myFrame.getMap().get(j).getIcon()!=null){
                            if (this.getX()+this.getWidth()>myFrame.getMap().get(j).getMyX() & this.getY()+this.getWidth()>=myFrame.getMap().get(j).getMyY()) {
                                return false;
                            }
                        }
                    }
                }
                else{
                    return individualStop(myFrame.getMap().get(i));
                }
            }
        }
        return true;
    }

    private Boolean stopRight(MyFrame myFrame){
        if (this.getX() + this.SIZE >= 450) return false;
        for (int i = 0; i < myFrame.getMap().size(); i++) {
            if (myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()>this.getX() & myFrame.getMap().get(i).getMyX()<=this.getX()+this.getWidth() &
                    myFrame.getMap().get(i).getMyY()<this.getY()+this.getHeight() & myFrame.getMap().get(i).getMyY()+myFrame.getMap().get(i).getHeight()>this.getY()) {
                if (myFrame.getMap().get(i).getIcon()==null){
                    for (int j = 0; j < myFrame.getMap().size(); j++){
                        if(myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()==myFrame.getMap().get(j).getMyX() &
                                myFrame.getMap().get(i).getMyY()+myFrame.getMap().get(i).getHeight()==myFrame.getMap().get(j).getMyY() & myFrame.getMap().get(j).getIcon()!=null){
                            if (this.getX()+this.getWidth()>=myFrame.getMap().get(j).getMyX() & this.getY()+this.getHeight()>myFrame.getMap().get(j).getMyY()) {
                                return false;
                            }
                        }
                    }
                }
                else {
                    return individualStop(myFrame.getMap().get(i));
                }
            }
        }
        return true;
    }

    private Boolean stopLeft(MyFrame myFrame){
        if (this.getX() <= 0) return false;
        for (int i = 0; i < myFrame.getMap().size(); i++) {
            if (myFrame.getMap().get(i).getMyX()+myFrame.getMap().get(i).getWidth()>=this.getX() & myFrame.getMap().get(i).getMyX()<this.getX()+this.getWidth() &
                    myFrame.getMap().get(i).getMyY()<this.getY()+this.getHeight() & myFrame.getMap().get(i).getMyY()+myFrame.getMap().get(i).getHeight()>this.getY()) {
                if (myFrame.getMap().get(i).getIcon()==null){
                    for (int j = 0; j < myFrame.getMap().size(); j++){
                        if(myFrame.getMap().get(i).getMyX()==myFrame.getMap().get(j).getMyX()+myFrame.getMap().get(j).getWidth() &
                                myFrame.getMap().get(i).getMyY()+myFrame.getMap().get(i).getHeight()==myFrame.getMap().get(j).getMyY() & myFrame.getMap().get(j).getIcon()!=null){
                            if (this.getX()<=myFrame.getMap().get(j).getMyX()+myFrame.getMap().get(j).getWidth() & this.getY()+this.getHeight()>myFrame.getMap().get(j).getMyY()) {
                                return false;
                            }
                        }
                    }
                }
                else {
                    return individualStop(myFrame.getMap().get(i));
                }
            }
        }
        return true;
    }

    private Boolean individualStop(Blocks blocks){
        if (blocks.getIcon()==null){
                return true;
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
        //для сглаживания изображения
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //находим центр относительно вращения, на некоторых компьютерах остается смещенным .Дополнительно нужно вычитать 0.1
        double center=this.SIZE/2.0-0.1;
        if (bufStorona==1){
            g2d.rotate(4*Math.PI/2,center,center);
        }
        if (bufStorona==2){
            g2d.rotate(Math.PI,center,center);
        }
        if (bufStorona==3){
            g2d.rotate(Math.PI/2,center,center);
        }
        if (bufStorona==4){
            g2d.rotate(3*Math.PI/2,center,center);
        }
        g2d.drawImage(icon,0,0,null);
    }
}
