import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class Entry extends Frame implements ActionListener,TextListener
{
	Image img;                         // background image of frame
	Button x,ok,cl,eok;
    TextField ut,pt;
    Dialog d1;
    Label el=new Label("Error label");

Entry()
{
Label title,ul,pl;

setSize(600,400);

Point p= new Point(Set.size(getSize()));      // calling my defined static  size function of Set class..
setLocation(p);
setLayout(null);


el.setFont(new Font("TimesNewRoman",Font.BOLD,12));
d1 = new Dialog(this,"Error! ",true);
d1.setSize(200,60);
d1.setBackground(new Color(250,210,210));
d1.setLayout(new FlowLayout());
eok=new Button("Ok !");
eok.addActionListener(this);
d1.add(el);
d1.add(eok);
d1.setUndecorated(true);
d1.setLocation(p.x+200,p.y+220);



x =new Button("X");
ok =new Button("Proceed");
cl=new Button(" clear");

title=new Label(" Inventory Management System ");
ul=new Label("Admin :");
pl=new Label("Password :");

ut = new TextField(20);
pt = new TextField(20);
pt.setEchoChar('*');

title.setBounds(180,5,300,50);
ul.setBounds(200,150,60,30);
pl.setBounds(200,220,80,30);
ut.setBounds(300,150,200,30);
pt.setBounds(300,220,200,30);


x.setBounds(100,300,50,30);
ok.setBounds(350,300,70,30);
ok.setEnabled(false);
cl.setBounds(450,300,60,30);

ut.addTextListener (this);pt.addTextListener(this);
x.addActionListener(this); ok.addActionListener(this); cl.addActionListener(this);

add(title);
add(ul);
add(pl);
add(ut);
add(pt);
add(x);
add(ok);
add(cl);


title.setBackground(new Color(0,0,128));
title.setFont(new Font("TimesNewRoman",Font.ITALIC,20));
title.setForeground(Color.WHITE);
//ul.setForeground(Color.BLUE);
ul.setFont(new Font("TimesNewRoman",Font.BOLD,15));
//pl.setForeground(Color.BLUE);
pl.setFont(new Font("TimesNewRoman",Font.BOLD,15));
ut.setFont(new Font("SANS_SERIF",Font.PLAIN,15));
pt.setFont(new Font("SANS_SERIF",Font.PLAIN,15));

x.setBackground(new Color(176,23,31));
x.setForeground(Color.WHITE);
ok.setForeground(Color.RED);


setUndecorated(true);
setVisible(true);

}

public void actionPerformed(ActionEvent ae)
{
	if(ae.getSource()==ok)
	{
	     if(dbConnect()==true)
	      {
	     	   new Ui();
	     	  dispose();
	        }
	        else
	        {
				d1.setVisible(true);
	           ut.setText("");
		       pt.setText("");
	         }
	 }
	else if(ae.getSource()==cl)
	{
		ut.setText("");
		pt.setText("");
	}
	else if(ae.getSource()==eok)
		{
		   d1.dispose();
	    }
	else
	dispose();
}


public void textValueChanged(TextEvent e)
{
  if(ut.getText().equals("") || pt.getText().equals(""))
  {
   ok.setEnabled(false);
  }
  else
  {
	  ok.setEnabled(true);
  }
}

public boolean dbConnect()
{
	Connection con=null;
	try
	{
             // for sql server Db
      	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      	String connectionUrl = "jdbc:sqlserver://localhost:1433;" +"databaseName=Inventory;user=sa;password=admin;";
      	con = DriverManager.getConnection(connectionUrl);
        PreparedStatement st = con.prepareStatement("select * from Admin where users=? AND password=?");
         st.setString(1,ut.getText());
         st.setString(2,pt.getText());
	    	ResultSet rs = st.executeQuery();
				 if(rs.next())
                  return true;
                  else
                   {
                      el.setText("Unauthorized User");
	  	   		        return false;
	           		  }
     }
        catch(Exception e)
	  	         {
					 el.setText("Database Connectivity Error"+e);
					   System.out.println(e);
	  	   		        return false;
	              }
	  finally
	  {try
	    {con.close();} catch(Exception e)
      	            {}
       }
}



public void paint(Graphics g)
{
	  img = Toolkit.getDefaultToolkit().getImage("1.jpg");
	  g.drawImage(img,0,0,this);
}


public static void main(String args[])
{
new Entry();
}
}