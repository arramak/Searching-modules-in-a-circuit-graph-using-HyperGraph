import java.util.regex.*;
import java.util.*;
import java.io.*;

class Component {
    String name;
    String source;
    String drain;
    String gate;
    String body;
    String type;
    // double length;
    // double width;

    Component(String name,String source,String drain,String gate,String body,String type) { // ,double length,double width) {
        this.name = name;
        this.source = source;
        this.drain = drain;
        this.gate = gate;
        this.body = body;
        this.type = type;
        // this.length = length;
        // this.width = width;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: "+name);
        sb.append(" Source: "+source);
        sb.append(" Drain: "+drain);
        sb.append(" Gate: "+gate);
        sb.append(" Body: "+body);
        sb.append(" Type: "+type);
        // sb.append(" Length: "+length);
        // sb.append(" Width: "+width);
        return sb.toString();
    }
}

public class ParseComponents {
    public static List<Component> parseNetlist(String fileName) throws IOException {
        FileInputStream fis = null;
        BufferedReader br = null;
        ArrayList<Component> components = new ArrayList<>();
        try {
            fis = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(fis));
            String line = br.readLine();
            while(line!=null) {
                // System.out.println(line);
                if(line.length()==0) {
                    line = br.readLine();
                    continue;
                }
                Pattern p = Pattern.compile("[a-zA-Z]");
                Matcher m = p.matcher(String.valueOf(line.charAt(0)));
                if(!m.matches()) {
                    line = br.readLine();
                    continue;
                }
                String[] parts = line.split("\\s+");
                if(parts.length<8) {
                    line = br.readLine();
                    continue;
                }
                //System.out.println(parts[0]+" "+parts[1]+" "+parts[2]+" "+parts[3]+" "+parts[4]+" "+parts[5]);
                components.add(new Component(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5].substring(0,4))); //,Double.parseDouble(parts[6].split("=")[1]),Double.parseDouble(parts[7].split("=")[1])));
                line = br.readLine();
            }
            // for(Component cmp : components) {
            //     System.out.println(cmp);
            // }
            // System.out.println();
        }
        finally {
            if(fis!=null) {
                fis.close();
            }
            if(br!=null) {
                br.close();
            }
        }
        return components;
    }
}