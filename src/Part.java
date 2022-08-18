public class Part {

    private String name;
    private Stack<Item> items;

    public Part(String name) {

        this.name = name;
        items = new Stack<>();

    }

    public Stack<Item> getItems() {
        return items;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {

        String str = name+":";

        for (Item item:items){

            str+="\n"+item.getId();

        }

        if (items.size()==0){
            str+="\n";
        }
        str += "\n---------------";
        return str;

    }
}
