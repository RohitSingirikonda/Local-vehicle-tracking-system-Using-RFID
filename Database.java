package tracking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Database{
    private static Connection con;
public static Connection getCon()throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    con = DriverManager.getConnection("jdbc:mysql://localhost/local_vehicle_tracking","root","root");
     return con;
}
public static int getParkingBlock(int floor)throws Exception{
    int free_block = 0;
	int total_block = 0;
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select blocks from addparking");
    if(rs.next()){
        total_block = rs.getInt(1);
    }
	rs.close();stmt.close();
    stmt=con.createStatement();
    rs=stmt.executeQuery("select count(block_no) from parking where floor_no='"+floor+"' and status='in'");
    if(rs.next()){
        int fill_block = rs.getInt(1);
		fill_block = fill_block + 1;
		if(fill_block <= total_block)
			free_block = fill_block;
		else
			free_block = -1;
    }
	rs.close();stmt.close();con.close();
	return free_block;
}
public static String[] track(String vno)throws Exception{
	String owner="none";
    StringBuilder sb = new StringBuilder();
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select owner_name from addvehicle where vehicle_no='"+vno+"'");
	if(rs.next()){
        owner = rs.getString(1);
    }
	rs.close();stmt.close();
	if(!owner.equals("none")){
		stmt = con.createStatement();
		rs = stmt.executeQuery("select * from parking where vehicle_no='"+vno+"'");
		while(rs.next()){
			sb.append(owner+","+vno+","+rs.getString(2)+"-->"+rs.getString(3)+","+rs.getString(4)+","+rs.getString(5)+","+rs.getString(6)+"\n");
		}
	}
	if(sb.length() > 0)
		sb.deleteCharAt(sb.length()-1);
	else
		sb.append("no record found");
	return sb.toString().split("\n");
}
public static String doParking(int floor,int block,String vno)throws Exception{
    String msg = "none";
	String owner = "";
	String status="";
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select status from parking where vehicle_no='"+vno+"' and floor_no='"+floor+"' and status !='out'");
	if(rs.next()){
        msg = rs.getString(1);
    }
	rs.close();stmt.close();
	stmt = con.createStatement();
	rs = stmt.executeQuery("select owner_name from addvehicle where vehicle_no='"+vno+"'");
	if(rs.next()){
		owner = rs.getString(1);
	}
	java.util.Date d1 = new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(d1.getTime());
	if(msg.equals("in")){
		PreparedStatement stat=con.prepareStatement("update parking set out_time=?,status=? where status=? and vehicle_no=?");
		stat.setTimestamp(1,time);
		stat.setString(2,"out");
		stat.setString(3,"in");
		stat.setString(4,vno);
		int i=stat.executeUpdate();
		if(i > 0){
			msg = "success";
			status = "OUT";
		}
	}else{
		PreparedStatement stat=con.prepareStatement("insert into parking values(?,?,?,?,?,?)");
		stat.setString(1,vno);
		stat.setInt(2,floor);
		stat.setInt(3,block);
		stat.setTimestamp(4,time);
		stat.setTimestamp(5,time);
		stat.setString(6,"in");
		int i=stat.executeUpdate();
		if(i > 0){
			msg = "success";
			status = "IN";
		}
	}
	if(msg.equals("success")){
		msg = owner+","+time.toString()+","+status;
	}else
		msg = "error";
    return msg;
}

}
