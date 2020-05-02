import java.io.*;
import java.util.Scanner;
public class Lab06 {
    public static String Value_AC = "0000";
    public static String[] Value_array = new String[100];
    public static int count = 0; //เช็คว่าไฟล์ถูกหรือไม่
    public static String name;
    public static String Value_file[] = new String[103];
    public static void main(String args[])throws IOException {
        System.out.println("\n\n***********************************************************"
        +"\n*** Welcome to an Extended Little Man Computer Emulator! ***\n?>");
        Scanner scan = new Scanner(System.in);
        String filename = "";
        while(true){
            System.out.print("* * * * * * * * * * * * *\n* quit\t\tdump    *\n* load\t\trun     *\n* restore\treset   *");
            System.out.println("\n* * * * * * * * * * * * *");
            String choose = scan.next();
            if(choose.equals("quit")) break;
            else if(choose.equals("dump")) ex();
            else if(choose.equals("load")) {
                System.out.print("Please Enter Filename: ");
                name = scan.next();
                dump_1(name,choose);
            } else if(choose.equals("run")) dump_1(name,choose);
            else if(choose.equals("step")) dump_1(name,choose);
            else if(choose.equals("restore")) dump_1(name,choose);
        }
    }

    //Phase III อ่านไฟล์
    public static void dump_1(String Filename,String Choose)throws IOException{
        String str_temp = ""; //อ่านข้อมูลในไฟล์
        String str_file = ""; //เก็บข้อมูลในไฟล์
        try {
             BufferedReader br = new BufferedReader(new FileReader(Filename+".lmc")); //อ่านไฟล์ที่เราต้องการ
             while (str_temp != null) //ถ้ามีข้อมูลให้เข้าลูป
             {
                if (str_temp == null) break;
                 //ถ้าไม่มีขอมูลให้ออกลูป
                str_file += str_temp;            
                str_temp = br.readLine();
            } br.close();
        } catch (Exception e) {}
        int count = 0, count_index = 0;
        String temp[] = new String[str_file.length()/4];
        String value[] = str_file.split("");
        String str = "";
        for(int i=0; i<str_file.length(); i++){
            str += value[i];
            count++;
            if(count%4 == 0){
                temp[count_index] = str;
                count_index++;
                str = "";
            }
        } Verify_code(Filename,temp,Choose);
    }

    //ตรวจสอบคำผิด
    public static void Verify_code(String Filename,String[] Value,String Choose)throws IOException{
        boolean count_Invalid = true;
        for(int i=0; i<Value.length; i++){
            String value_check[] = Value[i].split("");
            if((value_check[0].equals("0") || value_check[0].equals("9")) && (!Value[i].equals("9001") 
            && !Value[i].equals("9002") && !Value[i].equals("0000"))){
                System.out.println("Line "+(i+1)+": Invalid machine code "+Value[i]);
                count_Invalid = false;      
            }
        }
        if(count_Invalid == false) main(null);
        else if(count_Invalid == true && Choose.equals("load")) {
            ex_1(Value,"0000","0000","0000");
            main(null);
        } else if(count_Invalid == true && Choose.equals("run")) run(Value,Value.length,Choose);
        else if(count_Invalid == true && Choose.equals("step")) run(Value,Value.length,Choose);
        else if(count_Invalid == true && Choose.equals("restore")) restore(Value,Choose);
    }

    public static void ex_1(String[] Value,String Ac,String PC,String IR){
        String[] temp = new String[100];
        int count_value = 0, count = 0;
        System.out.println("\n\n***********************************************************"
        +"\n*** Welcome to an Extended Little Man Computer Emulator! ***\n");
        System.out.println("REGISTERS:\nAccumulator (AC): "+Ac+"\n"+"Program Counter (PC): "+PC+
        "\n"+"Instruction Register (IR): "+IR+"\n\n"+"MEMORY:");
        System.out.println("\t   0\t   1\t   2\t   3\t   4\t   5\t   6\t   7\t   8\t   9");
        for(int i=0; i<temp.length; i++){
            if(count_value < Value.length){
                temp[i] = Value[count_value];
                count_value++;
            } else temp[i] = "0000";
        }
        for(int i=0; i<temp.length; i++){
            ++count;
            if(i%10 == 0) System.out.print(i);  
                System.out.print("\t"+temp[i]); 
            if(count%10 == 0) System.out.println();
        }
    }    

    public static void restore(String[] Value,String Choose)throws IOException{
        Scanner scan = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new FileReader("_Output.asm"));
        Value_file = br.readLine().split("/");
        int number = Integer.parseInt(Value_file[0]) , array = 4;
        String value_AC = Value_file[1], count_PC = Value_file[2], value_IR = Value_file[3];
        String[] temp = new String[100];
        String[] value_temp = new String[Value.length];
        for(int i=0; i<temp.length; i++){
            temp[i] = Value_file[array];
            array++;
        }  
        for(int i=number; i<temp.length; i++){//จำกัดในวนลูปเท่ากับจำนวนไฟล์
            value_temp = temp[i].split("");//แก้เป็น count_ary  
            value_IR = temp[i];
            count_PC = (String.valueOf(i + 1));
            count_PC = (count_PC.length() == 1) ? "000" + count_PC
            : (count_PC.length() == 2) ? "00" + count_PC 
            : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
            if(temp[i].equals("0000")){
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                break;
            } else if(temp[i].equals("9001")){
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                System.out.print("Please Enter Value: ");
                value_AC = scan.next();
                Check_AC(value_AC);  
                System.out.print("Save? (y/n): ");
                if(scan.next().equals("y")){
                    BufferedWriter writer = new BufferedWriter(new FileWriter("_Output.asm"));
                    writer.write(i+"/"+value_AC+"/"+count_PC+"/"+value_IR+"/");
                    for(int j=0; j<temp.length; j++)
                        writer.write(temp[j]+"/");
                    writer.close();
                } System.out.print("quit? (y/n): ");
                if(scan.next().equals("y")) break;
            } 
            else if(temp[i].equals("9002")){
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                System.out.println("OUTPUT : "+Value_AC);
            } else if(value_temp[0].equals("1")){ //นำค่าตำแหน่งที่ -xxx มาบวกกับ AC
                value_AC = String.valueOf(Integer.parseInt(value_AC) 
                + Integer.parseInt(temp[Integer.parseInt(temp[i])-1000]));
                Check_AC(value_AC);
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("2")){
                value_AC = String.valueOf(Integer.parseInt(value_AC) 
                - Integer.parseInt(temp[Integer.parseInt(temp[i])-2000]));
                Check_AC(value_AC);
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("3")){//นำค่า AC เก็บในตำแหน่ง xxx ของ mailbox
                temp[Integer.parseInt(temp[i])-3000] = value_AC;
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("4")){
                String split_AC[] = value_AC.split(""); 
                temp[Integer.parseInt(temp[i])-4000] = split_AC[2]+split_AC[3];
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("5")){
                Value_AC = temp[Integer.parseInt(temp[i])-5000];
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("6")){
                count_PC = String.valueOf(Integer.parseInt(temp[i])-6000);
                count_PC = (count_PC.length() == 1) ? "000" + count_PC
                : (count_PC.length() == 2) ? "00" + count_PC : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                i = Integer.parseInt(count_PC)-1;
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("7") && Integer.parseInt(value_AC) == 0){
                count_PC = String.valueOf(Integer.parseInt(temp[i])-7000);
                count_PC = (count_PC.length() == 1) ? "000" + count_PC
                : (count_PC.length() == 2) ? "00" + count_PC : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                i = Integer.parseInt(count_PC)-1;
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            } else if(value_temp[0].equals("8") && Integer.parseInt(value_AC) >= 0){
                count_PC = String.valueOf(Integer.parseInt(temp[i])-8000);
                count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                i = Integer.parseInt(count_PC)-1;
                ex_1(check_array(temp), Value_AC, count_PC, value_IR);
            }
        }
    }


    public static void run(String[] Value,int Length_val,String Choose)throws IOException{
        Scanner scan = new Scanner(System.in);
        String value_AC = "0000", count_PC = "", value_IR = "";
        System.out.println("step: "+Value.length);
        String[] temp = new String[100];
        String[] value_temp = new String[Value.length];
        for(int i=0; i<temp.length; i++){
            if(i< Value.length) temp[i] = Value[i];
            else temp[i] = "0000";
        }
        if(Choose.equals("run")){  
            for(int i=0; i<temp.length; i++){//จำกัดในวนลูปเท่ากับจำนวนไฟล์
                value_temp = temp[i].split("");//แก้เป็น count_ary  
                value_IR = temp[i];
                count_PC = (String.valueOf(i + 1));
                count_PC = (count_PC.length() == 1) ? "000" + count_PC
                : (count_PC.length() == 2) ? "00" + count_PC 
                : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                if(temp[i].equals("0000")){
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                    break;
                } else if(temp[i].equals("9001")){
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                    System.out.print("Please Enter Value: ");
                    value_AC = scan.next();
                    Check_AC(value_AC); 
                    System.out.print("Save? (y/n): ");
                    if(scan.next().equals("y")){
                        BufferedWriter writer = new BufferedWriter(new FileWriter("_Output.asm"));
                        writer.write(i+"/"+value_AC+"/"+count_PC+"/"+value_IR+"/");
                    for(int j=0; j<temp.length; j++)
                        writer.write(temp[j]+"/");
                    writer.close();
                    }
                    System.out.print("quit? (y/n): ");
                    if(scan.next().equals("y")) break;
                } else if(temp[i].equals("9002")){
                     ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                    System.out.println("OUTPUT : "+Value_AC);
                } else if(value_temp[0].equals("1")){ //นำค่าตำแหน่งที่ -xxx มาบวกกับ AC
                    value_AC = String.valueOf(Integer.parseInt(value_AC) 
                    + Integer.parseInt(temp[Integer.parseInt(temp[i])-1000]));
                    Check_AC(value_AC);
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("2")){
                    value_AC = String.valueOf(Integer.parseInt(value_AC) 
                    - Integer.parseInt(temp[Integer.parseInt(temp[i])-2000]));
                    Check_AC(value_AC);
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("3")){//นำค่า AC เก็บในตำแหน่ง xxx ของ mailbox
                    temp[Integer.parseInt(temp[i])-3000] = value_AC;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("4")){
                    String split_AC[] = value_AC.split(""); 
                    temp[Integer.parseInt(temp[i])-4000] = split_AC[2]+split_AC[3];
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("5")){
                    Value_AC = temp[Integer.parseInt(temp[i])-5000];
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("6")){
                    count_PC = String.valueOf(Integer.parseInt(temp[i])-6000);
                    count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                    i = Integer.parseInt(count_PC)-1;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("7") && Integer.parseInt(value_AC) == 0){
                    count_PC = String.valueOf(Integer.parseInt(temp[i])-7000);
                    count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                    i = Integer.parseInt(count_PC)-1;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if(value_temp[0].equals("8") && Integer.parseInt(value_AC) >= 0){
                    count_PC = String.valueOf(Integer.parseInt(temp[i])-8000);
                    count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                    i = Integer.parseInt(count_PC)-1;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                }
            }
        }
        else if(Choose.equals("step")){
            for(int i=0; i<temp.length; i++){
                if(i< Value.length) temp[i] = Value[i];
                else temp[i] = "0000";
            }
            int j=0;
            while(j<temp.length){
                value_temp = temp[j].split("");// แก้เป็น count_ary
                value_IR = temp[j];
                count_PC = (String.valueOf(j + 1));
                count_PC = (count_PC.length() == 1) ? "000" + count_PC
                : (count_PC.length() == 2) ? "00" + count_PC
                : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                if (temp[j].equals("0000")) {
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                    break;
                } else if (temp[j].equals("9001")) {
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                    System.out.print("Please Enter Value: ");
                    value_AC = scan.next();
                    Check_AC(value_AC);
                } else if (temp[j].equals("9002")) {
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                    System.out.println("OUTPUT : " + Value_AC);
                } else if (value_temp[0].equals("1")) { // นำค่าตำแหน่งที่ -xxx มาบวกกับ AC
                    value_AC = String.valueOf(Integer.parseInt(value_AC) 
                    + Integer.parseInt(temp[Integer.parseInt(temp[j]) - 1000]));
                    Check_AC(value_AC);
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if (value_temp[0].equals("2")) {
                    value_AC = String.valueOf(Integer.parseInt(value_AC) 
                    - Integer.parseInt(temp[Integer.parseInt(temp[j]) - 2000]));
                    Check_AC(value_AC);
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if (value_temp[0].equals("3")) {// นำค่า AC เก็บในตำแหน่ง xxx ของ mailbox
                    temp[Integer.parseInt(temp[j]) - 3000] = value_AC;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if (value_temp[0].equals("5")) {
                    Value_AC = temp[Integer.parseInt(temp[j]) - 5000];
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if (value_temp[0].equals("6")) {
                    count_PC = String.valueOf(Integer.parseInt(temp[j]) - 6000);
                    count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC
                    : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                    j = Integer.parseInt(count_PC) - 1;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if (value_temp[0].equals("7") && Integer.parseInt(value_AC) == 0) {
                    count_PC = String.valueOf(Integer.parseInt(temp[j]) - 7000);
                    count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC
                    : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                    j = Integer.parseInt(count_PC) - 1;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                } else if (value_temp[0].equals("8") && Integer.parseInt(value_AC) >= 0) {
                    count_PC = String.valueOf(Integer.parseInt(temp[j]) - 8000);
                    count_PC = (count_PC.length() == 1) ? "000" + count_PC
                    : (count_PC.length() == 2) ? "00" + count_PC
                    : (count_PC.length() == 3) ? "0" + count_PC : count_PC;
                    j = Integer.parseInt(count_PC) - 1;
                    ex_1(check_array(temp), Value_AC, count_PC, value_IR);
                 }
                System.out.print("Save? (y/n): ");
                if(scan.next().equals("y")){
                    BufferedWriter writer = new BufferedWriter(new FileWriter("_Output.asm"));
                    writer.write(j+"/"+value_AC+"/"+count_PC+"/"+value_IR+"/");
                    for(int k=0; k<temp.length; k++)
                        writer.write(temp[k]+"/");
                    writer.close();
                }
                System.out.println();
                System.out.print("step? (y/n): ");
                if (scan.next().equals("y")) j++;
                System.out.print("quit? (y/n): ");
                if(scan.next().equals("y")) break;
            }
        }
    }

    public static void Check_AC(String value_AC){
        String check_AC[] = value_AC.split("");
        if(check_AC[0].equals("-")){
            value_AC = value_AC.replace("-","");
            value_AC = (value_AC.length() == 1) ? "-000" + value_AC
            : (value_AC.length() == 2) ? "-00" + value_AC 
            : (value_AC.length() == 3) ? "-0" + value_AC : value_AC;
        } else{
            value_AC = (value_AC.length() == 1) ? "000" + value_AC
            : (value_AC.length() == 2) ? "00" + value_AC 
            : (value_AC.length() == 3) ? "0" + value_AC : value_AC;
        }
        Value_AC = value_AC;
    }

    public static String[] check_array(String[] Value){
        for(int i=0; i<Value.length; i++){
            String check_Value[] = Value[i].split("");
            String str_Value = Value[i];
            if(check_Value[0].equals("-")){
                Value[i] = Value[i].replace("-", "");
                Value[i] = (Value[i].length() == 1) ? "-000" + Value[i]
                        : (Value[i].length() == 2) ? "-00" + Value[i] 
                        : (Value[i].length() == 3) ? "-0" + Value[i] : "-"+Value[i];
            } else {
                Value[i] = (Value[i].length() == 1) ? "000" + Value[i]
                        : (Value[i].length() == 2) ? "00" + Value[i] 
                        : (Value[i].length() == 3) ? "0" + Value[i] : Value[i];
            }
        } return Value;
    }

    //Phase II
    public static void ex(){
        String AC="0000",PC="0000",IR="0000";
        String[] temp = new String[100];
        int count = 0;
        System.out.println("REGISTERS:\nAccumulator (AC): "+AC+"\n"+"Program Counter (PC): "+PC+"\n"
        +"Instruction Register (IR): "+IR+"\n\n"+"MEMORY:\n?>t");
        System.out.println("1.quit\n2.reset\n3.save\n4.restore\n");
        System.out.println("\t   0\t   1\t   2\t   3\t   4\t   5\t   6\t   7\t   8\t   9");
        for(int i=0; i<temp.length; i++){
            temp[i] = "0000";
        }
        for(int i=0; i<temp.length; i++){
            ++count;
            if(i%10 == 0) System.out.print(i);  
                System.out.print("\t"+temp[i]); 
            if(count%10 == 0) System.out.println(); 
        }
    }    
}