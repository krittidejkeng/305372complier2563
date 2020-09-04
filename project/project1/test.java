import java.io.*;
import java.util.Scanner;
public class test{
    public static int pc=0,sp=0,ct=0,cy=0,bp=0,temp=0,temp1=0,temp2=0,carry=0;
    public static void main(String[] args)throws IOException{
        Binary_code("source");
    }


    // Phase III: Binary Machine code
    public static void Binary_code(String Filename)throws IOException{
        String str_temp = ""; //อ่านข้อมูลในไฟล์
        String str_file = ""; //เก็บข้อมูลในไฟล์
        try {
             BufferedReader br = new BufferedReader(new FileReader(Filename+".lmc")); //อ่านไฟล์ที่เราต้องการ
             while (str_temp != null) //ถ้ามีข้อมูลให้เข้าลูป
            {
                if (str_temp == null) break; //ถ้าไม่มีขอมูลให้ออกลูป
                str_file += str_temp;
                str_temp = br.readLine();
                
            }
             br.close();
        } catch (Exception e) {}

        //******************************* แยกข้อมูลจากไฟล์มาเก็บในอาเรย์ *************************/
        String data_array[] = str_file.split(""); // แยกทุกอักขระมาเก็บไว้ในอาเรย์
        String data_file[] = new String[data_array.length/16]; // เก็บข้อมูลฐาน 2
        String data_temp = ""; // สำรองข้อมูลทุกครั้งที่อักขระครบ 16 ตัว
        int count=0,array=0;
        for(int i=0; i<str_file.length(); i++){
            count++;
            data_temp += data_array[i];
            if(count==16){
                data_file[array] = data_temp;
                data_temp = "";
                count = 0;
                array++;
            }
        }
        //แปลงฐาน 2 เป็นฐาน 10
        int Two2ten[] = new int[data_array.length/16];
        for(int i=0; i<data_array.length/16; i++){
            String Two2ten_temp[] = data_file[i].split("");
            for(int j=0; j<16; j++){
                Two2ten[i] += (Two2ten_temp[j].equals("1")) ? Math.pow(2,16-(j+1)) : 0;
            }      
        }

        //แปลง int เป็น String 
        // BufferedWriter wr = new BufferedWriter(new FileWriter(Filename+"_output.lmc"));
        // String Two2ten_str[] = new String[Two2ten.length]; //สร้างอารเรย์สำหรับแปลงเป็น String
        // for(int i=0; i<Two2ten.length; i++){
        //     Two2ten_str[i] = String.valueOf(Two2ten[i]);
        //     Two2ten_str[i] = (Two2ten_str[i].length() == 1) ? "000"+Two2ten_str[i] 
        //     : (Two2ten_str[i].length() == 2) ? "00"+Two2ten_str[i]
        //     : (Two2ten_str[i].length() == 3) ? "0"+Two2ten_str[i]
        //     : Two2ten_str[i];
        //     // System.out.println(Two2ten_str[i]);
        //     wr.write(Two2ten_str[i]+"\n");
        // }
        // wr.close();
    //    String_code(Filename,Two2ten_str);

    String Two2ten_temp[] = new String[Two2ten.length]; //สร้างอารเรย์สำหรับแปลงเป็น String
    for(int i=0; i<Two2ten.length; i++){
        Two2ten_temp[i] = String.valueOf(Two2ten[i]);
        Two2ten_temp[i] = (Two2ten_temp[i].length() == 1) ? "000" + Two2ten_temp[i]
                : (Two2ten_temp[i].length() == 2) ? "00" + Two2ten_temp[i]
                        : (Two2ten_temp[i].length() == 3) ? "0" + Two2ten_temp[i] : Two2ten_temp[i];
        // System.out.println(Two2ten_str[i]);
    }
    int mem[] = new int[Two2ten_temp.length];
    for (int i = 0; i < Two2ten_temp.length; i++){
        mem[i] = Integer.parseInt(Two2ten_temp[i]);
        System.out.println(mem[i]);
    }
        
    emulate(Filename, mem);
    }


    public static void emulate(String Filename, int[] mem){
        Scanner sc = new Scanner(System.in);
        // int pc=0,sp=0,ct=0,cy=0,bp=0,temp=0,temp1=0,temp2=0;
        System.out.println("Program counter register(PC): "+pc+"\nstack pointer register(SP): "+sp+"\nCount register(CT): "+ct+
        "\nCarry register(CY): "+cy+"\nBase pointer register(BP): "+bp+"\na work register within the CPU: "+temp+
        "\na work register within the CPU: "+temp1+"\na work register within the CPU: "+temp2);
        
        System.out.println("0. p      1. pc     2. pr     3. cora   4. asp    5. call    6. ja     7. jct     8. jp      9. jn"+
        "\n10. jz    11. jnz   12. jodd  13. jzon  14. jzop  15. ret    16. add   17. sub    18. stav   19. stva"+
        "\n20. load  21. awc   22. pwc   23. dupe  24. esba  25. reba   26. zsp   27. cmps   28. cmpu   29. rev"+
        "\n30. shll  31. shrl  32. shra  33. neg   34. mult  35. div    36. rem   37. addy   38. or     39. xor"+
        "\n40. and   41. flip  42. cali  43. sct   44. rot   45. psp    46. bpbp  47. pobp   48. pbp    49. bcpy"+
        "\n50. uout  51. sin   52. sout  53. hin   54. hout  55. ain    56. aout  57. din    58. dout   59. noop"+
        "\n60. halt");
        byte order  = sc.nextByte();
        // String order = sc.next();
        int num=0;
        if(order >=0 && order <= 14 || order == 21 || order == 27 || order == 28 || (order >= 30 && order <=32)){
            System.out.println("Please Enter: ");
            num = sc.nextInt();
        }
        // ret
        else if(order == 15){
            pc = mem[sp++];
        }
        // add
        else if(order == 16){
            temp = mem[sp++];
            mem[sp] = mem[sp] + temp;
            cy = carry;
        }
        // sub
        else if(order == 17){
            temp = mem[sp++];
            mem[sp] = mem[sp] - temp;
        }
        // stav
        else if(order == 18){
            temp = mem[sp++];
            mem[sp] = temp;
        }
        // stav
        else if(order == 19){
            temp = mem[sp++];
            mem[temp] = mem[sp++];
        }
        // load
        else if(order == 20){
            while(sp < mem.length){
                mem[sp] = mem[sp];
                sp++;
            }
        }
        // dupe
        else if(order == 23){
            temp = mem[sp];
            mem[--sp] = temp;
        }
        // esba
        else if(order == 24){
            mem[--sp] = bp;
            bp = (sp*12);
        }        
        // reba
        else if(order == 25){
            sp = bp;
            bp = mem[sp++];
        }
        // zsp
        else if(order == 26){
            sp = 0;
        }
        else if(order == 29){
            temp1 = mem[sp++];
            temp2 = mem[sp];
            mem[sp--] = temp1;
            mem[sp] = temp2;
        }
        // neg
        else if(order == 33){
            mem[sp] = -mem[sp];
        }
        // mult
        else if(order == 34){
            temp = mem[sp++];
            mem[sp] = mem[sp] * temp;
        }
        // div
        else if(order == 35){
            temp1 = mem[sp++];
            temp2 = sp;
            if(temp1 == 0) ct = -1;
            else mem[sp] = mem[sp]/temp;
        }
        // rem
        else if(order == 36){
            temp = mem[sp++];
            if(temp1 == 0) ct = -1;
            else mem[sp] = mem[sp]%temp;           
        }
        // addy
        else if(order == 37){
            temp = mem[sp++];
            mem[sp] = mem[sp] + temp + carry;
            cy = carry;
        }
        // or
        else if(order == 38){
            mem[sp] = mem[sp++] | mem[sp];
        }
        // xor
        else if(order == 39){
            mem[sp] = mem[sp++] ^ mem[sp];
        }
        // and
        else if(order == 40){
            mem[sp] = mem[sp++] & mem[sp];
        }
        // flip
        else if(order == 41){
            mem[sp] = ~mem[sp];
        }
        // cali
        else if(order == 42){
            temp = mem[sp];
            mem[sp] = pc;
            pc = temp2;
        }
        // sct
        else if(order == 43){
            
        }
    } 

    public static void order(int num){
        if(num == 0){

        }
        
    } 
    /* method รับคำสั่งและทำตาม */

    // Phase II: Machine code verifier
    // public static void Verify_code(String Filename,String[] Value)throws IOException{
    //     int count_Invalid = 0;
    //     for(int i=0; i<Value.length; i++){
    //         String value_check[] = Value[i].split("");
    //         if((value_check[0].equals("0") || value_check[0].equals("9")) && (!Value[i].equals("9001") 
    //         && !Value[i].equals("9002") && !Value[i].equals("0000"))){
    //             System.out.println("Line "+(i+1)+": Invalid machine code "+Value[i]);
    //             count_Invalid = 1;        
    //         }
    //     }
    //     if(count_Invalid == 0) String_code(Filename,Value); 
    // }

    // Phase I: Machine code as a string
    // public static void String_code(String Filename,String[] Value)throws IOException{
    //     String keep_writer;
    //     BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_Output.asm"));
    //     for(int i=0; i<Value.length; i++){
    //         String value_temp[] = Value[i].split("");
    //         keep_writer = (Value[i].equals("0000")) ? "STOP\n" 
    //         : (Value[i].equals("9001")) ? "READ\n"
    //         : (Value[i].equals("9002")) ? "PRINT\n"

    //         //เช็คเงื่อนไขว่า value_temp[0].equals("1")
    //         : value_temp[0].equals("1") ? "ADD\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("1")

    //         //เช็คเงื่อนไขว่า value_temp[0].equals("2")
    //         : (value_temp[0].equals("2")) ? "SUB\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("2")    
            
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("3")
    //         : (value_temp[0].equals("3")) ?  "STO\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("3")
            
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("4")
    //         : (value_temp[0].equals("4")) ? "STA\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("4")
            
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("5")
    //         : (value_temp[0].equals("5")) ? "LOAD\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("5")
            
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("6")
    //         : (value_temp[0].equals("6")) ? "B\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("6")
            
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("7")
    //         : (value_temp[0].equals("7")) ? "BZ\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("7")
            
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("8")
    //         : (value_temp[0].equals("8")) ? "BP\t\t"+(value_temp[2].equals("0") 
    //         ? Integer.parseInt(value_temp[3])+"\n" 
    //         : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n") : "";
    //         //เช็คเงื่อนไขว่า value_temp[0].equals("8")

    //         writer.write(keep_writer);
    //     }
    //     writer.close();   
    // }
}