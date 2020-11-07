
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileMerger
{
    String file1;
    String file2;
    String out = "data/combined.csv";

    InputFileReader reader1;
    InputFileReader reader2;
    Map<String, Map<String, String>> res = new HashMap();

    public FileMerger(String file1, String file2) throws Exception
    {
        this.file1 = new File(file1).getAbsolutePath();
        this.file2 = new File(file2).getAbsolutePath();

        reader1 = _read(file1);
        reader2 = _read(file2);
    }

    private String _getOutputPath()
    {
        return new File(out).getAbsolutePath();
    }

    public void merge() throws IOException
    {
        res = _merge(reader1.read(file1), reader2.read(file2));

        // write header
        Set<String> headers = _getUniqueHeaders(res, new HashMap<>());

        Writer writer = Files.newBufferedWriter(Paths.get(_getOutputPath()));
        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        csvWriter.writeNext(headers.toArray(new String[headers.size()]));

        List<String> sortedKeys=new ArrayList(res.keySet());
        Collections.sort(sortedKeys);

        for(String key : sortedKeys)
        {
            Map<String, String> r = res.get(key);
            String[] rowToWrite = new String[headers.size()];
            int i = 0;

            for(String h : headers)
            {
                rowToWrite[i] = r.get(h) != null ? r.get(h) : "N/A";
                i++;
            }

            csvWriter.writeNext(rowToWrite);
        }

        // close writers
        csvWriter.close();
        writer.close();
    }

    private static InputFileReader _read(String file) throws Exception
    {
        String ext = com.google.common.io.Files.getFileExtension(new File(file).getAbsolutePath());

        switch (ext)
        {
            case "html":
                return new HtmlReader();
            case "csv":
                return new CsvReader();
            default:
                throw new Exception("Unsupported file extension");
        }
    }


    private static Map<String, Map<String, String>> _merge(Map<String, Map<String, String>> f1, Map<String, Map<String, String>> f2)
    {
        Map<String, Map<String, String>> res = new HashMap<>();

        Set<String> uniqueHeaders = _getUniqueHeaders(f1, f2);

        Set<String> uniqueIds = new HashSet();
        uniqueIds.addAll(f1.keySet());
        uniqueIds.addAll(f2.keySet());

        for(String id : uniqueIds)
        {
            Map<String, String> r1 = f1.get(id);
            Map<String, String> r2 = f2.get(id);

            Map<String, String> m = new HashMap<>();

            if(r1 == null && r2 != null)
            {
                for(String uh : uniqueHeaders)
                {
                    m.put(uh, r2.get(uh));
                }
            }
            else if(r1 != null && r2 == null)
            {
                for(String uh : uniqueHeaders)
                {
                    m.put(uh, r1.get(uh));
                }
            }
            else
            {
                for (String header : uniqueHeaders)
                {
                    if(r1.containsKey(header))
                    {
                        m.put(header, r1.get(header));
                    }
                    else
                    {
                        m.put(header, r2.get(header));
                    }
                }
            }

            res.put(id, m);
        }

        return res;
    }

    private static Set<String> _getUniqueHeaders(Map<String, Map<String, String>> f1, Map<String, Map<String, String>> f2)
    {
        Set<String> res = new HashSet<>();

        for(var v : f1.values())
        {
            res.addAll(v.keySet());
            break;
        }

        for(var v : f2.values())
        {
            res.addAll(v.keySet());
            break;
        }

        return res;
    }

    private static void _print(Object o)
    {
        System.out.println(o);
    }
}
