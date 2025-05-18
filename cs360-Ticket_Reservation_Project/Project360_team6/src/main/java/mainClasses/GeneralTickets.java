package mainClasses;

public class GeneralTickets {
	private int generaltickets_id;
	private int reserv_id;
	private int event_id;
	private String seat_no;
	private boolean availability;
	
	public GeneralTickets(int new_event_id, String new_seatno) {
		this.reserv_id=0;
		this.event_id=new_event_id;
		this.seat_no=new_seatno;
		this.availability=true;
	}
	
	public GeneralTickets() {}
	
	public void set_gentick_id(int new_gentick_id) {
		this.generaltickets_id=new_gentick_id;
	}
	
	public int get_gentick_id() {return this.generaltickets_id;}
	
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