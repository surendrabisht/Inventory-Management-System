import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;   // for arrayList data structures

class Issue2 extends JPanel implements ActionListener
{
            JLabel title;
			TextField person;
            List item;
			Choice srno;
			JButton done,ends;
	Issue2()
	{
			JPanel p1,p2,p3,p4;

            setLayout(null);

		p1=new JPanel();
		title=new JLabel("Issue by selection");
		title.setFont(new Font("TimesNewRoman",Font.BOLD+Font.ITALIC,30));
		p1.setBorder(BorderFactory.createLineBorder(Color.BLUE,10));
		p1.add(title);

		p2= new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setBorder(BorderFactory.createTitledBorder("Enter Candidate name"));
		person=new TextField(30);
		p2.add(person);

		p3=new JPanel();
		p3.setBorder(BorderFactory.createTitledBorder("Select Item"));
        item=new List();
                 ArrayList<String> list=DbConnector.getAllItems();
                 for(String str : list)
                 item.add(str);
        p3.add(item);

        p4=new JPanel();
        p4.setLayout(new BorderLayout());
        p4.setBorder(BorderFactory.createTitledBorder("Select Serial no"));
        srno =new Choice();
        p4.add(srno);

         done=new JButton("Issue");
         ends=new JButton("End Session");
         p1.setBounds(300,2,500,80);
         p2.setBounds(100,200,250,60);
         p3.setBounds(500,200,200,250);
         p4.setBounds(800,200,100,80);
         done.setBounds(900,300,100,40);
         ends.setBounds(1020,300,150,40);

        item.addActionListener(this);
        ends.setVisible(false);
		done.addActionListener(this);
		ends.addActionListener(this);
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(done);
        add(ends);


	}


  public void actionPerformed(ActionEvent ae)
  {
	  if(ae.getSource()==done)
	  {
		       if(person.getText().equals("")  || srno.getItemCount()==0)
	     	     JOptionPane.showMessageDialog(this," Blank entry detected .Either name not written  or  Product out of stock..","Error",JOptionPane.WARNING_MESSAGE);
	     	  else
	     	  {
				               try{
				             	   DbConnector.issue(person.getText(),Integer.parseInt(srno.getSelectedItem()),item.getSelectedItem());
				            		JOptionPane.showMessageDialog(this,"Done !");
				             		person.setEditable(false);
                                     ends.setVisible(true);
				  			        }
				  			        catch(Exception e)
				  			        {
				  						JOptionPane.showMessageDialog(this,"Error : "+e.getMessage());}
			   }
	  }

        else if(ae.getSource()==item)
	      {
             srno.removeAll();
              ArrayList<String> sr=DbConnector.getSrno(item.getSelectedItem());
	            for(String str : sr)
                 srno.add(str);
             }
	  else
	  {
		   ends.setVisible(false);
		   person.setEditable(true);
		   person.setText("");

	  }

  }

}
