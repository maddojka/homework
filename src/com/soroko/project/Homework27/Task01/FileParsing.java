package com.soroko.project.Homework27.Task01;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class FileParsing implements Runnable {
    private String name;
    private File file;
    private Thread t;
    private String filePath;
    private int counter;

    public FileParsing(String name, String filePath) {
        if (filePath == null) throw new IllegalArgumentException("Путь к файлу не может быть null");
        if (name == null) throw new IllegalArgumentException("Имя потока не может быть null");
        this.name = name;
        this.filePath = filePath;
        t = new Thread(this, name);
        file = new File(filePath);
        counter = 0;
    }

    public Thread getT() {
        return t;
    }

    public int getCounter() {
        return counter;
    }

    public void parseFile() {
        boolean dataExists = false;
        try (Scanner scan = new Scanner(file);) {
            while (scan.hasNext()) {
                String nextLine = scan.nextLine();
                String[] data = nextLine.split("::");
                for (int i = 1; i < data.length; i += 2) {
                    int priority = Integer.parseInt(data[i]);
                    if (priority >= 7) {
                        System.out.println("Дата и время: " + data[0] + ", приоритет "
                                + priority + ", событие: " + data[2]);
                        counter++;
                        dataExists = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("При чтении файла возникла ошибка");
        }
        if (!dataExists) {
            System.out.println("По данному критерию событий не найдено");
        }
        System.out.println("Количество событий с приоритетом 7 и выше: " + (counter));
    }

    @Override
    public void run() {
        parseFile();
    }
}
