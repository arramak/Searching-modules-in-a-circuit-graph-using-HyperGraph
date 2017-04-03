import java.util.regex.*;
import java.util.*;
import java.io.*;

public class CreateLibrary {

    public Map<Priority,GenerateGraph> library;
    public Map<Priority,String> libFileName;

    CreateLibrary() throws IOException {
        libFileName = this.library();
        library = this.createLib(libFileName);
    }

    public Map<Priority,GenerateGraph> createLib(Map<Priority,String> libFileNames) throws IOException {
        
        Map<Priority,GenerateGraph> map = new HashMap<>();

        for(Map.Entry<Priority,String> entry : libFileNames.entrySet()) {
            if(!map.containsKey(entry.getKey())) {
                map.put(entry.getKey(),new GenerateGraph(entry.getValue()));
            }
        }

        return map;
    }

    private Map<Priority,String> library() throws IOException {
        Map<Priority,String> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("priority.txt")));
        String line = br.readLine();
        while(line!=null) {
            String[] parts = line.split(" ");
            map.put(new Priority(parts[0],parts[1]),"lib/"+parts[2]+".ckt");
            //System.out.println(parts[1]);
            line = br.readLine();
        }
        return map;
    }

}
