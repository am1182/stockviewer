package stockviewer1320342;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/*
*
* ================= STUDENT NUMBER: 1320342
*
*/

public class CSV {
    
    // ---------- READ FILE ----------
    // https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
    public static List readFile(String filePath) {
        String csvFile = filePath;
        String line = "";
        ArrayList<String> contentList = new ArrayList<String>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] content = line.split(",");
                for (int i=0; i<content.length; i++) {
                    contentList.add(content[i]);
                }
            }
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage().toString());
        }
        return contentList;
    }
    
    // ---------- GET FILEPATHS ----------
    // https://coderanch.com/t/544063/java/Finding-CSV-files-directory
    public static List getFilePaths(String directoryPath) { 
        List filePathList = new ArrayList();
        try {
            File[] files = new File(directoryPath).listFiles();
            for(File f:files) {
                String filePath = f.getAbsolutePath();
                filePathList.add(filePath);
            }
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return filePathList;
    }
}
