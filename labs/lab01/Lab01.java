import java.io.*;
public class Lab01{
  public static void main(String args[])throws IOException{
    srcTotxt("Source");
    srcTopy("Source");
    srcToc("Source");
    srcTocpp("Source");
    srcTocs("Source");
  }
  public static void srcTotxt(String Filename)throws IOException{
     BufferedReader br = new BufferedReader(new FileReader(Filename+".src"));
    BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.txt"));
    String st; 
    while ((st = br.readLine()) != null) 
    writer.write(st);
    writer.close();
  }

  public static void srcTopy(String Filename) throws IOException{
    BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
    BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.py"));
    String st; 
    while ((st = br.readLine()) != null) 
    writer.write("print(\""+st+"\")");
    writer.close();
  }

  public static void srcToc(String Filename) throws IOException{
    BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
    BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.c"));
    String st; 
    writer.write("#include<stdio.h>\n"+"int main()\n"+"{\n");
    while ((st = br.readLine()) != null) 
    writer.write("\tprintf(\""+st+"\");\n"+"\treturn 0;"+"\n}");
    writer.close();
  }

  public static void srcTocpp(String Filename) throws IOException{
    BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
    BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.cpp"));
    String st;  
    writer.write("#include<iostream>\n"+"using namespace std;\n"+"int main()\n"+"{\n");
    while ((st = br.readLine()) != null) 
    writer.write("\tcout<<\""+st+"\";\n\treturn 0;\n"+"}");
    writer.close();
  }

  public static void srcTocs(String Filename) throws IOException{
    BufferedReader br = new BufferedReader(new FileReader(Filename+".src")); 
    BufferedWriter writer = new BufferedWriter(new FileWriter(Filename+"_File.cs"));
    String st;  
    writer.write("using System;\n\n"+"namespace ConsoleApp1\n{\n"+"\tclass Program\n"+
    "\t{\n\t\tstatic void Main(string[] args)"+"\n\t\t{\n");
    while ((st = br.readLine()) != null) 
    writer.write("\t\t\tConsole.WriteLine(\""+st+"\");\n"+"\t\t\tConsole.ReadLine();\n"
    +"\t\t}\n\t}\n}");
    writer.close();
  }
}
