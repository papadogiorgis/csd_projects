package mainClasses;


public class Card{
    private int cust_id;
    private String card_number;
    private String card_exp_date;
    private String cvv;

    public Card(int cust_id,String card_number,String card_exp_date,String cvv){
        this.cust_id=cust_id;
        this.card_number=card_number;
        this.card_exp_date=card_exp_date;
        this.cvv=cvv;
    }

    public int getCust_id(){
        return cust_id;
    }

    public void setCust_id(int cust_id){
        this.cust_id=cust_id;
    }

    public String getCard_number(){
        return card_number;
    }

    public void setCard_number(String card_number){
        this.card_number=card_number;
    }

    public String getCard_exp_date(){
        return card_exp_date;
    }

    public void setCard_exp_date(String card_exp_date){
        this.card_exp_date=card_exp_date;
    }

    public String getCvv(){
        return cvv;
    }

    public void setCvv(String cvv){
        this.cvv=cvv;
    }
}