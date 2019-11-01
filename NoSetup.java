public class PersonSmell {

List<Person>a;
PersonController b;
String x;

 

@Test
public void testAdd() {
b.add(new DummyPerson());
List<Person>newPersonList = new PersonUtility().getAllPerson("C://data//person.txt");
personList = new PersonUtility().getAllPerson("C://data//person.txt");
personController = new PersonController();
assertEquals(a.size()+1, newPersonList.size());

}
@Test
public void testDelete() {
a = new PersonUtility().getAllPerson("C://data//person.txt");
 
List<Person>newPersonList = new PersonUtility().getAllPerson("C://data//person.txt");
x=x.reverse();
personList = new PersonUtility().getAllPerson("C://data//person.txt");
personController = new PersonController();
cntlString=cntlString.reverse();
}

@Test
public void testAddAgain() {
b.add(new DummyPerson());
List<Person>newPersonList = new PersonUtility().getAllPerson("C://data//person.txt");
x=x+1;
System.out.print("test ");
assertEquals(a.size()+1, newPersonList.size());
}

}
