import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CsvReader implements InputFileReader
{
    @Override
    public Map<String, Map<String, String>> read(String csvFile) throws IOException
    {
        Map<String, Map<String, String>> res = new HashMap<>();

        Reader reader = Files.newBufferedReader(Paths.get(csvFile));
        CSVReader csvReader = new CSVReader(reader, ',');

        // Read header
        String[] headerRow = csvReader.readNext();
        List<String> headers = Arrays.stream(headerRow).collect(Collectors.toList());

        String[] row;
        while((row = csvReader.readNext()) != null)
        {
            Map<String, String> m = new HashMap<>();

            for(int j = 0; j < headers.size(); j++)
            {
                m.put(headers.get(j), row[j]);
            }

            res.put(m.get("ID"), m);
        }

        // close readers
        csvReader.close();
        reader.close();

        return res;
    }
}
