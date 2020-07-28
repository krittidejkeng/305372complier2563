import java.io.*;
import java.util.Scanner;

public class comp4_1_1{
    public static void main(String[] args)throws IOException{
        // Scanner scan = new Scanner(System.in);
        // System.out.print("Enter Filename: ");
        // String Filename = scan.next();
        BufferedReader br = new BufferedReader(new FileReader("source"+".cpp"));
        String st;
        int count = 0 ;
        while((st = br.readLine()) != null)
            count++; //นับว่ามีไฟล์เท่าไหร่ทุกครั้งที่ขึ้นบรรทัดใหม่
        String value_file[] = new String[count]; //เก็บข้อมูลในไฟล์ทั้งหมด จากนั้นจึงค่อยมาเก็บแค่ตัวสตริงทีหลัง
        // String number_file[] = new String[count]; //เก็บเฉพาะตัวเลขเท่านั้นในไฟล์เท่านั้น
        String str_temp = ""; //คอยเก็บค่าที่อ่านได้จากไฟล์
        int i = 0; //ทำหน้าที่เป็นตัวหมุนตำแหน่งอินเด็กซ์
        try {
            BufferedReader br_loop = new BufferedReader(new FileReader("source"+".cpp"));
            while(str_temp != null){
                if(str_temp == null) break;
                str_temp = br_loop.readLine();
                value_file[i] = str_temp; //เก็บค่าในไฟล์ทั้งหมดมาเก็บไว้ตามตำแหน่งบรรทัดตามไฟล์
                i++;
            }
            br_loop.close();
        } catch (Exception e) {}
        

        String state = "state_0";
        int n_comment = 0;
        int line_comment = 0;
        int s_line = 0;
        int n=0;
        while(true){
            try {
                String[] kkkk = value_file[n].split(" ");  
                // System.out.println(kkkk[0]);
                if(state.equals("state_0")){
                    if(kkkk[0].equals("//")) {
                        for(int p=1; p<kkkk.length; p++)
                            n_comment += kkkk[p].length();
                        line_comment++;
                    }
                    //เช็คว่า มี souce code กี่บรรทัด
                    else if(value_file[n].length() >= 1 && !value_file[n].equals("")){
                        s_line++;
                        // System.out.println("\n************************\nThis is !kkkk[0].equals("+"): "+value_file[n]);
                    }
                }
                
                n++;
            } catch (Exception e) {
                break;
            }
        }           
        System.out.println("the number of characters in the comment : "+n_comment);
        System.out.println("the number of comment lines : "+line_comment);
        System.out.println("the number of source line : "+s_line);
    }
}