
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


class View extends JPanel implements ItemListener
{

 JTable table;
 JLabel title;
   Object[] columns = {"Code", "Item", "Serialno","Issue Date","Return Date","UserName"};
   Object[][] data;
JPanel p,p1,p2;
CheckboxGroup cbg;
Checkbox byU,byC,byS;
 View()
 {
       setLayout(null);
            p1=new JPanel();
	   		title=new JLabel("STOCK Full Information ");
	   		title.setFont(new Font("TimesNewRoman",Font.ITALIC,25));
	   		p1.setBorder(BorderFactory.createLineBorder(Color.RED,5));
	   		p1.setBackground(new Color(250,210,210));
		    p1.add(title);

   p2=new JPanel();
   cbg = new CheckboxGroup();
   byU = new Checkbox("Usernames", cbg, false);
   byC = new Checkbox("Code", cbg, false);
   byS = new Checkbox("Serial No.", cbg, false);
    p2.add(byU);
	p2.add(byC);
    p2.add(byS);
        byU.addItemListener(this);
        byC.addItemListener(this);
        byS.addItemListener(this);

     p2.setBorder(BorderFactory.createTitledBorder("View Sorted Listed By :"));



      add(p1);
      add(p2);


         p1.setBounds(350,2,420,50);
         p2.setBounds(150,150,600,60);


    p=new JPanel();
    add(p);

 }


 public void itemStateChanged(ItemEvent ie)
 {
	 filltable();
	 }

public void reset()
    {
		if(cbg.getSelectedCheckbox()!=null)
                cbg.setSelectedCheckbox(null);
//   remove(p);                                              // it can be used/unused accordingly as per the user choice.
   this.repaint();
}


public void filltable()
{
	try{
		remove(p);
		 p=new JPanel();
     p.setBounds(100,250,700,350);
     ArrayList<String> list;
     if(cbg.getSelectedCheckbox().getLabel().equals("Usernames"))
	 list=DbConnector.viewbyU();
	 else if(cbg.getSelectedCheckbox().getLabel().equals("Code"))
	 list=DbConnector.viewbyC();
	else
	 list=DbConnector.viewbyS();

	int j=-1,l=list.size();
	data=new Object[l/6][6];
	for(int i=0;i<l;i=i+6)
			{
			  j++;
				data[j][0] = list.get(i);
				data[j][1]=list.get(i+1);
				data[j][2]=list.get(i+2);
				data[j][3]=list.get(i+3);
				data[j][4]=list.get(i+4);
				data[j][5]=list.get(i+5);

		 }
		table = new JTable(data,columns){
						 public boolean isCellEditable(int rowIndex, int mColIndex) {
						              return false;                                   }	  };
		         table.getTableHeader().setBackground(Color.yellow);
		         table.setPreferredScrollableViewportSize(new Dimension(600,300));
                 p.add(new JScrollPane(table));
                 add(p);

      }
			 catch(Exception e){        e.printStackTrace();}

p.revalidate();

}

}





