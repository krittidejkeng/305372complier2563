import java.io.*;
public class Lab02{
    public static void main(String args[])throws IOException{
        srcTotxt("Source");
        srcTopy("Source");
        srcToc("Source");
        srcTocpp("Source");
        srcTocs("Source");
    }

    public static void srcTotxt(String Filename)throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src"));
        String st; 
        int number=0;
        while ((st = br.readLine()) != null) 
        try {
                number=Integer.parseInt(st);
                BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.txt"));
                writer.write(st);
                writer.close();        
        } catch (Exception e) {
            System.out.println("Value is out of the specified range");
        }
    }

    public static void srcTopy(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        String st; 
        int number=0;
        while ((st = br.readLine()) != null) 
        try {
                number=Integer.parseInt(st);
                BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.py"));
                writer.write("print("+number+")");
                writer.close();
            
        } catch (Exception e) {
            System.out.println("Value is out of the specified range");
        }
    }

    public static void srcToc(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        String st; 
        int number=0;
        while ((st = br.readLine()) != null) 
        try {
                number=Integer.parseInt(st);
                BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.c"));
                writer.write("#include<stdio.h>\n"+"int main()\n"+"{\n");
                writer.write("\tint value = "+number+";\n\tprintf(\"%d\", value);\n"+"\treturn 0;"+"\n}");
                writer.close();
            
        } catch (Exception e) {
            System.out.println("Value is out of the specified range");
        }      
      }

      public static void srcTocpp(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        String st;  
        int number=0;
        while ((st = br.readLine()) != null) 
        try {
            number=Integer.parseInt(st);
            BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.cpp"));
            writer.write("#include<iostream>\n"+"using namespace std;\n"+"int main()\n"+"{\n");
            writer.write("\tint value = "+number+";\n\tcout<<value;\n\treturn 0;\n"+"}");
            writer.close();           
        } catch (Exception e) {
            System.out.println("Value is out of the specified range");
        }
      }

      public static void srcTocs(String Filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
        String st;
        int number=0;
        while ((st = br.readLine()) != null) 
        try {
            number=Integer.parseInt(st);
            BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.cs"));
            writer.write("using System;\n\n"+"namespace ConsoleApp1\n{\n"+"\tclass Program\n"+
            "\t{\n\t\tstatic void Main(string[] args)"+"\n\t\t{\n");
            writer.write("\t\t\tint value = "+number+";\n");
            writer.write("\t\t\tConsole.WriteLine(value);\n"+"\t\t\tConsole.ReadLine();\n"
            +"\t\t}\n\t}\n}");
            writer.close();
        } catch (Exception e) {
            System.out.println("Value is out of the specified range");
        } 
      }
}