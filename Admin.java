package tracking;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
public class Admin extends JFrame{
	JPanel p1,p2,p3;
	JButton b1,b2;
	Font f1;
	JLabel l1;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
public Admin(){
	super("Vehicle Tracking");
	f1 = new Font("Courier New",Font.BOLD,14);
	p1 = new TitlePanel(600,60);
	p1.setBackground(new Color(204, 110, 155));
	l1 = new JLabel("<HTML><BODY><CENTER>RFID BASED LOCAL VEHICLE TRACKING SYSTEM</CENTER></BODY></HTML>");
	l1.setFont(new Font("Courier New",Font.BOLD,16));
	p1.add(l1);
	l1.setForeground(Color.white);

	p2 = new JPanel();
	b1 = new JButton("Add Vehicle Details");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			AddVehicle av = new AddVehicle();
			av.setVisible(true);
			av.setSize(350,400);
			av.clear();
		}
	});

	b2 = new JButton("Add Parking Details");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			AddParkingFloors ap = new AddParkingFloors();
			ap.setVisible(true);
			ap.setSize(450,200);
			ap.clear();
		}
	});

	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);
}

}