import java.io.*;
public class Lab04{
    public static void main(String args[])throws IOException{
        srcTotxt("Source");
        srcTopy("Source");
        srcToc("Source");
        srcTocpp("Source");
        srcTocs("Source");
    }

    public static void Transpiler(String Number,String Filename,String Type_File){
       Lexical_Analyzer(Number,Filename,Type_File);
    }


    public static void Lexical_Analyzer(String Number,String Filename,String Type_File){
        String[] Temp_Source = Number.split(" ");  
        String[] Temp = new String[Temp_Source.length];
        for(int i=0;i<Temp_Source.length;i++){
            Temp[i] = (Temp_Source[i].matches(".*[a-zA-Z].*") ? Temp_Source[i]  
            : Temp_Source[i].matches(".*[0-9].*")  && !Temp_Source[i].matches(".*[a-z].*") ? Temp_Source[i] 
            : Temp_Source[i].matches(".*[0-9a-zA-z].*") ? Temp_Source[i]
            : Temp_Source[i].equals("+")|| Temp_Source[i].equals("<")|| Temp_Source[i].equals("=") ? Temp_Source[i]
            : Temp_Source[i].equals("}") || Temp_Source[i].equals("(") || Temp_Source[i].equals(";") ? Temp_Source[i] 
            : " is not a valid word.");
        }
        String str;
        for(int j=0; j<Temp.length;j++){
            str = (Temp[j].matches(".*[a-zA-Z].*") ? "(identifier, "+Temp[j]+")" 
            : Temp[j].matches(".*[0-9].*") && !Temp[j].matches(".*[a-z].*") ? "(number, "+Temp[j]+")"
            : Temp[j].equals("+") ? "(plus, "+Temp[j]+")"
            : Temp[j].equals("<") ? "(Less-Than, "+Temp[j]+")"
            : Temp[j].equals("=") ? "(Equal to, "+Temp[j]+")"
            : Temp[j].equals("}") ? "(left, "+"Curly Bracket"+")"
            : Temp[j].equals("(") ? "(left, "+"parenthesis"+")"
            : Temp[j].equals(";") ? "(left, "+"Semicolon"+")"
            : " is not a valid word.");
            System.out.println(str);
            
        }

        for(int k=0; k<Temp.length;k++){
            if(Temp[k].matches(".*[0-9].*") && !Temp[k].matches(".*[a-z].*"))
               Number = Temp[k];
        }
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