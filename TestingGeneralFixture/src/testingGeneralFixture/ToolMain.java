package testingGeneralFixture;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;

public class ToolMain {
	
	public static void main(String[] args) throws FileNotFoundException
	{
		CompilationUnit compilationUnit=null;
		FileInputStream fileInputStream;
		String filePath="C://Users//noshi//Documents//"
			+ "Comic Life//PersonSmell.java";

	if(filePath!=null) {
			
			fileInputStream = new FileInputStream(filePath);
			System.out.println(filePath);
			compilationUnit = JavaParser.parse(fileInputStream);
	}
 
	
	GenFixVisitor genfixVisitor= new GenFixVisitor( );

	genfixVisitor.visit(compilationUnit, null);
	genfixVisitor.makeSetupFieldsList();
	genfixVisitor.checkSmell();
	 
	} 

}
 
