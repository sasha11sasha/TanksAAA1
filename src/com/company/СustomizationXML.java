package com.company;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/*Класс для создания и настрйки парсера для распаковки XML файлов и созданию обработчика событий*/

public class СustomizationXML {

    //Источник данных для работы с XML файлами
    private InputSource sourseXML;
    //Создаем объектную переменную парсера, он посылает события в обработчик
    private SAXParser myParser;
    //Создаем объектную переменную обработчика событий
    private DefaultHandler myHandler;
    //ArrayList который хранит в себе карту при распаковке XML.
    private ArrayList<Blocks> myMap;

    public ArrayList<Blocks> getMyMap() {
        return myMap;
    }

    //конструктор класса
    public СustomizationXML(InputStream source){
        //Создаем символьный поток на основе байтового потока
        Reader reader=new InputStreamReader(source);
        //передаем символьный поток в источник данных с которым работает фабрика SAX, ну или любой другой распознаватель XML
        sourseXML=new InputSource(reader);
        //Создаемм ArrayList для хранения объектов карты
        myMap=new ArrayList<>();
        //Создаем парсер
        try {
            myParser= SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        //Создаем обработчик событий
        myHandler=new MyHandlerXML();
    }

    //Метод для настройки парера. Аргументом передаем источник данных и обработчик событий.
    public void parse() throws IOException, SAXException {
        myParser.parse(sourseXML,myHandler);
    }

    //Внутренний клас для обработки событий
    class MyHandlerXML extends DefaultHandler {

        //Поля для хранения координат расположения Блоков
        private int myX=0,myY=0;

        //Переопределенный метод обработки события открытия элемента
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (qName.equals("Строка")){
                line(attributes);
            }
            if (qName.equals("Блок")){
                block(attributes);
            }
        }

        //В методе считываем атрибут "Номер" и в зависимости от него формируем координату Y
        private void line(Attributes attributes){
            int k=Integer.parseInt(attributes.getValue("номер"));
            myY=k*45;
        }

        //В данном методе формируем координату Х и создаем Определенный Блок
        private void block(Attributes attributes){
            //Считываем у определенного блока атрибут номера и в зависимости от его значения формируем координату X
            int k=Integer.parseInt(attributes.getValue("номер"));
            myX=k*45;
            //В зависимости от значения атрибута "Тип" создаем соответствующий Тип Блока и добавляем его в ArrayList
            String type=attributes.getValue("тип");
            switch (type){
                case "кирпичная стена":
                    BrickWall brickWall=new BrickWall(myX,myY);
                    myMap.add(brickWall);
                    break;
                case "река":
                    River river=new River(myX,myY);
                    myMap.add(river);
                    break;
                case "дерево":
                    Wood wood=new Wood(myX,myY);
                    myMap.add(wood);
                    break;
                case "железная стена":
                    IronWall ironWall=new IronWall(myX,myY);
                    myMap.add(ironWall);
                    break;
            }
        }


    }
}
