import java.io.Serializable;
/*
   Employee
	Kevin Dang n01051682
   Babatunde Olokun n01046746
	CENG212 0NB
	gnad.nivek@hotmail.com
   olokuntunde@gmail.com
	Employee.java
	December 14th, 2015
*/
public class Employee implements Comparable<Employee>, Serializable
{
   private String name;
   private int number;
   
   public Employee()
   {
      name = "John";
      number = 6;
   }
   
   public Employee(String name, int number)
   {
      this.name = name;
      this.number = number;
   }
   
   public String getName()
   {
      return name;
   }
   
   public int getNumber()
   {
      return number;
   }
   
   /**
   override println(); so you can print the
   employee name and number
   @return employee name and number
   */
   public String toString()
   {
      return (name +","+ number);
   }
   
   public int compareTo(Employee o)
   {
      int cmp =  getName().compareTo(o.getName());
      return cmp;
   }
}