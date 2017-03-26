import java.io.*;
import java.util.*;

public class Priority {
    int level;
    int pValue;

    Priority(String level,String pValue) {
        this.level = Integer.parseInt(level);
        this.pValue = Integer.parseInt(pValue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Level: "+level);
        sb.append(" pValue: "+pValue);
        return sb.toString();
    }
}
