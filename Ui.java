import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;

class Ui extends JFrame
{
	static Connection con;
	Container c;
	JTabbedPane tp;
	Home h;
	Insert i;
	IssueForm is;
	Return ret;
	View v;
	static
	{

		try
					{
						      	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // for sqlserver db
						      	String connectionUrl = "jdbc:sqlserver://localhost:1433;" +"databaseName=Inventory;user=sa;password=admin;";
                             	con = DriverManager.getConnection(connectionUrl);
					}
					catch(Exception e)
					{
				JOptionPane.showMessageDialog(null,"Database Connectivity error..! Application will close.","Error",JOptionPane.ERROR_MESSAGE);
                   System.exit(0);
				}


    }

 Ui()
  {
	  DbConnector.driverSetup(con);
	  c = getContentPane();
	  h=new Home();
	  i=new Insert();
	  is=new IssueForm();
	  ret=new Return();
      v=new View();
		tp = new JTabbedPane();
		tp.addTab("Welcome",null,h,"Welcome");
		tp.addTab("Add item",null,i,"Add item");
		tp.addTab("Issue Item ",null,is,"Issue frame");
        tp.addTab("Return Item",null,ret,"return frame");
        tp.addTab("View",null,v,"Stock Info");

            tp.addChangeListener(new ChangeListener()

				{

				public void stateChanged(ChangeEvent e) {

			             JTabbedPane pane = (JTabbedPane) e.getSource();
			        int selectedIndex = pane.getSelectedIndex();
			        

			        switch(selectedIndex)
			        {
						case 4:
						v.reset();

					}
		        }

            });

		c.add(tp);
	setSize(Toolkit.getDefaultToolkit().getScreenSize());
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	addWindowListener(new WindowAdapter()
	{
	      public void windowClosing(WindowEvent we)
	      {
			  try
			  {
			  con.close();
             System.out.println(" Con is closed");
		     }
		     catch(Exception e)
		     {}
	          dispose();}
    });
	setVisible(true);

 }

}