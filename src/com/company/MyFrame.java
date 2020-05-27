package com.company;

import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MyFrame extends JFrame {

    //размеры главной формы
    private final int WIDTH=464,HEIGHT=487;
    //ArrayList для наполнения в него Пуль(Bullet)
    private ArrayList<Bullet> ammunition;
    private ArrayList<Blocks> map;
    //Объектная переменная танка пользователя
    private MyTanks myTank;
    private String nameXML;

    public ArrayList<Blocks> getMap() {
        return map;
    }

    public ArrayList<Bullet> getAmmunition() {
        return ammunition;
    }

    //конструктор главной формы
    public MyFrame (String nameXML) throws IOException {
        super();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        this.nameXML=nameXML;
        //Создаем ArrayLict для хранения пуль,которые находятся на форме
        ammunition=new ArrayList<>();
        initObjects();
        setVisible(true);
    }

    //Метод для добавления объектов на форму
    public void initObjects() throws IOException {
        //Вызываем метод для создания карты
        mapCreation();
        //Создаем объект танка пользователя
        myTank=new MyTanks();
        myTank.setLocation(135,405);
        //добавляем его на подслой 1,т.к. он должен проезжать под блоком дерево(Wood)
        getLayeredPane().add(myTank,2,0);
        //Присоеденяем к форме слушателя от клавиатуры
        this.addKeyListener(new KeyAdapter() {
            //Переопределяем метод для нажатия
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_W){
                    //Присваиваем переменной танка 1,она будет сообщать о том что последний раз танк двигался вверх
                    myTank.setBufStorona(1);

                    //Делаем проверку на возможность передвижения танка
                    if (myTank.stop(myTank.getBufStorona(), MyFrame.this)) {
                        //если оно возможно то вызываем метод передвижения вверх
                        myTank.up();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    //Присваиваем переменной танка 2,она будет сообщать о том что последний раз танк двигался вниз
                    myTank.setBufStorona(2);
                    //Делаем проверку на возможность передвижения танка
                    if (myTank.stop(myTank.getBufStorona(), MyFrame.this)) {
                        //если оно возможно то вызываем метод передвижения вниз
                        myTank.down();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_D){
                    //Присваиваем переменной танка 3,она будет сообщать о том что последний раз танк двигался вправо
                    myTank.setBufStorona(3);
                    //Делаем проверку на возможность передвижения танка
                    if (myTank.stop(myTank.getBufStorona(), MyFrame.this)) {
                        //если оно возможно то вызываем метод передвижения вправо
                        myTank.right();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_A){
                    //Присваиваем переменной танка 4,она будет сообщать о том что последний раз танк двигался влево
                    myTank.setBufStorona(4);
                    //Делаем проверку на возможность передвижения танка
                    if (myTank.stop(myTank.getBufStorona(), MyFrame.this)) {
                        //если оно возможно то вызываем метод передвижения влево
                        myTank.left();
                    }
                }
                myTank.repaint();

            }
            //Переопределяем метод для отпускания клавиши
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    //говорим, что если танк перезаряжен, то можно создавать пулю
                    if (myTank.recharge()){
                        //Создаем объектную переменную пули
                        Bullet bullet =null;
                        //В зависимости от того, куда ехал танк в последний раз ,создаем пулю с соответствующей стороны танка.Иначе не получается так как перерисовка у пули(
                        //независимо от приоритетов и слоев) все равно искажает картинку танка.
                        switch (myTank.getBufStorona()){
                            case 1:
                                bullet = new Bullet(myTank.getX() + 20, myTank.getY() , myTank.getBufStorona());
                                break;
                            case 2:
                                bullet = new Bullet(myTank.getX() + 20, myTank.getY() + 40, myTank.getBufStorona());
                                break;
                            case 3:
                                bullet = new Bullet(myTank.getX() + 40, myTank.getY() + 20, myTank.getBufStorona());
                                break;
                            case 4:
                                bullet = new Bullet(myTank.getX() , myTank.getY() + 20, myTank.getBufStorona());
                                break;
                        }
                        //добавляем пулю на слой ниже танка, но выше всех блоков(это из-за реки ,пуля должна лететь над ней),кроме дерева(т.к. танк должен быть ниже дерева)
                        getLayeredPane().add(bullet,1,0);
                        //добавляем пулю в ArrayList
                        ammunition.add(bullet);
                        //запускаем таймер пули и передаем аргументом нашу главную форму(т.к. нам необходимо проделывать с ней операции удаления пули)
                        try{
                            bullet.start(MyFrame.this);
                        }catch (NullPointerException r){
                            System.out.println("NullPointerException MyFrame bullet.start(MyFrame.this);");
                        }

                    }
                    //иначе танк перезаряжается
                    else {
                        myTank.recharge();
                    }
                }
            }
        });
    }

    //метод распаковки XML документа, который содержит информацию о карте.
    private void mapCreation(){
        try {
            //Создаем объект источника данных,который считывает информацию побайтово из XML
            InputStream stream=new FileInputStream(nameXML);
            //Создаем объект который создает и настраивает парсер фабрики SAX а также содержит обработчик событий приходящих из парсера во время обработки XML документа
            СustomizationXML custXML=new СustomizationXML(stream);
            //метод для настройки парсера
            try {
                custXML.parse();
                //вызываем метод непосредственной отрисовки карты.
                drawMap(custXML.getMyMap());
            }catch (FileNotFoundException e){
                System.out.println("FileNotFoundException MyFrame custXML.parse(); or drawMap(custXML.getMyMap());");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void drawMap (ArrayList<Blocks> myMap){
        this.map=myMap;
        //Перебираем ArrayList который сформировался в процессе распаковки XML файла
        for (int i=0;i<myMap.size();i++){
            //Задаем каждаму блоку соответствубщие координаты расположения
            myMap.get(i).setLocation(myMap.get(i).getMyX(),myMap.get(i).getMyY());
            //если в файле есть дерево(Wood) то его ставим в слой выше танка, т.к. танк должен проезжать под деревом.
            if (myMap.get(i).getIcon()==null){
                getLayeredPane().add(myMap.get(i),3,0);
            }
            //иначе, т.е. все остальные текстуры ставим в слой ниже пули(делаем это из-за реки, т.к. пуля должна пролетать над рекой
            else{
                getLayeredPane().add(myMap.get(i),0,0);
            }
        }
    }

    void close(FrameSelectLevel objFrameSelectLevel){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                objFrameSelectLevel.setVisible(true);
            }
        });
    }
}
