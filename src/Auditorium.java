//import scanner neccessaries
//Syed Naqvi
//SAN190003
import java.util.*;
public class Auditorium {
    //set the head to null
    Node First=null;
    //list of alphabets to reference seat column
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //Auditorium stats
    private int row =0;
    private int seatcount=0;
    private int AdultTickets=0;
    private int SeniorTickets=0;
    private int ChildTickets=0;
    private int TotalTickets=0;
    private int TotalSeats=0;
    private double TotalSales=0;

    //A bunch of getters and setters
    public void setTotalTickets(int a)
    {
        TotalTickets = a;
    }
    public int getTotalSeats()
    {
        return TotalSeats;
    }
    public void setTotalSales(double a)
    {
        TotalSales=a;
    }
    public int getRow()
    {
        return row;
    }
    public int getSeatcount()
    {
        return seatcount;
    }
    public int getAdultTickets()
    {
        return AdultTickets;
    }
    public int getSeniorTickets()
    {
        return SeniorTickets;
    }
    public int getChildTickets()
    {
        return ChildTickets;
    }
    public int getTotalTickets()
    {
        return TotalTickets;
    }
    public double getTotalSales()
    {
        return TotalSales;
    }
    //auditorium constructors that reads the input file and stores each seat as a Node linked to the subsequent column and Row (if the node is the first in the row)
    public Auditorium(Scanner inFS)
    {
        First = null;
        row =0;
        while(inFS.hasNextLine())
        {
            String line = inFS.nextLine();

            //create a new node
            for(int i =0; i<line.length();i++)
            {
                Node newNode = new Node (row, alphabet.charAt(i), line.charAt(i));
                //storing the node by appending it to the appropriate node
                if (First==null)
                {
                    First = newNode;
                    seatcount++;
                }
                else if (i==0)
                {
                    Node last = First;
                    while (last.getDown() != null) {
                        last = last.getDown();
                    }

                    // Insert the new_node at last node
                    last.setDown(newNode);
                    seatcount++;
                }
                else
                {
                    Node last = First;
                    while (last.getDown() != null) {
                        last = last.getDown();
                    }

                    while (last.getNext() != null) {
                        last = last.getNext();
                    }
                    // Insert the new_node at last node
                    last.setNext(newNode);
                    seatcount++;
                }
            }

            row++;//row counter
        }
    }
    //traverse the linked list using the same technique above exept saving the contents to a string "A,S,C" get converted to "#".
    public void toString(int r)
    {
        int i = 1;
        Node curNodeR=First;
        System.out.print(" ");
        String output ="";
        for (int j = 0; j<r; j++)
        {
            System.out.print(alphabet.charAt(j));//this prints out the alphabet at the top
        }
        System.out.println();

        while (curNodeR !=null)
        {
            output+=i;
            i++;
            Node curNodeC;
            curNodeC=curNodeR;
            while(curNodeC !=null)
            {
                if ((curNodeC.toString()).charAt(0)=='.')
                {
                    output+=(".");
                    //System.out.print(""+curNodeC.Payload.TicketType);
                }
                else
                {
                    output +="#";
                }

                curNodeC=curNodeC.getNext();
            }

            output+="\n";//New line
            curNodeR=curNodeR.getDown();

        }
        System.out.println (output);

    }
    //traverse the linked list using the same technique above exept this time we check for unoccupied ('.') Seats in the nodes and return true if all subsequent seats are free

    public boolean CheckAvalibility (int R, int C, int AQ, int SQ, int CQ)
    {
        int seatchecker = C;
        Node currNode = First;

        for (int i = 1; i<R; i++)
        {
            currNode=currNode.getDown();
        }
        for (int i = 0; i<seatchecker; i++)
        {
            currNode=currNode.getNext();
        }
        for (int i = 0; i< AQ ; i++)
        {
            if (currNode.getPayload().toString().charAt(0) != '.')
            {
                return false;
            }
            else
            {
                currNode=currNode.getNext();
            }

        }
        for (int i = 0; i< SQ ; i++)
        {
            if (currNode.getPayload().toString().charAt(0) != '.')
            {
                return false;
            }
            else
            {
                currNode=currNode.getNext();
            }

        }
        for (int i = 0; i< CQ ; i++)
        {
            if (currNode.getPayload().toString().charAt(0) != '.')
            {
                return false;
            }
            else
            {
                currNode=currNode.getNext();
            }

        }
        return true;

    }
    //exact same as the above method but instead of check it updates the auditorium
    public void ReserveSeats (int R, int C, int AQ, int SQ, int CQ)
    {
        Node currNode = First;

        for (int i = 1; i<R; i++)
        {
            currNode=currNode.getDown();
        }
        for (int i = 0; i< C; i++)
        {
            currNode=currNode.getNext();
        };
        for (int i = 0; i< AQ ; i++)
        {
            currNode.getPayload().setTicketType('A');
            currNode=currNode.getNext();

        }
        for (int i = 0; i< CQ ; i++)
        {
            currNode.getPayload().setTicketType('C');
            currNode=currNode.getNext();

        }
        for (int i = 0; i< SQ ; i++)
        {
            currNode.getPayload().setTicketType('S');
            currNode=currNode.getNext();

        }

    }
    public void UnReserveSeats (int R, int C)
    {
        Node currNode = First;

        for (int i = 1; i<R; i++)
        {
            currNode=currNode.getDown();
        }
        for (int i = 0; i< C; i++)
        {
            currNode=currNode.getNext();
        };
        currNode.getPayload().setTicketType('.');
        currNode=currNode.getNext();

        

    }
    //An update to the states (Traversal and count through the LL)
    public void UpdateStats()
    {
        Node curNodeR = First;
        Node curNodeC = First;
        while (curNodeR !=null)
        {
            curNodeC=curNodeR;
            while(curNodeC !=null)
            {
                if (curNodeC.getPayload().getTicketType() =='A')//if the char is A add to respective quantities
                {

                    AdultTickets++;
                    TotalTickets++;
                    TotalSeats++;
                    curNodeC=curNodeC.getNext();
                }
                else if (curNodeC.getPayload().getTicketType() =='S')//if the char is S add to respective quantities
                {

                    SeniorTickets++;
                    TotalTickets++;
                    TotalSeats++;
                    curNodeC=curNodeC.getNext();
                }
                else if (curNodeC.getPayload().getTicketType() =='C')//if the char is C add to respective quantities
                {

                    ChildTickets++;
                    TotalTickets++;
                    TotalSeats++;
                    curNodeC=curNodeC.getNext();
                }
                else if(curNodeC.getPayload().getTicketType() =='.')//if the char is . add to respective quantities
                {

                    TotalSeats++;
                    curNodeC=curNodeC.getNext();
                }
                else
                {
                    curNodeC=curNodeC.getNext();
                }
            }

            curNodeR=curNodeR.getDown();
        }
    }
    public String bestavalible(int r, int c, int total )
    {
        int w = -1;
        double d1 =1000000;
        double d2;
        String [] rad = new String[2];
        boolean availability =true;
        rad[0]="0";
        rad[1]="A";
        double centerRow = (double)(row-1)/2.0;
        double centerColumn = (((double)seatcount/(double)(row))-1)/2.0;

        Node curNodeR = First;
        Node temp= curNodeR.getNext();
        Node curNodeC;
        Node curNodep;

        for(int i =0; i<row;i++)
        {
            curNodeC=curNodeR;
            for (int j = 0; j<=(seatcount/row-total);j++)
            {
                curNodep=curNodeC;
                for (int k=0; k<total; k++)
                {
                    w=0;
                    if (curNodep.getPayload().getTicketType() !='.')
                    {
                        w=-1;
                        break;
                    }
                    temp = curNodep;
                    curNodep=curNodep.getNext();
                }

                if (w!=-1)
                {
                    d2=d1;
                    double avgPositionX=0;
                    curNodep=curNodeC;
                    for (int k =0; k<total;k++)
                    {
                        avgPositionX+=alphabet.indexOf(curNodep.getPayload().getSeat());
                        curNodep= curNodep.getNext();
                    }
                    avgPositionX=avgPositionX/(double)total;
                    d1= Math.sqrt(Math.pow(avgPositionX-centerColumn,2)+Math.pow((i)-centerRow,2));
                    if (d1<=d2)//if d1 is smaller
                    {
                        if (d1==d2)
                        {
                            if (temp.getPayload().getRow() !=centerRow)
                            {
                                if (Integer.parseInt(rad[0])>= temp.getPayload().getRow() &&alphabet.indexOf(rad[1].charAt(0))>alphabet.indexOf(temp.getPayload().getSeat()))
                                {
                                    rad[0]=(i)+"";
                                    rad[1]=""+alphabet.charAt(j);//store seat in return value
                                    availability=false;
                                }


                            }
                            else {
                                if (Integer.parseInt(rad[0])!=centerRow){
                                    rad[0]=(i)+"";
                                    rad[1]=""+alphabet.charAt(j);//store seat in return value
                                    availability=false;
                                }

                            }
                        }
                        else
                        {
                            rad[0]=(i)+"";
                            rad[1]=""+alphabet.charAt(j);//store seat in return value
                            availability=false;
                        }

                    }
                    else
                    {
                        d1=d2;//revert the d1 value if not smaller
                    }
                }
                curNodeC=curNodeC.getNext();
            }
            curNodeR=curNodeR.getDown();
        }
        if (availability)//if r didn't change then return -1
        {
            return "-";
        }
        else//return the seat number +1 (since r is the index not the seat column)
        {
            rad[0]= Integer.toString(Integer.parseInt(rad[0])+1);
            return rad[0]+rad[1];
        }
    }

}


