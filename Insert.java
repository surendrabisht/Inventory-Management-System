import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Insert extends JPanel implements ActionListener,TextListener
{
			JLabel title,i,c,s;
            TextField item,icode,srno;
            JButton done;
            JLabel status,e2,e3;
            String errc="",errs="";
	Insert()
	{
			JPanel p1,p2;
    setLayout(null);

		p1=new JPanel();
		title=new JLabel("Insert Stock Form");
		title.setFont(new Font("TimesNewRoman",Font.BOLD+Font.ITALIC,30));
		p1.add(title);

		p2= new JPanel();
        i=new JLabel("ITEM :");
		c=new JLabel("ITEM CODE : ");
		s=new JLabel("ITEM SERIAL NO :");
		item=new TextField(30);
		icode=new TextField(30);
		srno=new TextField(30);


        p2.setLayout(new GridLayout(3,2,30,50));
        p2.add(i);p2.add(item);
		p2.add(c);p2.add(icode);
		p2.add(s);p2.add(srno);
		p2.setBorder(BorderFactory.createTitledBorder("Enter Your Data"));
        e2=new JLabel(new ImageIcon("x.gif"));
        e3=new JLabel(new ImageIcon("x.gif"));
        e2.setVisible(false);
        e3.setVisible(false);
		done=new JButton("Add");
		status=new JLabel("Not Ready");
		status.setForeground(new Color(0,120,0));

        p1.setBounds(300,2,500,80);
        p2.setBounds(300,200,500,250);
        e2.setBounds(810,300,60,60);
        e3.setBounds(810,390,60,60);
        done.setBounds(570,470,60,30);
        status.setBounds(700,470,60,30);

		    add(p1);
            add(p2);add(e2);add(e3);
      		add(done);add(status);
item.addTextListener(this);
icode.addTextListener(this);
srno.addTextListener(this);
done.addActionListener(this);

	}

	public void actionPerformed(ActionEvent ae)
  {
	if(ae.getSource()==done)
	{

		      if(item.getText().equals("") || icode.getText().equals("") || srno.getText().equals(""))
	     	  JOptionPane.showMessageDialog(this," Blank entry. All blanks must be filled","Incomplete Submit",JOptionPane.WARNING_MESSAGE);

	          else if(!errc.equals("") || !errs.equals(""))
	           {
	           JOptionPane.showMessageDialog(this,errc+errs,"Not possible",JOptionPane.WARNING_MESSAGE);
	               if(!errc.equals(""))
	               { srno.setText(""); 	icode.setText("");}
	               else
	               srno.setText("");
		         }

         	  else
         	   {
				   try{
           		DbConnector.insert(item.getText(),Integer.parseInt(icode.getText()),Integer.parseInt(srno.getText()));
          		JOptionPane.showMessageDialog(this,"Done !");
          		srno.setText("");
           		icode.setText("");
         		item.setText("");
			        }
			        catch(Exception e)
			        {
						JOptionPane.showMessageDialog(this,"Error : "+e.getMessage());
					}

               	}
	  }
	}


public void textValueChanged(TextEvent e)
{
	if(e.getSource()==item)
	{
		int temp=DbConnector.icheck(item.getText());
		if(temp!=-1)
		{
			icode.setText(temp+"");
			icode.setEditable(false);
		}
		else
		{
			icode.setText("");
			icode.setEditable(true);

		}
	}


   else if(e.getSource()==icode)
	{
		e2.setVisible(false);
      if(!icode.getText().equals("") && icode.isEditable()==true)
      {
		  if(item.getText().equals(""))
		  {
			  JOptionPane.showMessageDialog(this,"First fill Item Entry");
			  icode.setText("");
		  }
		  else
		  {
		if(!DbConnector.ccheck(Integer.parseInt(icode.getText())))
		{	  errc="this code is already assigned to another item\n"; e2.setVisible(true);}
        else
        errc="";
           }
	   }
     }

   else
   {
	      e3.setVisible(false);
	   if(!srno.getText().equals(""))
	   {
	      if(item.getText().equals("") || icode.getText().equals(""))
           {
		  JOptionPane.showMessageDialog(this,"First fill above entries");
		  srno.setText("");
	       }
          else
   	        {
               if(!DbConnector.scheck(Integer.parseInt(icode.getText()),Integer.parseInt(srno.getText())))
               {  errs="Serial no cannot be same of two similar items";  e3.setVisible(true); }
               else
                errs="";
             }
		 }
   }

  if(!srno.getText().equals("")  && e2.isVisible()==false && e3.isVisible()==false)
   status.setText("Ready.");
   else
  status.setText("Not Ready");
}
}
