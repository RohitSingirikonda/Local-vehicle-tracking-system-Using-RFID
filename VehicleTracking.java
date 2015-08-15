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
public class VehicleTracking extends JFrame{
	JPanel p1,p2,p3;
	JButton b1,b2;
	Font f1;
	JLabel l1;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	int floor;
	RFID rfid;
public VehicleTracking(int fl){
	super("Vehicle Tracking");
	floor = fl;
	f1 = new Font("Courier New",Font.BOLD,14);
	p1 = new TitlePanel(600,60);
	p1.setBackground(new Color(204, 110, 155));
	l1 = new JLabel("<HTML><BODY><CENTER>RFID BASED LOCAL VEHICLE TRACKING SYSTEM</CENTER></BODY></HTML>");
	l1.setFont(new Font("Courier New",Font.BOLD,16));
	p1.add(l1);
	l1.setForeground(Color.white);

	p2 = new JPanel();
	p2.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setFont(f1);
	table.setRowHeight(30);
	table.getTableHeader().setFont(new Font("Courier New",Font.BOLD,15));
	dtm.addColumn("Owner Name");
	dtm.addColumn("Vehicle No");
	dtm.addColumn("Parking Time");
	dtm.addColumn("Parked Location");
	dtm.addColumn("Status");
	jsp = new JScrollPane(table);
	p2.add(jsp,BorderLayout.CENTER);

	p3 = new JPanel();
	b1 = new JButton("Administrator");
	b1.setFont(f1);
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Login login = new Login();
			login.setVisible(true);
			login.setSize(600,360);
			login.setLocationRelativeTo(null);
			login.setResizable(false);
			login.clear();
		}
	});

	b2 = new JButton("Track Vehicle");
	b2.setFont(f1);
	p3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			TrackVehicle tv = new TrackVehicle();
			tv.setVisible(true);
			tv.setSize(800,600);
		}
	});
	p2.add(p3,BorderLayout.NORTH);
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);

	rfid = new RFID(dtm,floor);
	rfid.setPort("COM1");
	rfid.setRate(9600);
	rfid.initialize();
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
	int fl = 0;
	String floor = JOptionPane.showInputDialog(null,"Enter floor no");
	try{
		fl = Integer.parseInt(floor.trim());
	}catch(NumberFormatException nfe){
		JOptionPane.showMessageDialog(null,"Building floor must be numeric value");
		return;
	}
	VehicleTracking vt = new VehicleTracking(fl);
	vt.setVisible(true);
	vt.setExtendedState(JFrame.MAXIMIZED_BOTH);
}
}