package mainClasses;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservations {
	private int reserv_id;
	private int cust_id;
	private int event_id;
	private int number_of_tickets_gen;
	private int number_of_tickets_vip;
	private String reserv_date;
	private double reserv_cost;
	
	public Reservations(int new_cust_id, int new_event_id, int new_numtick_gen, int new_numtick_vip) {
		this.cust_id=new_cust_id;
		this.event_id=new_event_id;
		this.number_of_tickets_gen=new_numtick_gen;
		this.number_of_tickets_vip=new_numtick_vip;
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.reserv_date = currentDate.format(formatter);
	}
	
	public void set_reserv_id(int new_reserv_id) {
		this.reserv_id=new_reserv_id;
	}
	
	public int get_reserv_id() {return this.reserv_id;}
	
	public void set_cust_id(int new_cust_id) {
		this.cust_id=new_cust_id;
	}
	
	public int get_cust_id() {return this.cust_id;}
	
	public void set_event_id(int new_event_id) {
		this.event_id=new_event_id;
	}
	
	public int get_event_id() {return this.event_id;}
	
	public void set_numtick_gen(int new_numtick_gen) {
		this.number_of_tickets_gen=new_numtick_gen;
	}
	
	public int get_numtick_gen() {return this.number_of_tickets_gen;}
	
	public void set_numtick_vip(int new_numtick_vip) {
		this.number_of_tickets_vip=new_numtick_vip;
	}
	
	public int get_numtick_vip() {return this.number_of_tickets_vip;}
	
	public void set_res_date(String new_res_date) {
		this.reserv_date=new_res_date;
	}
	
	public String get_res_date() {return this.reserv_date;}
	
	public void set_reserv_cost(double new_reserv_cost) {
		this.reserv_cost=new_reserv_cost;
	}
	
	public double get_reserv_cost() {return this.reserv_cost;}
}