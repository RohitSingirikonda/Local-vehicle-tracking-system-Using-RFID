package tracking;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
public class AddParkingFloors extends JFrame{
	JPanel p1;
	JLabel l1,l2;
	JTextField tf1,tf2;
	JButton b1;
	Font f1;
	JComboBox c1;
public AddParkingFloors(){
	super("Add Vehicle Screen");
	p1 = new JPanel();
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,14);

	l1 = new JLabel("Building Floors");
	l1.setFont(f1);
	l1.setBounds(50,10,250,30);
	p1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	tf1.setBounds(250,10,130,30);
	p1.add(tf1);
	
	l2 = new JLabel("Total Blocks");
	l2.setFont(f1);
	l2.setBounds(50,60,250,30);
	p1.add(l2);
	tf2 = new JTextField(15);
	tf2.setFont(f1);
	tf2.setBounds(250,60,130,30);
	p1.add(tf2);

	
	b1 = new JButton("Add Parking");
	b1.setFont(f1);
	b1.setBounds(100,110,150,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			addParking();
		}
	});
		
	getContentPane().add(p1,BorderLayout.CENTER);
}

public void clear(){
	tf1.setText("");
	tf2.setText("");
}
public void addParking(){
	String floors = tf1.getText();
	String block = tf2.getText();
	if(floors == null || floors.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Building floor must be enter");
		tf1.requestFocus();
		return;
	}
	if(block == null || block.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Blocks size must be enter");
		tf2.requestFocus();
		return;
	}
	int floor_size = 0;
	int block_size = 0;
	int count = 0;
	try{
		floor_size = Integer.parseInt(floors.trim());
	}catch(NumberFormatException nfe){
		JOptionPane.showMessageDialog(this,"Building floor must be numeric value");
		tf1.requestFocus();
		return;
	}
	try{
		block_size = Integer.parseInt(block.trim());
	}catch(NumberFormatException nfe){
		JOptionPane.showMessageDialog(this,"Blocks size must be numeric value");
		tf2.requestFocus();
		return;
	}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/local_vehicle_tracking","root","root");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select count(*) from addparking where building_floors='"+floors+"' and blocks='"+block+"'");
		if(rs.next()){
			count = rs.getInt(1);
		}
		if(count > 0){
			JOptionPane.showMessageDialog(this,"Floor details already exist");
		}
		else{
			PreparedStatement stat=con.prepareStatement("insert into addparking values(?,?)");
			stat.setInt(1,floor_size);
			stat.setInt(2,block_size);
			int i=stat.executeUpdate();
			if(i > 0){
				JOptionPane.showMessageDialog(this,"Building parking details added");
				clear();
			}
			stat.close();
		}
		rs.close();stmt.close();con.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}

}
