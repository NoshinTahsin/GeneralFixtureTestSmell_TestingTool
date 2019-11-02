package testingGeneralFixture;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.NameExpr;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

public class GenFixVisitor extends VoidVisitorAdapter<Void> {
	 
	File projectDir = new File("a.java");
	
	private List<FieldDeclaration> listOfFields=new ArrayList<FieldDeclaration>();
    private List<MethodDeclaration> declaredMethodList = new ArrayList<MethodDeclaration>();
    private List<MethodDeclaration> setupMethodList = new ArrayList<MethodDeclaration>();
    private List<MethodDeclaration> testMethodList = new ArrayList<MethodDeclaration>();
    private List<String> setupFields=new ArrayList<String>();
    private List<String> testFields=new ArrayList<String>();
    Set<String> testMethodFieldSet = new HashSet<String>(); 
    private List<MethodDeclaration> smellyMethodList = new ArrayList<MethodDeclaration>();
 
    public GenFixVisitor(File projectDir) {
    
    	this.projectDir=projectDir;
    }
    
    void checkIfSetupOrTestMethod() {
    	
    	   for(int ss=0; ss<declaredMethodList.size();ss++)
           {
    		   System.out.println("Method gular list :"+declaredMethodList.get(ss));
           	
           		if(is_a_Valid_SetupMethod(declaredMethodList.get(ss))) {
           		
           			setupMethodList.add ((MethodDeclaration) declaredMethodList.get(ss));
           		}
           	
           		if (it==1){
           	
           			if(checkIfValidTestMethod(declaredMethodList.get(ss))) {
           				testMethodList.add ((MethodDeclaration) declaredMethodList.get(ss));
           			}
           		}	
           }
    }
    
    int it=0;
    
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
	          
        it++;
        
        System.out.println("\nIteration no: "+it);
        
        NodeList<BodyDeclaration<?>> members_body = n.getMembers();
        
        for(int i=0;i<members_body.size();i++) {
        	//System.out.println(members_body.get(i).toString());
        }
         
        super.visit(n, arg);
      
        int i=0;
      
        while(i<members_body.size()){
        	
        	if (members_body.get(i) instanceof FieldDeclaration) {
         
        		listOfFields.add( (FieldDeclaration) members_body.get(i));
                
        	}
    	   
            if (members_body.get(i) instanceof MethodDeclaration) {
             
            	declaredMethodList.add ((MethodDeclaration) members_body.get(i));
            }
  
            i++;
        }
         
        for(int ss=0; ss<listOfFields.size();ss++)
        {
        	//System.out.println("Field gular list :"+listOfFields.get(ss));
        	//NodeList<VariableDeclarator> variableList = listOfFields.get(ss).getVariables();
        	//System.out.println("Field gular var er list :"+variableList.toString());
        }
        
        checkIfSetupOrTestMethod();
        
        for(int ss=0; ss<testMethodList.size();ss++)
        {
        	System.out.println("\nList of Test Methods for the class :\n\n"+testMethodList.get(ss));
        }
        
        declaredMethodList=new ArrayList();
        
        //handling extended classes
        
        NodeList<ClassOrInterfaceType> extendedClass= n.getExtendedTypes();
    
        
        for(ClassOrInterfaceType ec:extendedClass) {
        	
        	System.out.println("\nparent "+ec.getNameAsString());
        	System.out.print("\n"+extendedClass.get(extendedClass.size()-1));
        	
        	CompilationUnit cuforextended=null;
        	
        	FileInputStream fileInputStream = null;
         	//System.out.print(projectDir.getPath());
    		String filePath= projectDir.getPath()+"\\"+
    				extendedClass.get(extendedClass.size()-1)+".java";
    		
    		filePath=filePath.replace("/", "\\");

    	if(filePath!=null) {
    			
    			try {
					fileInputStream = new FileInputStream(filePath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					
					System.out.append("File khuje pawa jay nai");
					e.printStackTrace();
				}
    			
    			System.out.println("\nVisitor er filepath: "+filePath);
    			cuforextended = JavaParser.parse(fileInputStream);
    		}
    	
    	visit(cuforextended,null);
    	 
        } 
    }
    
    void makeSetupFieldsList()
    {
    	      
         for(int amma=0; amma<setupMethodList.size(); amma++)
         {
         	 System.out.println("\nSetup method er list "+setupMethodList.get(amma));
         }
         
         
         if(setupMethodList.size()==0)
         {
        	System.out.println("\nSetup method nai re vai"); 	
         }
  
        
        else {
        	
        	for(int ss=0; ss<setupMethodList.size();ss++){
        		
         		Optional<BlockStmt> setupMethodBody = setupMethodList.get(ss).getBody();
        		
         		System.out.println("\nbody dekhi:\n "+setupMethodBody.toString());
        		
        		NodeList<Statement> setupMethodBodyStatements = setupMethodBody.get().getStatements();
        		 
        		for(int setupBodyStatementsCount=0; setupBodyStatementsCount<setupMethodBodyStatements.size();
        				setupBodyStatementsCount++) {
        		 
        					if (setupMethodBodyStatements.get(setupBodyStatementsCount) instanceof ExpressionStmt) {
                               
        						ExpressionStmt expression = (ExpressionStmt) setupMethodBodyStatements.get(setupBodyStatementsCount);
                                 
                                if (expression.getExpression() instanceof AssignExpr) {
                                
                                	AssignExpr assignExpression = (AssignExpr) expression.getExpression();
                                
                                    String expressionName=assignExpression.getTarget().toString();
                                 
                                    if(!setupFields.contains(expressionName) ) {
                                    	setupFields.add(expressionName);
                                    	System.out.println("\nSetup e ekhn ache : "+expressionName);
                                    } 
                               }
                            }
        			}
      	
        		MethodDeclaration setupMethod = setupMethodList.get(ss);
        	 
        		List<NodeList<?>> nodeList = setupMethod.getNodeLists();
      	 
         	}	
        }
      
    }
    
    void checkSmell() {
    	
         System.out.println("\nNumber of test methods in the class : "+testMethodList.size());
        
         for(int ss=0; ss<testMethodList.size();ss++){
     
    		visit(testMethodList.get(ss), null);
    		 
    		if(testMethodFieldSet.size()!=setupFields.size())
            {
            	smellyMethodList.add(testMethodList.get(ss));
            	
            	for (String setupfieldVariable:setupFields) {
            		
            		if (!testMethodFieldSet.contains(setupfieldVariable)) {
            		 
            			int methodLineNumber = testMethodList.get(ss).getBegin().get().line;
              			int methodEndNumber=testMethodList.get(ss).getEnd().get().line;

                        String resultString = "\nMethod "+testMethodList.get(ss).getNameAsString()+
                        		"() has smell for variable "+
                        setupfieldVariable+" from line no "+methodLineNumber+
                        " to "+methodEndNumber+"\n\n\n\n";
                        
                        System.out.println("\nResult String :"+resultString);
                        
                        String smellMethodName=testMethodList.get(ss).getNameAsString();
                        String smellVariableP=setupfieldVariable;
                        int startPoint=methodLineNumber;
                        int endpoint=methodEndNumber;
                   
            		}
            	}
            }
    		
    		testMethodFieldSet=new HashSet();
    	}    
    }
 
    
 public boolean is_a_Valid_SetupMethod(MethodDeclaration md) {
    	
        boolean isValidSetupMethod = false;
        
        if (md.getAnnotationByName("Ignore").isPresent()) {
        	return false;
        }
        
        else{
        	
            if (md.getAnnotationByName("Before").isPresent() || 
            		md.getNameAsString().equals("setUp"))
            {
            	 isValidSetupMethod = true;
            }          
        }

        return isValidSetupMethod;
    }

  
 public static boolean checkIfValidTestMethod(MethodDeclaration methodToCheck) {
        
        boolean isValidTestMethod=false;
        
        if (methodToCheck.getAnnotationByName("Ignore").isPresent()) {
        	return false;
        }
        
        else{
            if ((methodToCheck.getAnnotationByName("Test").isPresent()||
            		methodToCheck.getNameAsString().toLowerCase().startsWith("test")||
            		methodToCheck.getNameAsString().toLowerCase().lastIndexOf("test")!=-1)
            		&& methodToCheck.getModifiers().contains(Modifier.PUBLIC)){
            	isValidTestMethod=true;          
            }              
        }

        return isValidTestMethod;
    }
    
    @Override
   	public void visit(NameExpr n, Void arg) {
   		// TODO Auto-generated method stub
   		super.visit(n, arg);
   		//System.out.println(n.getNameAsString());
   		
   		String testMethodField=n.getNameAsString();
        if (setupFields.contains(testMethodField) && 
        		!testMethodFieldSet.contains(testMethodField)){
        	testMethodFieldSet.add(testMethodField);
        //	System.out.println("added Field: "+testMethodField);
        	
         }	
   	}
}
