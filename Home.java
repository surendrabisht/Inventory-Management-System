import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Home extends JPanel
{
			JLabel title,pic;

	Home()
	{
			Panel p1,p2;



		p1=new Panel();
		title=new JLabel("Welcome to Inventory Management System");
		title.setFont(new Font("TimesNewRoman",Font.BOLD+Font.ITALIC,30));
		p1.add(title);

		p2= new Panel();

		pic=new JLabel(new ImageIcon("mid.jpg"));

		p2.add(pic);


       //  setBackground(Color.PINK);
		add(p1,"North");
      add(p2);
	}
}
