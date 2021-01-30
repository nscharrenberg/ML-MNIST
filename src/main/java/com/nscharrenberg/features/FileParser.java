package com.nscharrenberg.features;

import au.com.bytecode.opencsv.CSVWriter;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileParser
{
    private static boolean makeLabelByDirectory;


    private static ClassLoader classLoader = FileParser.class.getClassLoader();






    public static void storeCsvFromBytes (String toRead,String toSave)
    {
        InputStream inputStream = classLoader.getResourceAsStream(toRead);
        ArrayList<String[]> collect = new ArrayList<>();
        try {
            //TODO: add file format logic
            byte [] store = inputStream.readAllBytes();
            int logic = 1;
            String [] storeStrings = new String[logic];
            for (int i = 0; i< logic; i++ )
                storeStrings[i] = String.valueOf(store[i]);
             collect.add(storeStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            FileWriter outputFile = new FileWriter(toSave);
            CSVWriter writer = new CSVWriter(outputFile,',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END
                    );
            writer.writeAll(collect);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    enum fileType
    {
        CSV,
        ZIP,
        BIN,
        PNG,
        JPG
    }
}
