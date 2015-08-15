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
import javax.swing.JTextField;
public class TrackVehicle extends JFrame{
	JPanel p1,p2,p3;
	JButton b1;
	JLabel l1;
	JTextField tf1;
	Font f1;
	DefaultTableModel dtm;
	JScrollPane jsp;
	JTable table;
public TrackVehicle(){
	super("Vehicle Tracking");
	f1 = new Font("Courier New",Font.BOLD,14);
	p1 = new TitlePanel(600,60);
	p1.setBackground(new Color(204, 110, 155));
	l1 = new JLabel("<HTML><BODY><CENTER>RFID BASED LOCAL VEHICLE TRACKING SYSTEM</CENTER></BODY></HTML>");
	l1.setFont(new Font("Courier New",Font.BOLD,16));
	p1.add(l1);
	l1.setForeground(Color.white);

	p3 = new JPanel();
	l1 = new JLabel("Vehicle No");
	l1.setFont(f1);
	p3.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	p3.add(tf1);

	b1 = new JButton("Track");
	b1.setFont(f1);
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			for(int i=dtm.getRowCount()-1;i>=0;i--){
				dtm.removeRow(i);
			}
			String vno = tf1.getText();
			if(vno == null || vno.trim().length() <= 0){
				JOptionPane.showMessageDialog(TrackVehicle.this,"Vehicle no must be enter");
				tf1.requestFocus();
				return;
			}
			try{
				String records[] = Database.track(vno);
				for(String str : records){
					String row[] = str.split(",");
					dtm.addRow(row);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});

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
	dtm.addColumn("Parked Location");
	dtm.addColumn("In Time");
	dtm.addColumn("Out Time");
	dtm.addColumn("Status");
	jsp = new JScrollPane(table);
	p2.add(p3,BorderLayout.NORTH);
	p2.add(jsp,BorderLayout.CENTER);

	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);

}
}