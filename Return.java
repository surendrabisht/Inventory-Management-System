
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


class Return extends JPanel implements ActionListener,TextListener
{
	String uname;
 JButton ok,get;                                 // ok for returning & get is for listing table
 JTable table;
 TextField person,srno;
 JLabel title,or,n,s;
   Object[] columns = {"Code", "Item", "Serialno","Issue Date","Return Date","Fine","Select"};
   Object[][] data;
JPanel p,p1,p2;
 Return()
 {
       setLayout(null);
            p1=new JPanel();
	   		title=new JLabel("Return Form");
	   		title.setFont(new Font("TimesNewRoman",Font.ITALIC,25));
	   		p1.setBorder(BorderFactory.createLineBorder(Color.RED,5));
	   		p1.setBackground(new Color(250,210,210));
		    p1.add(title);

     p2=new JPanel();
     n=new JLabel("Name :");
     person=new TextField(20);
     or=new JLabel("    OR     ");
     or.setFont(new Font("TimesNewRoman",Font.ITALIC,15));
     s=new JLabel("Serial_no. / Item ");
     srno=new TextField(25);
     get=new JButton("Check");
     get.addActionListener(this);
     srno.addTextListener(this);
     person.addTextListener(this);
     p2.add(n);
     p2.add(person);
     p2.add(or);
     p2.add(s);
     p2.add(srno);
     p2.add(get);
     p2.setBorder(BorderFactory.createTitledBorder("Return check Entry :"));

      ok=new JButton("Return !! ");
	  ok.addActionListener(this);

      add(p1);
      add(p2);
      add(ok);

         p1.setBounds(350,2,420,50);
         p2.setBounds(150,150,700,60);
         ok.setBounds(350,400,90,30);

    p=new JPanel();
    add(p);

 }


 public void actionPerformed(ActionEvent ae)
 {
	 if(ae.getSource()==ok)
	 {
	 String str="";
	 for(int i=0;i<data.length;i++)
	 {
		 if((Boolean)table.getValueAt(i,6)==true)
		 {str=str+"\n item :"+table.getValueAt(i,1)+" with srno. "+table.getValueAt(i,2);
		   DbConnector.returnItem(Integer.parseInt(table.getValueAt(i,0).toString()),Integer.parseInt(table.getValueAt(i,2).toString()));
           }
	 }
	 if(str.equals(""))
	 str="Nothing..Tick to select items ";

	 JOptionPane.showMessageDialog(null,"u selected"+str);
     filltable(uname);
     }

     else
     {
		 uname="";
        if(!person.getText().equals(""))
		 filltable(person.getText());
		 else if(!srno.getText().equals(""))
		 		 filltable(srno.getText());
		 		 else
		 			 JOptionPane.showMessageDialog(this,"Nothing to search in Database","Blank!",JOptionPane.WARNING_MESSAGE);
	 }
 }

 public void textValueChanged(TextEvent e)
 {
 	if(person.getText().equals("") && srno.getText().equals(""))
	{
		person.setEditable(true);
		srno.setEditable(true);
	}
	  else if(!person.getText().equals(""))
	    	srno.setEditable(false);
		else
		person.setEditable(false);
  }


public void filltable(String a)
{
	remove(p);
	try{
		 p=new JPanel();
     p.setBounds(100,250,700,100);
     ArrayList<String> list;
     if(person.isEditable()==true || !uname.equals(""))
	list=DbConnector.getallC(a);
	else
		list=DbConnector.getallCbyS(a);
	int j=-1,l=list.size();
	data=new Object[l/6][7];
	uname=list.get(0);
	p.setBorder(BorderFactory.createTitledBorder(" Table Panel for "+uname));
	for(int i=1;i<l;i=i+6)
			{
			  j++;
				data[j][0] = list.get(i);
				data[j][1]=list.get(i+1);
				data[j][2]=list.get(i+2);
				data[j][3]=list.get(i+3);
				data[j][4]=list.get(i+4);
				data[j][5]=list.get(i+5);
				data[j][6]=new Boolean(false);

		 }
		table = new JTable(data,columns){
						 public boolean isCellEditable(int rowIndex, int mColIndex) {
						         	if (mColIndex==6)
						              	return true;
						              return false;                                      }

						    public Class getColumnClass(int column) {
								                 if (column==6)
								                      return Boolean.class;
								                   else
								                      return Object.class;
		                                                               }
						 };
		        table.setCellSelectionEnabled(true);
		         table.getTableHeader().setBackground(Color.yellow);
		         table.setPreferredScrollableViewportSize(new Dimension(600,50));
                 p.add(new JScrollPane(table));
                 add(p);

			 }
			 catch(Exception e){  e.printStackTrace(); }

p.revalidate();
}

}





