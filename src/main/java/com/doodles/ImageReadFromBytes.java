package com.doodles;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;

public class ImageReadFromBytes {
    static byte[] storebats, storesun, storeflower, storerainbow;

    public static void main(String[] lol) throws Exception {

        ArrayList<String[]> animals = new ArrayList<>();

        ImageReadFromBytes app = new ImageReadFromBytes();

        //String fileName = "database.properties";
        String fileName = "data/bats1000.bin";
        System.out.println("getResourceAsStream : " + fileName);


        try {
            ClassLoader classLoader = app.getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            storebats = inputStream.readAllBytes();
            int singlelength = storebats.length / 1000;
            for (int i = 0; i < 900; i++) {
                String[] a = new String[singlelength + 1];
                a[0] = String.valueOf(0);
                for (int k = 0; k < singlelength; k++) {
                    a[k + 1] = String.valueOf(storebats[k + (i * singlelength)]);
                }
                animals.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        fileName = "data/sun1000.bin";
        System.out.println("getResourceAsStream : " + fileName);
        try {
            ClassLoader classLoader = app.getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            storesun = inputStream.readAllBytes();
            int singlelength = storesun.length / 1000;
            for (int i = 0; i < 900; i++) {
                String[] a = new String[singlelength + 1];
                a[0] = String.valueOf(1);
                for (int k = 0; k < singlelength; k++) {
                    a[k + 1] = String.valueOf(storesun[k + (i * singlelength)]);
                }
                animals.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        fileName = "data/flower1000.bin";
        System.out.println("getResourceAsStream : " + fileName);
        try {
            ClassLoader classLoader = app.getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            storeflower = inputStream.readAllBytes();
            int singlelength = storeflower.length / 1000;
            for (int i = 0; i < 900; i++) {
                String[] a = new String[singlelength + 1];
                a[0] = String.valueOf(2);
                for (int k = 0; k < singlelength; k++) {
                    a[k + 1] = String.valueOf(storeflower[k + (i * singlelength)]);
                }
                animals.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        fileName = "data/rainbow1000.bin";
        System.out.println("getResourceAsStream : " + fileName);
        try {
            ClassLoader classLoader = app.getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            storerainbow = inputStream.readAllBytes();
            int singlelength = storerainbow.length / 1000;
            for (int i = 0; i < 900; i++) {
                String[] a = new String[singlelength + 1];
                a[0] = String.valueOf(3);
                for (int k = 0; k < singlelength; k++) {
                    a[k + 1] = String.valueOf(storerainbow[k + (i * singlelength)]);
                }
                animals.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            System.out.println("all animals : " + animals.size());
            System.out.println("expected : " + 3600);
            FileWriter outputfile = new FileWriter("animals_data_train.csv");
            CSVWriter writer = new CSVWriter(outputfile, ',', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(animals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}