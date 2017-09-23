import java.awt.*;

class Set
{
public static Point size(Dimension d1)
{

	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d2 = tk.getScreenSize();                                                    // screen rsolution   size

	Point p=new Point((d2.width-d1.width)/2,(d2.height-d1.height)/2);                        // Centre point(x,y)

return p;
}
}