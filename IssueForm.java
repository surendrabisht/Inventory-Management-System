import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class IssueForm extends JPanel implements ActionListener
{

            JButton ibysr,ibypr;
            JPanel p1,p2;
	IssueForm()
	{
                                                           // setlayout(null);
            setLayout(new BorderLayout());

		p1=new JPanel();
		JLabel title=new JLabel("Issue Using :->  ");
         title.setFont(new Font("TimesNewRoman",Font.ITALIC,20));
         title.setForeground(new Color(225,0,0));
		ibysr=new JButton("Input From");
		ibypr=new JButton("Select Option");

		ibysr.addActionListener(this);
		ibypr.addActionListener(this);
		p1.add(title);
		p1.add(ibysr);
		p1.add(ibypr);


                                                           // p1.setBounds(300,2,200,40);

        add(p1,"North");
        p2=new JPanel();
                                                            //  p2.setBounds(0,100,1000,1200);
       add(p2);
	}


  public void actionPerformed(ActionEvent ae)
 {

	remove(p2);
     if(ae.getSource()==ibysr)
     p2=new Issue1();
     else
     p2=new Issue2();

     add(p2);
                                                             //p2.setBounds(0,100,1000,1200);
 }
}
