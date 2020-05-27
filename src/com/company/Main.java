package com.company;

import java.io.IOException;

//Главный класс

public class Main {

    public static void main(String[] args) {
        //Запускаем всю игру из потока рассылки событий.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
