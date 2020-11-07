import java.io.IOException;
import java.util.Map;

public interface InputFileReader
{
    public Map<String, Map<String, String>> read(String file) throws IOException;
}
