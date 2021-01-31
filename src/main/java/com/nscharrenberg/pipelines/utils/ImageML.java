package com.nscharrenberg.pipelines.utils;

import java.io.DataInputStream;
import java.io.IOException;

public class ImageML
{
    public static final int NUM_FILES = 10;
    public static final int NUM_IMAGES = 1000;
    public static final int IMAGE_WIDTH = 28;
    public static final int IMAGE_HEIGHT = 28;
    public static final int IMAGE_SIZE = IMAGE_WIDTH * IMAGE_HEIGHT;


    public void start()
    {
        ImageBuilder builder = new ImageBuilder(IMAGE_WIDTH, IMAGE_HEIGHT);
        DataImporter importer;

        for(int i=0; i<NUM_FILES; i++)
        {
            importer = new DataImporter("data" + i);
            for(int j=0; j<NUM_IMAGES; j++){
                int[] bytes = importer.readNextBytes(IMAGE_WIDTH * IMAGE_HEIGHT);
                int[][] array = convertTo2D(bytes, IMAGE_WIDTH, IMAGE_HEIGHT);
                builder.drawArray(array);
                builder.saveToFile("testing/_" + i + "/" + i + "" + j);

            }
        }

        System.out.println("Finished creating all images!");
    }
    public void decodeScreen()
    {

    }
    public int[][] convertTo2D(int[] arr, int width, int height){
        int[][] result = new int[width][height];
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                result[x][y] = arr[y*width + x];
            }
        }
        return result;
    }



    class DataImporter
    {

        private DataInputStream in;

        public DataImporter(String filename)
        {
            in = new DataInputStream(getClass().getResourceAsStream("/" + filename));
        }

        /**
         * Read the next number of bytes from the loaded binary file
         * @param numberBytes
         * @return array of bytes read
         */
        public int[] readNextBytes(int numberBytes){
            int[] intArr = new int[numberBytes];
            try {
                for(int i=0; i<numberBytes; i++){
                    intArr[i] = in.readUnsignedByte();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return intArr;
        }

    }

}
