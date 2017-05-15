/*
   Technician
	Kevin Dang n01051682
   Babatunde Olokun n01046746
	CENG212 0NB
	gnad.nivek@hotmail.com
   olokuntunde@gmail.com
	Technician.java
	December 14th, 2015
*/
public class Technician extends Employee
{
   private int level;
   private String department;
   
   public Technician()
   {
      super("Kevin", 2);
      level = 0;
      department = "inventory";
   }
   
   public Technician(int level, String department, String name, int number)
   {
      super(name, number);
      this.level = level;
      this.department = department;
   }
   
   public String toString()
   {
      return ("T," + super.toString() +","+ level +","+ department);
   }
}