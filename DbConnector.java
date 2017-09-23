import java.sql.*;
import java.util.*;
import java.text.*;		//Format Dates & Numbers
class DbConnector
{

static Connection con;
static	 PreparedStatement pst;
static	 ResultSet rs;
static int dayslimit=10;
static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");  // used for purpose of conversion of string to date obj
static Calendar cal = Calendar.getInstance();

public static void driverSetup(Connection c)
{
	con=c;
}
public static int icheck(String item)
{

	 int icode=-1;
	 try{
	   pst= con.prepareStatement("select * from Records where item=?");
	pst.setString(1,item);
	   rs = pst.executeQuery();
	        if(rs.next())
	          icode=rs.getInt("icode");
           else
           icode=-1;    // will not be set
	   }
	   catch(Exception e)
	   {}
        return icode;
}

public static boolean ccheck(int icode)
{

	boolean tf=false;
	try
	{
		pst= con.prepareStatement("select * from Records where icode=?");
	   pst.setInt(1,icode);
	   rs = pst.executeQuery();
	        if(rs.next())
	        tf=false;
	        else
	        tf=true;
	}
	catch(Exception e)
	{
		System.out.println(e+" in c check");tf=true;}
	return tf;
 }

public static boolean scheck(int icode,int srno)
{
	boolean tf=false;
		try
		{
			pst= con.prepareStatement("select * from Records where icode=? AND srno=?");
		   pst.setInt(1,icode);
		   pst.setInt(2,srno);
		   rs = pst.executeQuery();
	        if(rs.next())
            tf=false;
	        else
	        tf=true;
	}
	catch(Exception e)
	{ System.out.println(e+" in c check");tf=true;}
	return tf;
 }

public static void insert(String item,int icode,int srno)throws Exception
{

	pst=con.prepareStatement("insert into Records(item,icode,srno) Values(?,?,?)");
	pst.setString(1,item);
	pst.setInt(2,icode);
	pst.setInt(3,srno);
	pst.executeUpdate();

}


public static ArrayList<String> getItem(int srno)
{
	ArrayList<String> a=new ArrayList<String>();
	a.clear();
	try{
	pst=con.prepareStatement("select * from Records where srno=?  AND uname IS NULL ");
	pst.setInt(1,srno);
	rs = pst.executeQuery();
	 while(rs.next())
	 {
		 a.add(rs.getString("item"));
	 }
       }
       catch(Exception e){}
	return a;
}


public static void issue(String uname,int srno,String item)throws Exception
{
    	pst=con.prepareStatement("update Records SET uname=?,issue_Date=?,return_Date=?  where srno=? AND item=?");
		pst.setString(1,uname);

		// Date Entry Insertion
	             	java.util.Date id =new java.util.Date();    //  Issuing current date
		            id =DbConnector.setCalendar(id);
				       Calendar c = Calendar.getInstance();
				 		c.setTimeInMillis(id.getTime()+(1000*60*60*24*dayslimit));
                     java.util.Date fd=c.getTime();        // final returning date.

	    pst.setString(2,id.toString());
   	    pst.setString(3,fd.toString());
		pst.setInt(4,srno);
		pst.setString(5,item);
		pst.executeUpdate();
}

public static ArrayList<String> getAllItems()
{
	ArrayList<String> a=new ArrayList<String>();
	try{
	pst=con.prepareStatement("select DISTINCT item from Records");
	rs = pst.executeQuery();
	 while(rs.next())
		 {
			 a.add(rs.getString("item"));
		 }
	    }
	       catch(Exception e){ e.printStackTrace(); }
	return a;
}

public static ArrayList<String> getSrno(String item)
{
	ArrayList<String> a=new ArrayList<String>();
		try{
		pst=con.prepareStatement("select srno from Records where item=?  AND uname IS NULL ");
		pst.setString(1,item);
		rs = pst.executeQuery();
		 while(rs.next())
		 {
			 a.add(""+rs.getInt("srno"));
		 }
	       }
	       catch(Exception e){ e.printStackTrace(); }
	return a;
}

public static ArrayList<String> getallC(String person)
{
	ArrayList<String> a=new ArrayList<String>();  double fine=0;
	a.add(person);
		try{
		pst=con.prepareStatement("select * from Records where uname=?");
		pst.setString(1,person);
		rs = pst.executeQuery();
		 while(rs.next())
			 {
				 a.add(""+rs.getInt("icode"));
				 a.add(rs.getString("item"));
				 a.add(""+rs.getInt("srno"));
                    cal.setTime(sdf.parse(rs.getString("issue_Date")));
		         a.add(df.format(cal.getTime()));
		              java.util.Date fd=sdf.parse(rs.getString("return_Date"));     cal.setTime(fd);
                 a.add(df.format(cal.getTime()));



                    	java.util.Date rd =new java.util.Date();         //      current date  for returning
				 		rd =DbConnector.setCalendar(rd);
				        if(rd.getTime()>fd.getTime())
                          fine=(rd.getTime()-fd.getTime())/(1000*60*60*24)*20;
                        else
                          fine=0;
                 a.add(""+fine);
			 }
		    }
		       catch(Exception e){e.printStackTrace();}
	return a;
}

public static ArrayList<String> getallCbyS(String text)
{
	ArrayList<String> a=new ArrayList<String>();
	try{
	StringTokenizer st = new StringTokenizer(text,",/");
	String srno= st.nextToken();
	String item=st.nextToken();
		pst=con.prepareStatement("select * from Records where srno=? AND item=?");
		pst.setInt(1,Integer.parseInt(srno));
		pst.setString(2,item);
		rs = pst.executeQuery();
		 if(rs.next())
		  a= DbConnector.getallC(rs.getString("uname"));
		  else
			   a.add("[srno/item] "+text+" not found");
		    }
		       catch(Exception e){e.printStackTrace();a.add("[srno/item] "+text+" not found. correct Format(sr/item OR sr,item)");}
	return a;
}



public static void returnItem(int icode,int srno )
{
	try{
	    pst=con.prepareStatement("update Records SET uname=NULL,issue_Date=NULL,return_Date=NULL  where icode=? AND srno=?");
		pst.setInt(1,icode);
		pst.setInt(2,srno);
		pst.execute();}
		catch(Exception e) {  e.printStackTrace();}
}

public static ArrayList<String> viewbyU()
{

	System.out.println("list sort by user ");
	ArrayList<String> a=new ArrayList<String>();
			try{
		pst=con.prepareStatement("select * from Records order by uname");
		rs = pst.executeQuery();
		 while(rs.next())
					 {
						                         a.add(""+rs.getInt("icode"));
						 					     a.add(rs.getString("item"));
						 						 a.add(""+rs.getInt("srno"));
						 	String id=rs.getString("issue_Date");
						 	String fd=rs.getString("return_Date");
		     				 						 if(id==null)
								 				 a.add("-");
								 						 else
                                                         {   cal.setTime(sdf.parse(id));
		                                         a.add(df.format(cal.getTime())); }
		                                             if(fd==null)
                                                  a.add("-");
                                                         else
                                                         {  cal.setTime(sdf.parse(fd));
                                                 a.add(df.format(cal.getTime())); }
						                         a.add(rs.getString("uname"));

					 }
				 }
				 catch(Exception e){
					  e.printStackTrace();   }
			 return a;
}


public static ArrayList<String> viewbyC()
{
	System.out.println("list sort by code ");
	ArrayList<String> a=new ArrayList<String>();
			try{
		pst=con.prepareStatement("select * from Records order by icode");
		rs = pst.executeQuery();
		 while(rs.next())
							 {
								          		                         a.add(""+rs.getInt("icode"));
										  						 					     a.add(rs.getString("item"));
										  						 						 a.add(""+rs.getInt("srno"));
										  						 	String id=rs.getString("issue_Date");
										  						 	String fd=rs.getString("return_Date");
										  		     				 						 if(id==null)
										  								 				 a.add("-");
										  								 						 else
										                                                           {   cal.setTime(sdf.parse(id));
										  		                                         a.add(df.format(cal.getTime())); }
										  		                                             if(fd==null)
										                                                    a.add("-");
										                                                           else
										                                                           {  cal.setTime(sdf.parse(fd));
										                                                   a.add(df.format(cal.getTime())); }
										  						                         a.add(rs.getString("uname"));

							 }
						 }
						 catch(Exception e){
							 e.printStackTrace();   }
			 return a;
}

public static ArrayList<String> viewbyS()
{
	System.out.println("list sort by serial ");
	ArrayList<String> a=new ArrayList<String>();
			try{
		pst=con.prepareStatement("select * from Records order by srno");
		rs = pst.executeQuery();
		 while(rs.next())
							 {
								           		                         a.add(""+rs.getInt("icode"));
										   						 					     a.add(rs.getString("item"));
										   						 						 a.add(""+rs.getInt("srno"));
										   						 	String id=rs.getString("issue_Date");
										   						 	String fd=rs.getString("return_Date");
										   		     				 						 if(id==null)
										   								 				 a.add("-");
										   								 						 else
										                                                            {   cal.setTime(sdf.parse(id));
										   		                                         a.add(df.format(cal.getTime())); }
										   		                                             if(fd==null)
										                                                     a.add("-");
										                                                            else
										                                                            {  cal.setTime(sdf.parse(fd));
										                                                    a.add(df.format(cal.getTime())); }
										   						                         a.add(rs.getString("uname"));

							 }
						 }
						 catch(Exception e){
							 e.printStackTrace();   }
			 return a;
}

public static java.util.Date setCalendar(java.util.Date d1)
{
	Calendar cal=Calendar.getInstance();
    cal.setTime(d1);
    // Set time fields to zero
                    cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
	      	        cal.set(Calendar.MILLISECOND, 0);
	      	        return cal.getTime();
}

}