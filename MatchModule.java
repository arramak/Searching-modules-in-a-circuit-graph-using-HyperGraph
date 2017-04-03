import java.io.*;
import java.util.*;

public class MatchModule {

    public boolean isMatch(GenerateGraph givenCkt,GenerateGraph libCkt) {
        Map<String,CompCount> givenCkt_netList = new HashMap<>();
        Map<String,CompCount> libCkt_netList = new HashMap<>();

        for(Map.Entry<String,List<String>> entry : givenCkt.netListMap.entrySet()) {
            givenCkt_netList.put(entry.getKey(),new CompCount());
            for(String str : entry.getValue()) {
                if(str.contains("source")) {
                    givenCkt_netList.get(entry.getKey()).source++;
                }
                if(str.contains("drain")) {
                    givenCkt_netList.get(entry.getKey()).drain++;
                }
                if(str.contains("gate")) {
                    givenCkt_netList.get(entry.getKey()).gate++;
                }
                if(str.contains("body")) {
                    givenCkt_netList.get(entry.getKey()).body++;
                }
            }
        }

        for(Map.Entry<String,List<String>> entry : libCkt.netListMap.entrySet()) {
            libCkt_netList.put(entry.getKey(),new CompCount());
            for(String str : entry.getValue()) {
                if(str.contains("source")) {
                    libCkt_netList.get(entry.getKey()).source++;
                }
                if(str.contains("drain")) {
                    libCkt_netList.get(entry.getKey()).drain++;
                }
                if(str.contains("gate")) {
                    libCkt_netList.get(entry.getKey()).gate++;
                }
                if(str.contains("body")) {
                    libCkt_netList.get(entry.getKey()).body++;
                }
            }
        }

        boolean flag = false;

        for(Map.Entry<String,CompCount> entry : givenCkt_netList.entrySet()) {
            if(libCkt_netList.containsKey(entry.getKey())) {
                if(entry.getValue().source==libCkt_netList.get(entry.getKey()).source) {
                    flag = true;
                }
                else {
                    flag = false;
                    return flag;
                }

                if(entry.getValue().drain==libCkt_netList.get(entry.getKey()).drain) {
                    flag = true;
                }
                else {
                    flag = false;
                    return flag;
                }

                if(entry.getValue().gate==libCkt_netList.get(entry.getKey()).gate) {
                    flag = true;
                }
                else {
                    flag = false;
                    return flag;
                }
            }
        }    

        return flag;    
    }

    public Map<Priority,Boolean> matching(GenerateGraph givenCkt,Map<Priority,GenerateGraph> library) {
        Map<Priority,Boolean> map = new HashMap<>();
        for(Map.Entry<Priority,GenerateGraph> entry : library.entrySet()) {
            map.put(entry.getKey(),isMatch(givenCkt,entry.getValue()));
        }
        return map;
    }

    public static boolean isMatchLib(GenerateGraph givenCkt,GenerateGraph libCkt) {
        Map<String,List<Boolean>> givenCktBool = new HashMap<>();
        Map<String,List<Boolean>> libCktBool = new HashMap<>();

        for(Component comp : givenCkt.components) {
            givenCktBool.put(comp.name,new ArrayList<Boolean>());
            givenCktBool.get(comp.name).add(false);
            givenCktBool.get(comp.name).add(false);
            givenCktBool.get(comp.name).add(false);
            givenCktBool.get(comp.name).add(false);
        }

        for(Component comp : libCkt.components) {
            libCktBool.put(comp.name,new ArrayList<Boolean>());
            libCktBool.get(comp.name).add(false);
            libCktBool.get(comp.name).add(false);
            libCktBool.get(comp.name).add(false);
            libCktBool.get(comp.name).add(false);
        }

        int buff = libCkt.components.size();
        int i=0;
        while(i<givenCkt.components.size()) {
            if(i+buff>libCkt.components.size()) {
                break;
            }
            int count = buff;
            for(int j=i;j+buff<givenCkt.components.size();j++) {
                if(count==0) {
                    break;
                }
                String net = givenCkt.components.get(j).source;
                List<String> netCons = givenCkt.netListMap.get(net);
                List<String> libNetCons = libCkt.netListMap.get(net);
                if(netCons.get(0).contains(".source")) {
                    givenCktBool.get(givenCkt.components.get(j).name).set(0,true);
                    libCktBool.get(libCkt.components.get(j).name).set(0,true);
                    j++;
                }
                String trans_ckt = netCons.get(1).split("\\.")[1];
                String trans_lib = libNetCons.get(1).split("\\.")[1];
                if(trans_ckt.equals(trans_lib)) {
                    givenCktBool.get(givenCkt.components.get(j).name).set(1,true);
                    libCktBool.get(libCkt.components.get(j).name).set(1,true);
                    j++;
                }
                count--;
            }
            i++;
        }
        return true;
    }

    public static Map<Priority,Boolean> matchingLib(GenerateGraph givenCkt,Map<Priority,GenerateGraph> library) {
        Map<Priority,Boolean> map = new HashMap<>();
        for(Map.Entry<Priority,GenerateGraph> entry : library.entrySet()) {
            map.put(entry.getKey(),isMatchLib(givenCkt,entry.getValue()));
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        GenerateGraph givenCkt = new GenerateGraph("finalckt.ckt");
        CreateLibrary cl = new CreateLibrary();
        Map<Priority,Boolean> match = MatchModule.matchingLib(givenCkt,cl.library);
        //System.out.println(cl.libFileName);
        for(Map.Entry<Priority,String> entry : cl.libFileName.entrySet()) {
            System.out.println(entry.getKey().level+" "+entry.getKey().pValue+" "+entry.getValue().split("/")[1].split("\\.")[0]);
        }

        System.out.println();
        System.out.println("Sizing Rules: ");
        System.out.println();

        for(Map.Entry<Priority,String> entry : cl.libFileName.entrySet()) {
            System.out.print(SizingRule.sizing(entry.getValue().split("/")[1].split("\\.")[0]));
        }

    }

}

class CompCount {
    int source;
    int drain;
    int gate;
    int body;

    CompCount() {
        source=0;
        drain=0;
        gate=0;
        body=0;
    }
}
