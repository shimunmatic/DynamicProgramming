import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {

    public static String readDataFromFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes);
    }
}
