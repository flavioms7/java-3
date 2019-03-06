package challenge;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class CSVReader {

    private Path csvFilePath;
    public Stream csvFile;
    public List<String> columns;
    
    /**
     * 
     * - Retrieve the file's path 
     * 
     * @param csvFileName
     * @throws IOException
     */
    public CSVReader(String csvFileName) throws IOException {

        //Creating path object
        ClassLoader classLoader = getClass().getClassLoader();
        csvFilePath = Paths.get(classLoader.getResource(csvFileName)
                .getFile().replaceFirst("/", ""));

        //Initialize stream object
        this.initStream();

        //Find the colum names
        Optional<String> fLine = csvFile.findFirst();
        fLine.ifPresent(it -> this.columns = Arrays.asList(it.split(",")) );
    }

    /**
     * 
     * - Read the File and start the stream object
     * 
     * @throws IOException
     */
    public void initStream() throws IOException {
    	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        csvFile = Files.lines(csvFilePath, StandardCharsets.UTF_8);
    }
}