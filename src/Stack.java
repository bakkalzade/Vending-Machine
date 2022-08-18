import java.util.ArrayList;

public class Stack<T> extends ArrayList<T> {


    public void push(T element) {

        super.add(0,element);

    }
    public void pop(){
        this.remove(0);
    }
}
