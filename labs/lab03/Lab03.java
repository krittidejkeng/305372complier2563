import java.io.*;
public class Lab03{
    public static void main(String args[])throws IOException{
        srcTotxt("Source");
        srcTopy("Source");
        srcToc("Source");
        srcTocpp("Source");
        srcTocs("Source");

        // ไฟล์จำนวนเต็มลบ
        // srcTotxt("Testfile/Negative_num");
        // srcTopy("Testfile/Negative_num");
        // srcToc("Testfile/Negative_num");
        // srcTocpp("Testfile/Negative_num");
        // srcTocs("Testfile/Negative_num");

        //ไฟล์ที่เลขเกินมากกว่า Integer
        // srcTotxt("Testfile/Test01_OverRange");
        // srcTopy("Testfile/Test01_OverRange");
        // srcToc("Testfile/Test01_OverRange");
        // srcTocpp("Testfile/Test01_OverRange");
        // srcTocs("Testfile/Test01_OverRange");

        //ไฟล์ที่มีตัวเลขผสมตัวอักษร
        // srcTotxt("Testfile/Num_str");
        // srcTopy("Testfile/Num_str");
        // srcToc("Testfile/Num_str");
        // srcTocpp("Testfile/Num_str");
        // srcTocs("Testfile/Num_str");
    }

    public static void Transpiler(String Number,String Filename,String Type_File){
        Syntax_Analyzer(Number,Filename,Type_File);
    }

    public static void Syntax_Analyzer(String Number,String Filename,String Type_File){
        int count = 0;
        for(int i=0; i < Number.length(); i++){
            char c = Number.charAt(i);
            if(c < '0' || c > '9') {
                System.out.println("FIle is not a NumberFormat");
                break;
            }
            else{
                count++;
                if(count == Number.length()) Semantic_Analyzer(Number,Filename,Type_File);
            }
        }
    }

    public static void Semantic_Analyzer(String Number,String Filename,String Type_File){
        try {
            if(Integer.parseInt(Number)>0) Target_Code(Integer.parseInt(Number),Filename,Type_File);
            else System.out.print("Value is out of the specified range");
        } catch (Exception e) {
            System.out.print("Value is out of the specified range");
        }
    }

    public static void Target_Code(int Number,String Filename,String Type_File)throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File."+Type_File));
        String str;
        str = ((Type_File == "txt") ? Integer.toString(Number) 
        : (Type_File == "py")?"print("+Number+")"
        : (Type_File == "c") ? "#include<stdio.h>\n"+"int main()\n"+"{\n"
        + "\tint value = "+Number+";\n\tprintf(\"%d\", value);\n"+"\treturn 0;"+"\n}"
        : (Type_File == "cpp") ? "#include<iostream>\n"+"using namespace std;\n"+"int main()\n"+"{\n"
        + "\tint value = "+Number+";\n\tcout<<value;\n\treturn 0;\n"+"}" 
        :  "using System;\n\n"+"namespace ConsoleApp1\n{\n"+"\tclass Program\n"
        + "\t{\n\t\tstatic void Main(string[] args)"+"\n\t\t{\n" + "\t\t\tint value = "+Number+";\n"
        + "\t\t\tConsole.WriteLine(value);\n"+"\t\t\tConsole.ReadLine();\n"+"\t\t}\n\t}\n}");
        writer.write(str);
        writer.close();
        System.out.println("Success File: "+Filename+"_File."+Type_File); 
    }

    public static void srcTotxt(String Filename)throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src"));
        Transpiler(br.readLine(),Filename,"txt"); 
    }

    //1.2 srcTopy
    public static void srcTopy(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        Transpiler(br.readLine(),Filename,"py"); 
    }

    //1.3 srcToc
    public static void srcToc(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        Transpiler(br.readLine(),Filename,"c"); 
    }

    //1.4 srcTocpp
    public static void srcTocpp(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        Transpiler(br.readLine(),Filename,"cpp"); 
    }

    //1.5 srcTocs
    public static void srcTocs(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        Transpiler(br.readLine(),Filename,"cs"); 
    }
}