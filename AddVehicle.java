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
public class AddVehicle extends JFrame{
	JPanel p1;
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3,tf4;
	JButton b1,b2,b3;
	Font f1;
	JComboBox c1;
public AddVehicle(){
	super("Add Vehicle Screen");
	p1 = new JPanel();
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,14);

	l1 = new JLabel("Owner Name");
	l1.setFont(f1);
	l1.setBounds(50,10,100,30);
	p1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	tf1.setBounds(150,10,130,30);
	p1.add(tf1);
	
	l2 = new JLabel("Vehicle No");
	l2.setFont(f1);
	l2.setBounds(50,60,100,30);
	p1.add(l2);
	tf2 = new JTextField(15);
	tf2.setFont(f1);
	tf2.setBounds(150,60,130,30);
	p1.add(tf2);

	l3 = new JLabel("Vehicle Type");
	l3.setFont(f1);
	l3.setBounds(50,110,100,30);
	p1.add(l3);
	c1 = new JComboBox();
	c1.setFont(f1);
	c1.setBounds(150,110,130,30);
	p1.add(c1);
	c1.addItem("Car");

	l4 = new JLabel("Contact No");
	l4.setFont(f1);
	l4.setBounds(50,160,100,30);
	p1.add(l4);
	tf3 = new JTextField(15);
	tf3.setFont(f1);
	tf3.setBounds(150,160,130,30);
	p1.add(tf3);

	l5 = new JLabel("Address");
	l5.setFont(f1);
	l5.setBounds(50,210,100,30);
	p1.add(l5);
	tf4 = new JTextField(15);
	tf4.setFont(f1);
	tf4.setBounds(150,210,180,30);
	p1.add(tf4);

	b1 = new JButton("Add Vehicle");
	b1.setFont(f1);
	b1.setBounds(75,260,150,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			addVehicle();
		}
	});
		
	getContentPane().add(p1,BorderLayout.CENTER);
}

public void clear(){
	tf1.setText("");
	tf2.setText("");
	tf3.setText("");
	tf4.setText("");
}
public void addVehicle(){
	String owner = tf1.getText();
	String vno = tf2.getText();
	String type = c1.getSelectedItem().toString();
	String contact = tf3.getText();
	String address = tf4.getText();
	if(owner == null || owner.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Owner name must be enter");
		tf1.requestFocus();
		return;
	}
	if(vno == null || vno.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Vehicle no must be enter");
		tf2.requestFocus();
		return;
	}
	if(contact == null || contact.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Contact no must be enter");
		tf3.requestFocus();
		return;
	}
	if(address == null || address.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Address must be enter");
		tf4.requestFocus();
		return;
	}
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/local_vehicle_tracking","root","root");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select vehicle_no from addvehicle where vehicle_no='"+vno+"'");
		if(rs.next()){
			JOptionPane.showMessageDialog(this,"Vehicle no already exist");
		}else{
			PreparedStatement stat=con.prepareStatement("insert into addvehicle values(?,?,?,?,?)");
			stat.setString(1,owner);
			stat.setString(2,vno);
			stat.setString(3,type);
			stat.setString(4,contact);
			stat.setString(5,address);
			int i=stat.executeUpdate();
			if(i > 0){
				JOptionPane.showMessageDialog(this,"Vehicle details added");
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
