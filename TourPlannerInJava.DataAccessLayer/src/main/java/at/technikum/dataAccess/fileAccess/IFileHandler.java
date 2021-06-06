package at.technikum.dataAccess.fileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public interface IFileHandler {
    public void save(String path,String filename, String content) throws IOException;
    public void saveNewFile(String path, String filename, String content) throws IOException;
    public void delete(String path, String filename) throws IOException;
    public String read(String path, String filename) throws FileNotFoundException;
}
