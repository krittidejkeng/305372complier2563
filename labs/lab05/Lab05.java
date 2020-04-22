import java.io.*;

public class Lab05{
    public static void main(String[] args)throws IOException{
        Binary_code("Source");
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
        String Two2ten_str[] = new String[Two2ten.length]; //สร้างอารเรย์สำหรับแปลงเป็น String
        for(int i=0; i<Two2ten.length; i++){
            Two2ten_str[i] = String.valueOf(Two2ten[i]);
            Two2ten_str[i] = (Two2ten_str[i].length() == 1) ? "000"+Two2ten_str[i] 
            : (Two2ten_str[i].length() == 2) ? "00"+Two2ten_str[i]
            : (Two2ten_str[i].length() == 3) ? "0"+Two2ten_str[i]
            : Two2ten_str[i];
            System.out.println(Two2ten_str[i]);
        }
        Verify_code(Filename,Two2ten_str);     
    }


    // Phase II: Machine code verifier
    public static void Verify_code(String Filename,String[] Value)throws IOException{
        int count_Invalid = 0;
        for(int i=0; i<Value.length; i++){
            String value_check[] = Value[i].split("");
            if((value_check[0].equals("0") || value_check[0].equals("9")) && (!Value[i].equals("9001") 
            && !Value[i].equals("9002") && !Value[i].equals("0000"))){
                System.out.println("Line "+(i+1)+": Invalid machine code "+Value[i]);
                count_Invalid = 1;        
            }
        }
        if(count_Invalid == 0) String_code(Filename,Value); 
        
    }

    // Phase I: Machine code as a string
    public static void String_code(String Filename,String[] Value)throws IOException{
        String keep_writer;
        BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_Output.asm"));
        for(int i=0; i<Value.length; i++){
            String value_temp[] = Value[i].split("");
            keep_writer = (Value[i].equals("0000")) ? "STOP\n" 
            : (Value[i].equals("9001")) ? "READ\n"
            : (Value[i].equals("9002")) ? "PRINT\n"

            //เช็คเงื่อนไขว่า value_temp[0].equals("1")
            : value_temp[0].equals("1") ? "ADD\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("1")

            //เช็คเงื่อนไขว่า value_temp[0].equals("2")
            : (value_temp[0].equals("2")) ? "SUB\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("2")    
            
            //เช็คเงื่อนไขว่า value_temp[0].equals("3")
            : (value_temp[0].equals("3")) ?  "STO\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("3")
            
            //เช็คเงื่อนไขว่า value_temp[0].equals("4")
            : (value_temp[0].equals("4")) ? "STA\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("4")
            
            //เช็คเงื่อนไขว่า value_temp[0].equals("5")
            : (value_temp[0].equals("5")) ? "LOAD\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("5")
            
            //เช็คเงื่อนไขว่า value_temp[0].equals("6")
            : (value_temp[0].equals("6")) ? "B\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("6")
            
            //เช็คเงื่อนไขว่า value_temp[0].equals("7")
            : (value_temp[0].equals("7")) ? "BZ\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n")
            //เช็คเงื่อนไขว่า value_temp[0].equals("7")
            
            //เช็คเงื่อนไขว่า value_temp[0].equals("8")
            : (value_temp[0].equals("8")) ? "BP\t\t"+(value_temp[2].equals("0") 
            ? Integer.parseInt(value_temp[3])+"\n" 
            : Integer.parseInt(value_temp[2]) + Integer.parseInt(value_temp[3])+"\n") : "";
            //เช็คเงื่อนไขว่า value_temp[0].equals("8")

            writer.write(keep_writer);
        }
        writer.close();   
    }
}