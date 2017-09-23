import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;   // for arrayList data structures

class Issue1 extends JPanel implements ActionListener,TextListener
{
            JLabel title,l1,l2,l3,error;
			TextField person,srno;
			Choice item;
			JButton done,ends;
	Issue1()
	{
			JPanel p1,p2;

            setLayout(null);

		p1=new JPanel();
		title=new JLabel("Issue Form");
		title.setFont(new Font("TimesNewRoman",Font.BOLD+Font.ITALIC,30));
		p1.setBorder(BorderFactory.createLineBorder(Color.GREEN,10));
		p1.add(title);
		p2= new JPanel();
		p2.setBorder(BorderFactory.createTitledBorder("Entry"));
        l1=new JLabel("Candidate :");
		l2=new JLabel("srno :");
		l3=new JLabel("item : ");
		person=new TextField(30);
		srno=new TextField(30);
        item=new Choice();
        p2.setLayout(new GridLayout(3,2,30,50));
        p2.add(l1);p2.add(person);
		p2.add(l2);p2.add(srno);
		p2.add(l3);p2.add(item);

        done=new JButton("Issue");
        ends=new JButton("End Session");
        error=new JLabel(new ImageIcon("x.gif"));
         p1.setBounds(300,2,500,80);
         p2.setBounds(300,200,500,250);
         error.setBounds(810,300,60,60);
         done.setBounds(900,300,100,40);
         ends.setBounds(1020,300,150,40);

        ends.setVisible(false);
        error.setVisible(false);
        srno.addTextListener(this);
		done.addActionListener(this);
		ends.addActionListener(this);
        add(p1);
        add(p2);
        add(done);
        add(ends);
        add(error);

	}


  public void actionPerformed(ActionEvent ae)
  {
	  if(ae.getSource()==done)
	  {
		       if(person.getText().equals("") || srno.getText().equals("") || item.getItemCount()==0)
	     	  JOptionPane.showMessageDialog(this," Blank entry detected . All blanks must be filled","Error",JOptionPane.WARNING_MESSAGE);
	     	  else
	     	  {
				  try{
				             	   DbConnector.issue(person.getText(),Integer.parseInt(srno.getText()),item.getSelectedItem());
				            		JOptionPane.showMessageDialog(this,"Done !");
				             		srno.setText("");
				             		person.setEditable(false);
                                     ends.setVisible(true);

				  			        }
				  			        catch(Exception e)
				  			        {
				  						JOptionPane.showMessageDialog(this,"Error : "+e.getMessage());}
			   }
	  }

	  else
	  {
		   ends.setVisible(false);
		   person.setEditable(true);
		   person.setText("");
		   srno.setText("");
	  }

  }
  public void textValueChanged(TextEvent te)
  {
	     item.removeAll();
	     if(!srno.getText().equals(""))
	     {
		    try{
            ArrayList<String> list=DbConnector.getItem(Integer.parseInt(srno.getText()));
                 for(String str : list)
	  	         	item.add(str);
	  	         	if(item.getItemCount()==0)
	  	         	  error.setVisible(true);
	  	         	  else
	  	         	    error.setVisible(false);
	            }
	  	    catch(Exception e)
	  	     {error.setVisible(true);}

	      }
      else
       error.setVisible(false);
	}



	/*public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
	}*/
}
