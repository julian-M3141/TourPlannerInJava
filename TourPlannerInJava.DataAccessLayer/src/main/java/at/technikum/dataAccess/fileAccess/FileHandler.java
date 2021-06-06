package at.technikum.dataAccess.fileAccess;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


@Component
@Profile("!test")
public class FileHandler implements IFileHandler {
    @Override
    public void save(String path, String filename, String content) throws IOException {
        File newfile = new File(path + filename);
        FileWriter writer = new FileWriter(path + filename);
        writer.write(content);
        writer.close();
    }

    @Override
    public void saveNewFile(String path, String filename, String content) throws IOException {
        File newFile = new File(path + filename);
        //throws IOException if file already exists
        if (newFile.createNewFile()) {
            FileWriter writer = new FileWriter(path + filename);
            writer.write(content);
            writer.close();
        }
    }

    @Override
    public void delete(String path, String filename) throws IOException {
        File file = new File(path + filename);
        if (!file.delete()) {
            throw new IOException("File cannot be deleted!");
        }
    }

    @Override
    public String read(String path, String filename) throws FileNotFoundException {
        Scanner myReader = new Scanner(new File(path + filename));
        StringBuilder sb = new StringBuilder();
        while (myReader.hasNextLine()) {
            sb.append(myReader.nextLine());
        }
        return sb.toString();
    }

}
