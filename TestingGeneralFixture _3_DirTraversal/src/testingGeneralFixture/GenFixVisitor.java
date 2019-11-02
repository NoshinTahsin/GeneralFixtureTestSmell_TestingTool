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
			 
	public GenFixVisitor(File projectDir, List<MethodDeclaration> declaredMethodList2,
			List<FieldDeclaration> listOfFields2,
			List<MethodDeclaration> setupMethodList2,
			List<MethodDeclaration> testMethodList2, List<String> setupFields2,
			List<String> testFields2, Set<String> testMethodFieldSet2, 
			List<MethodDeclaration> smellyMethodList2 ) {
		
		this.projectDir=projectDir;
		this.listOfFields=listOfFields2;
		this.declaredMethodList=declaredMethodList2;
		this.setupMethodList=setupMethodList2;
		this.testMethodList=testMethodList2;
		this.setupFields=setupFields2;
		this.testFields=testFields2;
		this.testMethodFieldSet=testMethodFieldSet2;
		this.smellyMethodList=smellyMethodList2;
     		 
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
        
        System.out.println("Iteration no: ************************");
        System.out.print(it+"\n\n");
        
        NodeList<BodyDeclaration<?>> members_body = n.getMembers();
        
        for(int i=0;i<members_body.size();i++) {
        	System.out.println(members_body.get(i).toString());
        }
         
        //member == field and methods
        super.visit(n, arg);
      
        int i=0;
      
        while(i<members_body.size()){
        	
    	//member field hoile field list e add korbo
        //method hoile method list e add korbo
        	
        	if (members_body.get(i) instanceof FieldDeclaration) {
        	
        		System.out.println("Field re vai\n");
        		System.out.println(members_body.get(i).toString());
        		listOfFields.add( (FieldDeclaration) members_body.get(i));
                
        	}
    	   
            if (members_body.get(i) instanceof MethodDeclaration) {
            	System.out.println("Method re vai\n");
            	System.out.println(members_body.get(i).toString());
            	declaredMethodList.add ((MethodDeclaration) members_body.get(i));
            }
  
            i++;
        }
         
        for(int ss=0; ss<listOfFields.size();ss++)
        {
        	System.out.println("Field gular list :"+listOfFields.get(ss));
        	//NodeList<VariableDeclarator> variableList = listOfFields.get(ss).getVariables();
        	//System.out.println("Field gular var er list :"+variableList.toString());
        }
        
        checkIfSetupOrTestMethod();
        
        for(int ss=0; ss<testMethodList.size();ss++)
        {
        	System.out.println("test method gular list :"+testMethodList.get(ss));
        }
        
        declaredMethodList=new ArrayList();
        
        //handling extended classes
        
        NodeList<ClassOrInterfaceType> extendedClass= n.getExtendedTypes();
        System.out.println("KOto ???"+extendedClass.size());
        
        for(ClassOrInterfaceType ec:extendedClass) {
        	System.out.println("parent "+ec.getNameAsString());
        	System.out.print(extendedClass.get(extendedClass.size()-1));
        	
        	CompilationUnit cuforextended=null;
        	
        	FileInputStream fileInputStream = null;
        	System.out.print("\n\n\n\n\n\n\n acchaaa\n\n\n\n\n\n\n");
        	System.out.print(projectDir.getPath());
    		String filePath= projectDir.getPath()+"\\"+
    				extendedClass.get(extendedClass.size()-1)+".java";
    		
    		filePath=filePath.replace("/", "\\");

    	if(filePath!=null) {
    			
    			try {
					fileInputStream = new FileInputStream(filePath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					
					System.out.append("File khuje pawa jay nai*************************************************************************");
					e.printStackTrace();
				}
    			System.out.println(filePath);
    			cuforextended = JavaParser.parse(fileInputStream);
    		}
    	
    	visit(cuforextended,null);
    	 
        } 
    }
    
    void makeSetupFieldsList()
    {
    	 
    	 System.out.println("ETTTTTTTTTTTTTTTTTTTOOO KNNNNN "+setupMethodList.size());
         
         for(int amma=0; amma<setupMethodList.size(); amma++)
         {
         	 System.out.println("\n \n \n "+setupMethodList.get(amma));
         }
         
         
         if(setupMethodList.size()==0)
         {
        	System.out.println("Setup method nai re vai"); 	
         }
        
     /*   else {
        	
        	System.out.println("jj "+setupMethodList.size());
        	for(int ss=0; ss<setupMethodList.size();ss++){
        		visit(setupMethodList.get(ss), null);
        		
        	}
        	
        }*/
        
        else {
        	
        	for(int ss=0; ss<setupMethodList.size();ss++){
        		
        		//List<NodeList<?>> setupmethodNodelists = setupMethodList.get(ss).getNodeLists();
        		Optional<BlockStmt> setupMethodBody = setupMethodList.get(ss).getBody();
        		
        		//System.out.println("Nodelist dekhi dekhte kmn: "+setupmethodNodelists.toString());
        		System.out.println("body dekhi: "+setupMethodBody.toString());
        		
        		NodeList<Statement> setupMethodBodyStatements = setupMethodBody.get().getStatements();
        		//System.out.println("body er statement gula dekhi: "+setupMethodBodyStatements.toString());
        		//print hoyna node string 
        	 
        		for( int setupBodyStatementsCount=0; setupBodyStatementsCount<setupMethodBodyStatements.size();
        				setupBodyStatementsCount++) {
        			 
        			//	for(int varCount=0; varCount < variables.size(); varCount++) {
        					
        					if (setupMethodBodyStatements.get(setupBodyStatementsCount) instanceof ExpressionStmt) {
                               
        						ExpressionStmt expression = (ExpressionStmt) setupMethodBodyStatements.get(setupBodyStatementsCount);
                                System.out.println("Checking expression: "+expression.toString());
                                
                                if (expression.getExpression() instanceof AssignExpr) {
                                	
                                	System.out.println("Checking if assign expression: "+expression.toString());
                                   
                                	AssignExpr assignExpression = (AssignExpr) expression.getExpression();
                                	System.out.println("Checking assign expression: "+assignExpression.toString());
                                	
                                    String expressionName=assignExpression.getTarget().toString();
                                    
                                    System.out.println(expressionName);
                                    if(!setupFields.contains(expressionName) ) {
                                    	setupFields.add(expressionName);
                                    	System.out.println("Setup e ekhn ache : "+expressionName);
                                    }
                                  
                               }
                            }
        				
        			}
        		
        		
        		//b=setupMethodBodyStatements.
        		
        		MethodDeclaration setupMethod = setupMethodList.get(ss);
        		//FieldDeclaration fieldList;
        		
        		List<NodeList<?>> nodeList = setupMethod.getNodeLists();
        		
        		/*for(int s=0; s<nodeList.size(); s++) {
        			
        			if(nodeList.get(s) instanceof VariableDeclaration) {
        				
        			}
        		}
        		
        		
        		//System.out.println("koyta node?: "+fieldList.size());
        		//System.out.println("koyta node?: "+fieldList.);
        		
        		
        		//kaj korena
        		//visit(setupMethodList.get(ss), null);
        		
        		*
        		*/
        	 	
         	}	
        }
    
      
    }
    
    void checkSmell() {
    	
    	 //test method er field check kori
        System.out.println("jj "+testMethodList.size());
        
    	for(int ss=0; ss<testMethodList.size();ss++){
    		
    	
    		System.out.println("Checking for method: "+ testMethodList.get(ss));
    		
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
                        
                        System.out.println(resultString);
                        
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

   /* @Override
   	public void visit(FieldDeclaration n, Void arg) {
   		// TODO Auto-generated method stub
   		super.visit(n, arg);
   		
   		System.out.println("Ashchi ki??");
   		//System.out.println("Plzzzz vai:" +n);
   		NodeList<VariableDeclarator> a = n.getVariables();
   		System.out.println("A size: "+a.size());
   
   		
   	}*/
    
     
    
 public boolean is_a_Valid_SetupMethod(MethodDeclaration md) {
    	
        boolean isValidSetupMethod = false;
        
        if (md.getAnnotationByName("Ignore").isPresent()) {
        	return false;
        }
        
        else{
        	
            if ((md.getAnnotationByName("Before").isPresent() || 
            		md.getNameAsString().equals("setUp"))
            		&& md.getModifiers().contains(Modifier.PUBLIC)) {
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
   		System.out.println(n.getNameAsString());
   		
   		String testMethodField=n.getNameAsString();
        if (setupFields.contains(testMethodField) && 
        		!testMethodFieldSet.contains(testMethodField)){
        	testMethodFieldSet.add(testMethodField);
        	System.out.println("added Field: "+testMethodField);
        	
         }	
   	}
}
