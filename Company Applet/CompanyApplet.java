import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.JTextComponent;
/*
   CompanyApplet
	Kevin Dang n01051682
   Babatunde Olokun n01046746
	CENG212 0NB
	gnad.nivek@hotmail.com
   olokuntunde@gmail.com
	CompanyApplet.java
	December 14th, 2015
*/
public class CompanyApplet extends JApplet
{
   private Company myComp = new Company();
   
   private JTextArea empData;
   private JTextField nameF;
   private JTextField numberF;
   private JTextField targetLevelF;
   private JTextField sectionF;
   
   private JRadioButton s;
   private JRadioButton t;
   
   private int number;
   private double targetLevel;
   private String name;
   private String section;
   
   public CompanyApplet()
   {        
      setLayout(new BorderLayout());
      JPanel data = new JPanel();
      JPanel change = new JPanel();
      JPanel control = new JPanel();

      //1st panel 
      empData = new JTextArea(10, 35);
      empData.setLineWrap(true);
      empData.setWrapStyleWord(true);
      empData.setForeground(Color.blue);      
      empData.setEditable(false);     
      data.add(empData);   
      
      //radio button panel  
      s = new JRadioButton("SalesMan");
      t = new JRadioButton("Technican", true);
      ButtonGroup bg = new ButtonGroup();
      bg.add(s);
      bg.add(t);     
      
      //2nd panel   
      nameF = new JTextField(10);    
      numberF = new JTextField(10);     
      targetLevelF = new JTextField(10);   
      sectionF = new JTextField(10);
      
      JLabel l1 = new JLabel("Name:");
      JLabel l2 = new JLabel("Employee #:");
      JLabel l3 = new JLabel("Target or Level:");
      JLabel l4 = new JLabel("Department or Territory:");
 
      change.add(t);
      change.add(s);
      change.add(l1);
      change.add(nameF);
      change.add(l2);
      change.add(numberF);
      change.add(l3);
      change.add(targetLevelF);
      change.add(l4);
      change.add(sectionF);
      change.setLayout(new GridLayout(0,1));
            
      //3rd panel
      JButton display = new JButton("Display");
      JButton sort = new JButton("Sort");
      JButton add = new JButton("Add");
      JButton delete = new JButton("Delete");
      JButton reset = new JButton("Reset");
      
      //listeners for all buttons
      display.addActionListener(new DisplayButton());         
      sort.addActionListener(new SortButton());  
      add.addActionListener(new AddButton());    
      delete.addActionListener(new DeleteButton());  
      reset.addActionListener(new ResetButton()); 
      
      control.add(display);
      control.add(sort);
      control.add(reset);
      control.add(add);
      control.add(delete);      

      //add all panels to the frame
      add(data, BorderLayout.NORTH);
      add(change, BorderLayout.CENTER);
      add(control, BorderLayout.SOUTH);
   }
    
   private class AddButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{         
         //get the values user inputs in text fields
         name = nameF.getText();
         number = Integer.parseInt(numberF.getText());
         targetLevel = Double.parseDouble(targetLevelF.getText());   
         section = sectionF.getText();
         
         if(t.isSelected())   //checks what radio button is selected
         {            
            String letter = "T";
            myComp.add(letter, targetLevel, section, name, number);
         }    
         else
         {
            String letter = "S";
            myComp.add(letter, targetLevel, section, name, number);
         }
      }
   }
   
   private class DeleteButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{      
         number = Integer.parseInt(numberF.getText());
         myComp.delete(number);        
      }
   }
   
   private class SortButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
         myComp.sort();
      }
   }

   private class DisplayButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{       
         empData.setText(null);   //clear the text area
         LinkedList myCompany = new LinkedList();
         myCompany = myComp.display();
         
         for(int i = 0; i < myCompany.size(); i++)
         {              
            empData.append(myCompany.get(i).toString());  //prints a employee on the applet 
            empData.append("\n");  //prints a newline after the employee
         }              
      }
   }
   
   private class ResetButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
         myComp.reset();
      }
   }
}