import java.util.*;
/*
   Company
	Kevin Dang n01051682
   Babatunde Olokun n01046746
	CENG212 0NB
	gnad.nivek@hotmail.com
   olokuntunde@gmail.com
	Company.java
	December 14th, 2015
*/
public class Company extends Employee
{
   public LinkedList myCompany = new LinkedList(); //holds all employees
   
   public Company() //create the initial employee list
   {
      SalesMan s1 = new SalesMan(50.99, "Toronto", "Mary", 16);
      Technician t1 = new Technician(10, "field", "Kevin", 54);
      SalesMan s2 = new SalesMan(204.45, "humber", "Dan", 12);
      Technician t2 = new Technician(7, "office", "John", 23);
      
      myCompany.add(s1);
      myCompany.add(t1);
      myCompany.add(s2);
      myCompany.add(t2);
   }
   
   /**
   adds a new SalesMan or Technician to the myCompany list
   @param letter tells you the kind of employee being added
   @param targetLevel the new employee's target sales or level
   @param section the department or area the employee works at
   @param name the employee's name
   @param number the employee's number
   */
   public void add(String letter, double targetLevel, String section, String name, int number)
   {
      if(letter.equals("S"))  //looks to see if employee is a SalesMan or Technician
      {
         SalesMan s = new SalesMan(targetLevel, section, name, number);
         myCompany.add(s);   //adds a new SalesMan to the employee list
      }
      else
      {
         Technician t = new Technician(((int)targetLevel), section, name, number);
         myCompany.add(t);  
      }       
   }
   
   /**
   looks for an employee number and removes that
   employee for the list
   @param number the employee number of who to remove 
   */
   public void delete(int number)
   {      
      for(int i = 0; i < myCompany.size(); i++)
      {
         if((((Employee)myCompany.get(i)).getNumber()) == number) //if the employee number matches
         {
            myCompany.remove(i);  //the employee is deleted from the list
         }
      }      
   }
   
   /**
   returns the employee list the the applet to display
   @return myCompany the employee list
   */
   public LinkedList display()
   {
      return myCompany;
   }
   
   /**
   sorts the employee list alphabetical by name
   */
   public void sort()
   {
      Collections.sort(myCompany);     //sort the employee list
   }
   
   /**
   resets the employee list back to the default
   makes a new list with initial employees
   new list is make equal the orginal list
   */
   public void reset()
   {       
      LinkedList oldComp = new LinkedList();  //makes another list with only the initial employees 
      SalesMan s1 = new SalesMan(50.99, "Toronto", "Mary", 16);
      Technician t1 = new Technician(10, "field", "Kevin", 54);
      SalesMan s2 = new SalesMan(204.45, "humber", "Dan", 12);
      Technician t2 = new Technician(7, "office", "John", 23);
      
      oldComp.add(s1);
      oldComp.add(t1);
      oldComp.add(s2);
      oldComp.add(t2);
      
      myCompany = oldComp;  //makes the new list the same as the orignal 
   }
}