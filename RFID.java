package tracking;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.table.DefaultTableModel;
public class RFID {
	private String PORT_NAME;
	private static final int TIME_OUT = 2000;
	private int DATA_RATE;
	private SerialPort serialPort;
	DefaultTableModel dtm;
	int floor;
public RFID(DefaultTableModel dtm,int floor){
	this.dtm = dtm;
	this.floor = floor;
}
public void setPort(String PORT_NAME){
	this.PORT_NAME = PORT_NAME;
}
public void setRate(int DATA_RATE){
	this.DATA_RATE = DATA_RATE;
}
public void initialize(){
	CommPortIdentifier portId = null;
	try{
		portId = CommPortIdentifier.getPortIdentifier(PORT_NAME);
	}catch (NoSuchPortException e){
		e.printStackTrace();
	}
	try{
		this.serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
		this.serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
		SerialPort.PARITY_NONE);
		this.serialPort.addEventListener(new MyListener(this.serialPort,dtm,floor));
		this.serialPort.notifyOnDataAvailable(true);
	}catch (Exception e){
		e.printStackTrace();
	}
}

public synchronized void close(){
	
	if (this.serialPort != null) {
		this.serialPort.removeEventListener();
		this.serialPort.close();
	}
	
}
}
class MyListener implements SerialPortEventListener{
	private final SerialPort port;
	DefaultTableModel dtm;
	StringBuilder builder = new StringBuilder();
	int floor;
public MyListener(SerialPort port,DefaultTableModel dtm,int floor){
	super();
	this.port = port;
	this.dtm = dtm;
	this.floor = floor;
}
public void serialEvent(SerialPortEvent event){
	if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
		try{
			int available = this.port.getInputStream().available();
			byte chunk[] = new byte[available];
			this.port.getInputStream().read(chunk, 0, available);
			String input=new String(chunk);
			builder.append(input);
			writeLog();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
public void writeLog(){
	try{
		System.out.println(builder.toString());
		if(builder.length() == 12){
			int block = Database.getParkingBlock(floor);
			if(block != -1){
				String msg = Database.doParking(floor,block,builder.toString());
				String arr[] = msg.split(",");
				if(!arr[0].equals("error")){
					Object row[] = {arr[0],builder.toString(),arr[1],floor+"-->"+block,arr[2]};
					dtm.addRow(row);
				}
				builder.delete(0,builder.length());
			}else{
				String arr[]={"Parking full","","","",""};
				dtm.addRow(arr);
				builder.delete(0,builder.length());
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
