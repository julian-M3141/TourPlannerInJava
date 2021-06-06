package at.technikum.dataAccess.fileAccess;

import at.technikum.dataAccess.fileAccess.IFileHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;


@Component
@Profile("test")
public class FileHandlerMocked implements IFileHandler {
    @Override
    public void save(String path, String filename, String content) throws IOException {
    }

    @Override
    public void saveNewFile(String path, String filename, String content) throws IOException {
    }

    @Override
    public void delete(String path, String filename) throws IOException {
    }

    @Override
    public String read(String path, String filename) throws FileNotFoundException {
        return "some content";
    }
}
