import java.io.*;
import java.util.*;

public class SizingRule {

    public static String sizing(String circuitName) {
        StringBuilder sb = new StringBuilder();
        if(circuitName.equals("Voltage-controlled Current Source")) {
            sb.append("voltage-controlled current source\n");
            sb.append("sigid^2/iid=A/WL+sigW^2/W^2+sigW^2/L^2+4/(Vgs-Vth)+Ak/W.L\n");
            sb.append("FE1 : vds-(vgs-Vth)>=VSATmin\n");
            sb.append("FE2 : vds>=0\n");
            sb.append("FE3 : vgs-Vth >= 0\n");
            sb.append("RG1 : w·l >= AminSAT\n");
            sb.append("RG2 : w >= WminSAT\n");
            sb.append("RG3 : l>=LminSAT.\n");
        }
        else if(circuitName.equals("Voltage-Controlled Resistor")) {
            sb.append("Voltage-Controlled Resistor\n");
            sb.append("FE1 : (vgs-Vth)-vds >= Vlinmin\n");
            sb.append("FE2 : vds>=0\n");
            sb.append("FE3 : vgs-Vth >= 0\n");
        }
        else if(circuitName.equals("Simple Current Mirror")) {
            sb.append("Voltage-Controlled Resistor\n");
            sb.append("FG : l1=l2\n");
            sb.append("FE : |vds2− vds1| <= delta Vdsmax\n");
            sb.append("RE : vgs1,2-Vth 1,2 >= Vgsmin\n");
        }
        else if(circuitName.equals("Cascode_Current_Mirror")) {
            sb.append("Cascode Current Mirror\n");
            sb.append("FG1 : w1ls=w1cm\n");
            sb.append("FG2 : w2ls=w2cm\n");
        }
        else if(circuitName.equals("Level_Shifter")) {
            sb.append("Level Shifter\n");
            sb.append("FG : l1=l2\n");
            sb.append("RE : Vgs(1,2)-Vth(1,2)>=Vgsmin\n");
        }
        else if(circuitName.equals("4-Transistor_Current_Model")) {
            sb.append("4 Transistor Current Mirror\n");
            sb.append("FG1 : wvrl=wvrl\n");
            sb.append("FG2 : wcm=wcml\n");
            sb.append("FE : |Vds_vrl(1,2)-Vds_dscml|<=Vdsmax\n");
        }
        else if(circuitName.equals("Differential_Pair")) {
            sb.append("\nDifferential Pair\n");
            sb.append("FG1: l1=l2\n");
            sb.append("FG2: w1=w2\n");
            sb.append("FE: |Vds2-Vdsmin|<delVdsmax\n");
            sb.append("RE: |Vgs2-Vgs1|<delVgsmax\n");
        }

        return sb.toString();
    }
}
