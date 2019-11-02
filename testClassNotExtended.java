public class testClassNotExtended  {

String firstString="First";
String secondString="Second";

@Before
public void setUp() throws Exception {

System.out.println(firstString);
firstString="new";
secondString="secondNew";
 
}

@Test
public void StringFirstTest() {
 
assertEquals(firstString.size(), 5);

}

@Test
public void StringSecondTest() {
 
secondString+=" addedNewString";
assertEquals(firstString.size(), 5);

}

@Test
public void StringThirdTest() {
 
secondString+=" addedNewString";
firstString+="accha";
int ghghghgh=0;
assertEquals(firstString.size(), 5);

}

}