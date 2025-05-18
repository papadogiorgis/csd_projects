package mainClasses;

public class Cust{
    private int cust_id;
    private String name;
    private String email;

    public Cust(String name, String email){
        this.name=name;
        this.email=email;
    }

    public int getCust_id(){
        return cust_id;
    }

    public void setCust_id(int cust_id){
        this.cust_id=cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }
}