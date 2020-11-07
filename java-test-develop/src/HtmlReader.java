import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlReader implements InputFileReader
{
    @Override
    public Map<String, Map<String, String>> read(String file) throws IOException
    {
        Map<String, Map<String, String>> res = new HashMap<>();

        File input = new File(file);
        Document doc = Jsoup.parse(input, "UTF-8");

        List<String> headers = new ArrayList<>();

        Elements el = doc.select("th");

        for(Element e : el)
        {
            headers.add(e.text());
        }

        el = doc.select("tr");

        for(int i = 1; i < el.size(); i++)
        {
            Elements rowData = el.get(i).select("td");
            Map<String, String> m = new HashMap<>();

            for(int j = 0; j < headers.size(); j++)
            {
                m.put(headers.get(j), rowData.get(j).text());
            }

            res.put(m.get("ID"), m);
        }

        return res;
    }

    private void _print(Object o)
    {
        System.out.println(o);
    }
}
