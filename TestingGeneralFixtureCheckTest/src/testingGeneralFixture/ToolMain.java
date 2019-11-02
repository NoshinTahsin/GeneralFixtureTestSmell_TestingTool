package testingGeneralFixture;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.jdt.internal.corext.util.Strings;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ToolMain {
	
	 public static void listClasses(File projectDir) {
		 
	        new DirectoryExplorer((level, path, file) -> path.endsWith(".java") && 
	        		(path.contains("Test") || path.contains("test")),
	        		(level, path, file) -> {
	            System.out.println(path);
	            path=projectDir+path;
	            path=path.replace("/", "\\");
	            
	            BuildCompilationUnit bcu=new BuildCompilationUnit();
		        try {
					bcu.buildCompilationUnit(path, projectDir);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         //   System.out.println(Strings.repeat("=", path.length()));
	            try {
	                new VoidVisitorAdapter<Object>() {
	                    @Override
	                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
	                        super.visit(n, arg);
	                        System.out.println(" * " + n.getName());
	                    }
	                }.visit(JavaParser.parse(file), null);
	                System.out.println(); // empty line
	            } catch (IOException e) {
	                new RuntimeException(e);
	            }
	        }).explore(projectDir);
	        
	        
	    }
	 
	    public static void main(String[] args) {
	    	
	    	JFrame frame = new JFrame("A simple testing tool");
			frame.getContentPane().setBackground(new Color(255, 255, 255));
			frame.getContentPane().setForeground(Color.black);
			frame.setBounds(100, 100, 800, 500);
			
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);
	        
	      //  frame.setSize(300,300);
	        JButton btnToChooseFile = new JButton("Choose Directory");
	      //  frame.getContentPane().add(btnToChooseFile);
	        
	        btnToChooseFile.setForeground(new Color(255, 255, 255));
			btnToChooseFile.setBackground(new Color(55, 67, 67));
			//btnToChooseFile.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
			btnToChooseFile.setBounds(240, 170, 300, 36);
			btnToChooseFile.addActionListener(null);
			
	        frame.setVisible(true);
	        
	        frame.getContentPane().add(btnToChooseFile);
			
			JLabel lblGeneralFixture = new JLabel("Detect General Fixture Smell");
			lblGeneralFixture.setFont(new Font("Times New Roman", Font.BOLD, 25));
			lblGeneralFixture.setBounds(240, 41, 323, 43);
			frame.getContentPane().add(lblGeneralFixture);
			
			//button ta frame e add kora lagbe
			frame.getContentPane().add(btnToChooseFile);
			btnToChooseFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					
					System.out.println("button clicked");
					
					JFileChooser jFileChooserc = new JFileChooser();
		            jFileChooserc.setCurrentDirectory(new java.io.File("C:\\Users\\USER\\Desktop"));
		    		jFileChooserc.setMultiSelectionEnabled(true);
		    		jFileChooserc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		            
		            int returnValue = jFileChooserc.showOpenDialog(null);
		    		
					if (jFileChooserc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		                File[] files = jFileChooserc.getSelectedFiles();
		                
		               File projectDir = new File(jFileChooserc.getSelectedFile().toString());
		                
		                System.out.println("ProjectDir: "+projectDir);
					 
		                listClasses(projectDir);
					}
				
				}     
				     
		    } );
		
		
			
	   //     File projectDir = new File("C:\\Users\\noshi\\Documents\\EquationSolverTest-master");
	         
	    }
	

}
 
