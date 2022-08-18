
import java.util.ArrayList;

public class Queue<Token> extends ArrayList<Token> {

    public void enqueue(Token element){//because token implements comparable class at every moment that ve sorted the queue it will pretend like it is a priority queue

        super.add(element);  //add the element at the end of the queue

    }
    public void dequeueAll(ArrayList<Token> removeArr){

        for (Token token: removeArr){

            this.dequeue(token);


        }

    }

    public void dequeue(){

        this.remove(0);


    }
    public void dequeue(Token token){

        this.remove(token);


    }



}

