import java.io.*;
import java.util.Scanner;

public class lab07{
    public static String append_number = ""; //สำหรับต่อตัเลขให้ครบ 4 บิต
    public static void main(String args[])throws IOException{
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Filename: ");
        String Filename = scan.next();
        BufferedReader br = new BufferedReader(new FileReader(Filename+".asm"));
        String st;
        int count = 0;
        while((st = br.readLine()) != null)
            count++; //นับว่ามีไฟล์เท่าไหร่ทุกครั้งที่ขึ้นบรรทัดใหม่
        String value_file[] = new String[count]; //เก็บข้อมูลในไฟล์ทั้งหมด จากนั้นจึงค่อยมาเก็บแค่ตัวสตริงทีหลัง
        String number_file[] = new String[count]; //เก็บเฉพาะตัวเลขเท่านั้นในไฟล์เท่านั้น
        String str_temp = ""; //คอยเก็บค่าที่อ่านได้จากไฟล์
        int i = 0; //ทำหน้าที่เป็นตัวหมุนตำแหน่งอินเด็กซ์
        try {
            BufferedReader br_loop = new BufferedReader(new FileReader(Filename+".asm"));
            while(str_temp != null){
                if(str_temp == null) break;
                str_temp = br_loop.readLine();
                value_file[i] = str_temp; //เก็บค่าในไฟล์ทั้งหมดมาเก็บไว้ตามตำแหน่งบรรทัดตามไฟล์
                i++;
            }
            br_loop.close();
        } catch (Exception e) {}
        for(int j=0; j<value_file.length; j++){ //loop สำหรับแยกตัวเลขจากไฟล์มาเก็บไว้ใน number_file[]
            String check[] = value_file[j].split(" ");
            number_file[j] = (check[0].equals("ADD")) ? check[1] : (check[0].equals("SUB")) ? check[1]
            : (check[0].equals("STO")) ? check[1] : (check[0].equals("STA")) ? check[1]
            : (check[0].equals("LOAD")) ? check[1] : (check[0].equals("B")) ? check[1]
            : (check[0].equals("BZ")) ? check[1] : (check[0].equals("BP")) ? check[1] : (check[0].equals("DAT")) ? check[1] : null;
            value_file[j] = check[0]; //นำ value_file[] เก็บเฉพาะตัวสตริงเท่านั้น
        }
        asm_file(Filename, value_file, number_file);
        //asm_file(ชื่อไฟล์ของเรา, อาเรย์เก็บค่าเฉพาะสตริง , อาเรย์เก็บค่าเฉพาะตัวเลข); 
    }

    //เมธอด แปลง รหัส asm เป็น machine code
    public static void asm_file(String Filename,String[] Value,String[] number_file)throws IOException{
        String value_file[] = new String[Value.length]; //สร้างไว้สำหรับเก็บค่าตัวเลข หรือ machine code
        String value_asm[] = new String[Value.length]; //สร้างไว้สำหรับเก็บค่าสตริง หรือ  asm
        for(int i=0; i<Value.length; i++){
            String display = Value[i].equals("STOP") ? "0000" 
            : Value[i].equals("ADD") ? Check_Symbol(number_file[i])+"1"+append_number 
            : Value[i].equals("SUB") ? Check_Symbol(number_file[i])+"2"+append_number
            : Value[i].equals("STO") ? Check_Symbol(number_file[i])+"3"+append_number
            : Value[i].equals("STA") ? Check_Symbol(number_file[i])+"4"+append_number
            : Value[i].equals("LOAD") ? Check_Symbol(number_file[i])+"5"+append_number
            : Value[i].equals("B") ? Check_Symbol(number_file[i])+"6"+append_number
            : Value[i].equals("BZ") ? Check_Symbol(number_file[i])+"7"+append_number
            : Value[i].equals("BP") ? Check_Symbol(number_file[i])+"8"+append_number
            : Value[i].equals("DAT") ? Check_Symbol(number_file[i])+"0"+append_number
            : Value[i].equals("READ") ? "9001" : Value[i].equals("PRINT") ? "9002" : "";
            value_file[i] = display;
            value_asm[i] = Value[i];
        }
        Task(Value, number_file, value_file,value_asm);
    }

    //เช็คว่าเป็นเครื่องหมายบวกหรือลบ แล้วคืนค่ากลับไปเป็นเครื่องหมายนั้น
    public static String Check_Symbol(String Check_Number){
        String check_AC[] = Check_Number.split("");
        String symbol = check_AC[0].equals("-") ? "-" : "";
        if(check_AC[0].equals("-")){
            Check_Number = Check_Number.replace("-","");
            append_number = (Check_Number.length() == 1) ? "00" + Check_Number
            : (Check_Number.length() == 2) ? "0" + Check_Number : Check_Number;
        } else{
            append_number = (Check_Number.length() == 1) ? "00" + Check_Number
            : (Check_Number.length() == 2) ? "0" + Check_Number : Check_Number;
        }
        return symbol;
    }

    public static void Task(String[] Value,String[] number_file,String[] Value_file,String[] Value_asm){
        int j=0;
        System.out.println("{Label}\t\t\t{Mnemonic {Operand}}\t\t{;Comment}\n.code");
        for(int i=0; i<Value.length; i++){
            String display = Value[i].equals("STOP") ? "QUIT\t\t\tSTOP\t\t\t\t; Label this memory address as QUIT" 
            : Value[i].equals("ADD") ? "\t\t\tADD ONE\t\t\t\t; Adds ACC with the value stored at address ONE"
            : Value[i].equals("SUB") ? "\t\t\tSUB ONE\t\t\t\t; Subtract ACC with the value stored at address ONE"
            : Value[i].equals("STO") ? "\t\t\tSTO ONE\t\t\t\t; Stores the value into ACC at address ONE"
            : Value[i].equals("STA") ? "\t\t\tSTA ONE\t\t\t\t" 
            + "; Stores the address portion of the value ACC (last 2 digits) into the\n"
            + "\t\t\t\t\t\t\t; address portion of the instruction at address ONE"
            : Value[i].equals("LOAD") ? "\t\t\tLOAD ONE\t\t\t\t; Loads the contents at address ONE into the ACC"
            : Value[i].equals("DAT") ? "ONE\t\t\tDAT\t\t\t\t; Label the address as ONE\n"
            +"\t\t\t\t\t\t\t; Store the value "+number_file[j]+" in this memory address"
            : Value[i].equals("READ") ? "\t\t\tREAD"
            : Value[i].equals("PRINT") ? "\t\t\tPRINT"+"\t\t\t\t; print the ACC" : "";
            if(Value[i].equals("B") || Value[i].equals("BZ") || Value[i].equals("BP")){
                System.out.print("LOOP");
                for(j=i; j<Value.length;j++){
                    if(Value.equals("STOP")) break;
                    String loop =  Value[j].equals("ADD") ? "\t\t\tADD ONE\t\t\t\t; Adds ACC with the value stored at address ONE"
                    : Value[j].equals("SUB") ? "\t\t\tSUB ONE\t\t\t\t; Subtract ACC with the value stored at address ONE"
                    : Value[j].equals("STO") ? "\t\t\tSTO ONE\t\t\t\t; Stores the value into ACC at address ONE"
                    : Value[j].equals("STA") ? "\t\t\tSTA ONE\t\t\t\t" 
                    + "; Stores the address portion of the value ACC (last 2 digits) into the\n"
                    + "\t\t\t\t\t\t\t; address portion of the instruction at address ONE"
                    : Value[j].equals("LOAD") ? "\t\t\tLOAD ONE\t\t\t\t; Loads the contents at address ONE into the ACC"
                    : Value[j].equals("B") ? "\t\t\tB LOOP" + "\t\t\t\t; Jump unconditionally to the address labeled "
                    +Check_Comment(Value_file,Value_asm,j)
                    : Value[j].equals("BZ") ? "\t\t\tBZ "+Check_Comment(Value_file,Value_asm,j)
                    +"\t\t\t\t; Label this address as "+"LOOP"+"\n"
                    +"\t\t\t\t\t\t\t; If ACC is 0, jump to the address labeled "+Check_Comment(Value_file,Value_asm,j)
                    : Value[j].equals("BP") ? "\t\t\tBP LOOP"
                    : Value[j].equals("DAT") ? "ONE\t\t\tDAT\t\t\t\t; Label the address as ONE\n"
                    +"\t\t\t\t\t\t\t; Store the value "+number_file[j]+" in this memory address"
                    : Value[j].equals("READ") ? "\t\t\tREAD"
                    : Value[j].equals("PRINT") ? "\t\t\tPRINT\t\t\t\t; Print the content of ACC" 
                    : "QUIT\t\t\tSTOP\t\t\t\t; Label this memory address as QUIT";
                    System.out.println(loop);
                }
                i=j;    
            }
            System.out.println(display);
        }
    }


    public static String Check_Comment(String[] Value_file,String[] Value_asm,int i){
        int index = 0;
        String value = "";
            String temp[] = Value_file[i].split("");
            if(temp[0].equals("6")){
                index = Integer.parseInt(Value_file[i])-6000;
                value = Value_asm[index].equals("B") || Value_asm[index].equals("BZ") 
                || Value_asm[index].equals("BP") ? "LOOP" 
                : Value_asm[index].equals("STOP") ? "QUIT" : Value_asm[index] ;
            }
            if(temp[0].equals("7")){
                index = Integer.parseInt(Value_file[i])-7000;
                value = Value_asm[index].equals("B") || Value_asm[index].equals("BZ") 
                || Value_asm[index].equals("BP") ? "LOOP" 
                :Value_asm[index].equals("STOP") ? "QUIT" : Value_asm[index] ;
            }   
        return value;
    }
}