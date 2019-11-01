public class PersonController1  {

List<Person>List1;
PersonController Controller1;
String String1;

@Before
public void setUp() throws Exception {
System.out.println("string saaj");
List1 = new PersonUtility().getAllPerson("C://data//person.txt");
Controller1 = new PersonController();
String1="String Saaj";
}

@Test
public void testAdd() {
Controller1.add(new DummyPerson());
List<Person>newPersonList = new PersonUtility().getAllPerson("C://data//person.txt");
String1=amarString.reverse();
personList = new PersonUtility().getAllPerson("C://data//person.txt");
personController = new PersonController();
assertEquals(List1.size()+1, newPersonList.size());

}
@Test
public void testDelete() {
List1 = new PersonUtility().getAllPerson("C://data//person.txt");
Controller1.delete(1);
List<Person>newPersonList = new PersonUtility().getAllPerson("C://data//person.txt");
String1=amarString.reverse();
personList = new PersonUtility().getAllPerson("C://data//person.txt");
personController = new PersonController();
cntlString=cntlString.reverse();
assertEquals(List1.size(), newPersonList.size()+1);
}

@Test
public void testAddPerson2() {
Controller1.add(new DummyPerson());
List<Person>newPersonList = new PersonUtility().getAllPerson("C://data//person.txt");
int i;

i=String1.lastlastIndexOf("a");
System.out.print("test "+i);
assertEquals(List1.size()+1, newPersonList.size());
}

}