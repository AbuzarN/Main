//Syed Naqvi
//SAN190003
public class Node
{
    private Node next;
    private Node Down;
    private Seat Payload;

    // initalize node (takes in Row, Seat, and ticket type) and creates a seat object as well as links to the next node and node benath (if needed)
    public Node (int r, char s, char t)
    {
        Payload= new Seat (r,s,t);
        next =null;
        Down = null;
    }
    //setter for Payload
    public void setPayload(Seat A)
    {
        Payload = A;
    }
    //getter for Payload
    public Seat getPayload()
    {
        return Payload;
    }
    //setter for nexnode
    public void setNext(Node n)
    {
        next = n;
    }
    //getter for nexnode
    public Node getNext()
    {
        return next;
    }
    //setter for downnode
    public void setDown(Node n)
    {
        Down = n;
    }
    //getter for downnode
    public Node getDown()
    {
        return Down;
    }
    //returns the payloads toString method
    public String toString()
    {
        return getPayload().toString();
    }
}
