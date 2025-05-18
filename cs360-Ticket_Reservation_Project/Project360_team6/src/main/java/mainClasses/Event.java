package mainClasses;




public class Event {

    /**
     * Attributes
     */
    private int event_id;
    private String event_name;
    private String event_date;
    private String event_time;
    private String event_type;
    private int event_capacity;
    private int event_vip_capacity;
    private int event_general_capacity;
    private int event_vip_price;
    private int event_general_price;

    /**
     * Setters
     */

    public void setId(int event_id){
        this.event_id = event_id;
    }

    public void setName(String event_name){
        this.event_name = event_name;
    }

    public void setDate(String event_date){
        this.event_date = event_date;
    }

    public void setTime(String event_time){
        this.event_time = event_time;
    }

    public void setType(String event_type){
        this.event_type = event_type;
    }

    public void setCapacity(int event_capacity){
        this.event_capacity = event_capacity;
    }

    public void setVipCapacity(int event_vip_capacity){
        this.event_vip_capacity = event_vip_capacity;
    }

    public void setGeneralCapacity(int event_general_capacity){
        this.event_general_capacity = event_general_capacity;
    }

    public void setVipPrice(int event_vip_price){
        this.event_vip_price = event_vip_price;
    }

    public void setGeneralPrice(int event_general_price){
        this.event_general_price = event_general_price;
    }


    /**
     * Getters
     */

    public int getId(){
        return this.event_id;
    }

    public String getName(){
        return this.event_name;
    }

    public String getDate(){
        return this.event_date;
    }

    public String getTime(){
        return this.event_time;
    }

    public String getType(){
        return this.event_type;
    }

    public int getCapacity(){
        return this.event_capacity;
    }

    public int getVipCapacity(){
        return this.event_vip_capacity;
    }

    public int getGeneralCapacity(){
        return this.event_general_capacity;
    }

    public int getVipPrice(){
        return this.event_vip_price;
    }

    public int getGeneralPrice(){
        return this.event_general_price;
    }

    /**
     * Constructor
     */

    public Event(String event_name, String event_date, String event_time, String event_type, int event_capacity,
    int event_vip_capacity, int event_general_capacity, int event_vip_price, int event_general_price){
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_type = event_type;
        this.event_capacity = event_capacity;
        this.event_vip_capacity = event_vip_capacity;
        this.event_general_capacity = event_general_capacity;    
        this.event_vip_price = event_vip_price;
        this.event_general_price = event_general_price;       
    }
}