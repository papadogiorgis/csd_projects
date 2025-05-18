package mainClasses;

public class VIPTickets {
	private int viptickets_id;
	private int reserv_id;
	private int event_id;
	private String seat_no;
	private boolean availability;
	
	public VIPTickets(int new_event_id, String new_seatno) {
		this.reserv_id=0;
		this.event_id=new_event_id;
		this.seat_no=new_seatno;
		this.availability=true;
	}
	
	public VIPTickets() {}
	
	public void set_viptick_id(int new_viptick_id) {
		this.viptickets_id=new_viptick_id;
	}
	
	public int get_gentick_id() {return this.viptickets_id;}
	
	public void set_reserv_id(int new_reserv_id) {
		this.reserv_id=new_reserv_id;
	}
	
	public int get_reserv_id() {return this.reserv_id;}
	
	public void set_event_id(int new_event_id) {
		this.event_id=new_event_id;
	}
	
	public int get_event_id() {return this.event_id;}
	
	public void set_seatno(String new_seatno) {
		this.seat_no=new_seatno;
	}
	
	public String get_seatno() {return this.seat_no;}
	
	public void set_availability(boolean new_availability) {
		this.availability=new_availability;
	}
	
	public boolean get_availability() {return this.availability;}
}