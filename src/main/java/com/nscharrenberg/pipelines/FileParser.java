package com.nscharrenberg.pipelines;

public class FileParser
{
    public FileParser(String directory, FileType type)
    {
        if (type.equals(FileType.CSV))
            new CSVParser(directory);
    }


    class CSVParser
    {
        public CSVParser(String directory)
        {

        }

    }
    enum FileType
    {
        CSV,
        BIN,
        ZIP
    }
}
