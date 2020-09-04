import java.io.*;
import java.util.Scanner;
public class project2{
    public static int pc=0,sp=0,ct=0,cy=0,bp=0,temp=0,ac=0,ar=0,carry=0;
    public static void main(final String[] args)throws IOException{
        Binary_code("source");
    }


    // Phase III: Binary Machine code
    public static void Binary_code(final String Filename)throws IOException{
        String str_temp = ""; //อ่านข้อมูลในไฟล์
        String str_file = ""; //เก็บข้อมูลในไฟล์
        try {
            final BufferedReader br = new BufferedReader(new FileReader(Filename+".lmc")); //อ่านไฟล์ที่เราต้องการ
            while (str_temp != null) //ถ้ามีข้อมูลให้เข้าลูป
            {
                if (str_temp == null) break; //ถ้าไม่มีขอมูลให้ออกลูป
                str_file += str_temp;
                str_temp = br.readLine();
                
            }
            br.close();
        } catch (final Exception e) {}

        //******************************* แยกข้อมูลจากไฟล์มาเก็บในอาเรย์ *************************/
        final String data_array[] = str_file.split(""); // แยกทุกอักขระมาเก็บไว้ในอาเรย์
        final String data_file[] = new String[data_array.length/16]; // เก็บข้อมูลฐาน 2
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
        final int Two2ten[] = new int[data_array.length/16];
        for(int i=0; i<data_array.length/16; i++){
            final String Two2ten_temp[] = data_file[i].split("");
            for(int j=0; j<16; j++){
                Two2ten[i] += (Two2ten_temp[j].equals("1")) ? Math.pow(2,16-(j+1)) : 0;
            }      
        }

    final String Two2ten_temp[] = new String[Two2ten.length]; //สร้างอารเรย์สำหรับแปลงเป็น String
    for(int i=0; i<Two2ten.length; i++){
        Two2ten_temp[i] = String.valueOf(Two2ten[i]);
        Two2ten_temp[i] = (Two2ten_temp[i].length() == 1) ? "000" + Two2ten_temp[i]
                : (Two2ten_temp[i].length() == 2) ? "00" + Two2ten_temp[i]
                        : (Two2ten_temp[i].length() == 3) ? "0" + Two2ten_temp[i] : Two2ten_temp[i];
        // System.out.println(Two2ten_str[i]);
    }
    final int mem[] = new int[Two2ten_temp.length];
    for (int i = 0; i < Two2ten_temp.length; i++){
        mem[i] = Integer.parseInt(Two2ten_temp[i]);
        System.out.println(mem[i]);
    }
        
    emulate(Filename, mem);
    }


    public static void emulate(final String Filename, final int[] mem){
        final Scanner sc = new Scanner(System.in);
        // int pc=0,sp=0,ct=0,cy=0,bp=0,temp=0,temp1=0,temp2=0;
        System.out.println("Program counter register(PC): "+pc+"\nstack pointer register(SP): "+sp+"\nCount register(CT): "+ct+
        "\nCarry register(CY): "+cy+"\nBase pointer register(BP): "+bp+"\na work register within the CPU: "+temp+
        "\na work register within the CPU: "+ac+"\na work register within the CPU: "+ar);
        
        // System.out.println("0. p      1. pc     2. pr     3. cora   4. asp    5. call    6. ja     7. jct     8. jp      9. jn"+
        // "\n10. jz    11. jnz   12. jodd  13. jzon  14. jzop  15. ret    16. add   17. sub    18. stav   19. stva"+
        // "\n20. load  21. awc   22. pwc   23. dupe  24. esba  25. reba   26. zsp   27. cmps   28. cmpu   29. rev"+
        // "\n30. shll  31. shrl  32. shra  33. neg   34. mult  35. div    36. rem   37. addy   38. or     39. xor"+
        // "\n40. and   41. flip  42. cali  43. sct   44. rot   45. psp    46. bpbp  47. pobp   48. pbp    49. bcpy"+
        // "\n50. uout  51. sin   52. sout  53. hin   54. hout  55. ain    56. aout  57. din    58. dout   59. noop"+
        // "\n60. halt");
        final byte order  = sc.nextByte();
        // String order = sc.next();
        int num = 0;
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
            ac = mem[sp++];
            ar = mem[sp];
            mem[sp--] = ac;
            mem[sp] = ar;
        }
        // neg
        else if (order == 33) {
            mem[sp] = -mem[sp];
        }
        // mult
        else if (order == 34) {
            temp = mem[sp++];
            mem[sp] = mem[sp] * temp;
        }
        // div
        else if (order == 35) {
            ac = mem[sp++];
            ar = sp;
            if (ac == 0)
                ct = -1;
            else
                mem[sp] = mem[sp] / temp;
        }
        // rem
        else if (order == 36) {
            temp = mem[sp++];
            if (ac == 0)
                ct = -1;
            else
                mem[sp] = mem[sp] % temp;
        }
        // addy
        else if (order == 37) {
            temp = mem[sp++];
            mem[sp] = mem[sp] + temp + carry;
            cy = carry;
        }
        // or
        else if (order == 38) {
            mem[sp] = mem[sp++] | mem[sp];
        }
        // xor
        else if (order == 39) {
            mem[sp] = mem[sp++] ^ mem[sp];
        }
        // and
        else if (order == 40) {
            mem[sp] = mem[sp++] & mem[sp];
        }
        // flip
        else if (order == 41) {
            mem[sp] = ~mem[sp];
        }
        // cali
        else if (order == 42) {
            temp = mem[sp];
            mem[sp] = pc;
            pc = ar;
        }
        // sct
        else if(order == 43){
            
        }
    } 

    public static void order(final int num){
        if(num == 0){

        }
        
    } 
}