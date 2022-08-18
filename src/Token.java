public class Token implements Comparable<Token> {

    private String id;
    private String part;
    private int credit;

    public Token(String id, String part, int credit) {
        this.id = id;
        this.part = part;
        this.credit = credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public String getPart() {
        return part;
    }

    public int getCredit() {
        return credit;
    }

    public int use(Queue<Token> tokenBox,String part,int howMany){

        //arrange the new values

        if (this.credit<howMany){

            howMany = howMany-this.credit;
            this.credit=0;


        }else if(this.credit> howMany){

            this.credit= this.credit-howMany;//descent the credit of token
            howMany=0;



        }else {
            this.credit=0;
            howMany=0;


        }

        return howMany;


    }

    @Override
    public int compareTo(Token o) {

        return -Integer.compare(this.credit,o.credit);

    }

    @Override
    public String toString() {
        return id +" "+ part+" "+credit;
    }


}
