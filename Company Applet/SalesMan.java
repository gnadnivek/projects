/*
   SalesMan
	Kevin Dang n01051682
   Babatunde Olokun n01046746
	CENG212 0NB
	gnad.nivek@hotmail.com
   olokuntunde@gmail.com
	SalesMan.java
	December 14th, 2015
*/
public class SalesMan extends Employee
{
   private double target;
   private String territory;
   
   public SalesMan()
   {
      super("Mary", 3);   //sets default employee name and number for SalesMan
      target = 0;
      territory = "Woodbridge";
   }
   
   public SalesMan(double target, String territory, String name, int number)
   {
      super(name, number);  //sets the SalesMan's name and employee number 
      this.target = target;
      this.territory = territory;
   }
   
   public String toString()
   {
      return ("S," + super.toString() +","+ target +","+ territory);
   }
}