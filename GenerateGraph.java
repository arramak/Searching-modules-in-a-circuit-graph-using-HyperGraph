import java.util.regex.*;
import java.util.*;
import java.io.*;

public class GenerateGraph {
    List<Component> components;
    List<String> netList;
    Map<String,List<String>> netListMap;
    Map<String,List<String>> componentMap;

    GenerateGraph(String fileName) throws IOException {
        components = ParseComponents.parseNetlist(fileName);
        Collections.sort(components,new Comparator<Component>() {
            public int compare(Component c1,Component c2) {
                return c1.name.compareTo(c2.name);
            }
        });
        this.standardCompNameList(components);

        netList = createNetList(components);
        Collections.sort(netList);
        
        netListMap = generateNetListMap(components,netList);
        componentMap = generateComponentMap(components,netList);
    }

    public List<String> createNetList(List<Component> components) {
        Set<String> nets = new HashSet<>();

        for(Component comp : components) {
            
            if(isNet(comp.source) && !nets.contains(comp.source)) {
                nets.add(comp.source);
            }
            if(isNet(comp.drain) && !nets.contains(comp.drain)) {
                nets.add(comp.drain);
            }
            if(isNet(comp.gate) && !nets.contains(comp.gate)) {
                nets.add(comp.source);
            }
        }

        List<String> netList = new ArrayList<>();
        netList.addAll(nets);
        netList.add("vin");
        netList.add("vout");
        netList.add("0");
        netList.add("vdd");

        return netList;
    }

    public Map<String,List<String>> generateNetListMap(List<Component> components,List<String> netList) {
        String[] terminals = {".source",".drain",".gate",".body"};
        Map<String,List<String>> map = new HashMap<>();

        for(String net : netList) {
            if(!map.containsKey(net)) {
                map.put(net,new ArrayList<String>());
            }
            for(Component component : components) {
                if(component.source.equals(net)) {
                    map.get(net).add(component.name+terminals[0]);
                }
                if(component.drain.equals(net)) {
                    map.get(net).add(component.name+terminals[1]);
                }
                if(component.gate.equals(net)) {
                    map.get(net).add(component.name+terminals[2]);
                }
                if(component.body.equals(net)) {
                    map.get(net).add(component.name+terminals[3]);
                }
            }
        }

        return map;
    }

    public Map<String,List<String>> generateComponentMap(List<Component> components,List<String> netList) {
        Map<String,List<String>> map = new HashMap<>();

        for(Component comp : components) {
            if(!map.containsKey(comp.name)) {
                map.put(comp.name,new ArrayList<String>());
            }
            Set<String> set = new HashSet<>();
            set.add(comp.source);
            set.add(comp.drain);
            set.add(comp.gate);
            set.add(comp.body);

            map.get(comp.name).addAll(set);
        }

        return map;
    }

    public boolean isNet(String str) {
        Pattern p = Pattern.compile("[n]");
        Matcher m = p.matcher(String.valueOf(str.charAt(0)));
        return m.matches();
    }

    public void standardCompNameList(List<Component> components) { // Standardise the names of the components given from any circuit
        for(int i=0;i<components.size();i++) {
            components.get(i).name=components.get(i).name.substring(0,1)+i;
        }
    }
   


    // public static void main(String[] args) throws IOException {
    //     List<Component> components = ParseComponents.parseNetlist("finalckt.ckt");

    //     List<String> netList = createNetList(components);
    //     Map<String,List<String>> netListMap = generateNetListMap(components,netList);
    //     Map<String,List<String>> componentMap = generateComponentMap(components,netList);

    //     System.out.println(netList);
    //     System.out.println(netListMap);
    //     System.out.println(componentMap);

    // }

}