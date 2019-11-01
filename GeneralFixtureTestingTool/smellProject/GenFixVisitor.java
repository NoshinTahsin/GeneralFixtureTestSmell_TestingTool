 package smellProject;

import java.io.BufferedWriter;
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
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;


public class GenFixVisitor extends VoidVisitorAdapter<Void> {
	 
	public GenFixVisitor( ) {
     		 
	}
    
    private List<FieldDeclaration> listOfFields=new ArrayList<FieldDeclaration>();
    private List<MethodDeclaration> declaredMethodList = new ArrayList<MethodDeclaration>();
    private List<MethodDeclaration> setupMethodList = new ArrayList<MethodDeclaration>();
    private List<MethodDeclaration> testMethodList = new ArrayList<MethodDeclaration>();
    private List<String> setupFields=new ArrayList<String>();
    private List<String> testFields=new ArrayList<String>();
    Set<String> testMethodFieldSet = new HashSet<String>(); 
    private List<MethodDeclaration> smellyMethodList = new ArrayList<MethodDeclaration>();
    
    
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
		
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
        
        for(int ss=0; ss<declaredMethodList.size();ss++)
        {
        	System.out.println("Method gular list :"+declaredMethodList.get(ss));
        	
        	if(is_a_Valid_SetupMethod(declaredMethodList.get(ss))) {
        		
        		setupMethodList.add ((MethodDeclaration) declaredMethodList.get(ss));
        	}
        	
        	if(checkIfValidTestMethod(declaredMethodList.get(ss))) {
        		
        		testMethodList.add ((MethodDeclaration) declaredMethodList.get(ss));
        		
        	}
        		
        	
        }
        
        for(int ss=0; ss<setupMethodList.size();ss++)
        {
        	System.out.println("setup method gular list :"+setupMethodList.get(ss));
        }
        
        for(int ss=0; ss<testMethodList.size();ss++)
        {
        	System.out.println("test method gular list :"+testMethodList.get(ss));
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
                                    setupFields.add(expressionName);
                                  
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
        		}*/
        		
        		
        		//System.out.println("koyta node?: "+fieldList.size());
        		//System.out.println("koyta node?: "+fieldList.);
        		
        		
        		//kaj korena
        		//visit(setupMethodList.get(ss), null);
        		
        		System.out.println("Ashchi ki??2");
        		
        		
        		
        	}
        		
        }
        
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
        		!testMethodFieldSet.contains(testMethodField)) {
        	testMethodFieldSet.add(testMethodField);
        	System.out.println("added Field: "+testMethodField);
        	
         }
        
        
   		
   		
   	}
}