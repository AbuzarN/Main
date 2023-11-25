//Syed Naqvi
//SAN190003
public class Seat {
    private int Row;
    private char Seat;
    private char TicketType;
    //inialize a seat object with the given parameters
    public Seat(int r, char s, char t)
    {
        Row=r;
        Seat=s;
        TicketType=t;

    }

    //TicketTypeSetter
    public void setTicketType(char ticketType) {
        TicketType = ticketType;
    }

    //row getter
    public int getRow()
    {
        return Row;
    }
    //steat getter
    public char getSeat()
    {
        return Seat;
    }
    //Tickettype getter
    public char getTicketType()
    {
        return TicketType;
    }
    //return row and string (seat position)
    //return Tickettype
    public String toString()
    {
        return TicketType+"";
    }
}
