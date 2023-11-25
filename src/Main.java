//importing different libraries
//Syed Naqvi
//SAN190003
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //file stream initialize
        Scanner scnr;// Scanner Object initialize
        scnr = new Scanner(System.in);//Scanner for user input
        Scanner inFS = null;//Scanner object for file
        String[] fileName = new String[]{"A1.txt","A2.txt","A3.txt"};//file name string
        System.out.println("Enter File Name");//prompt for file name
        Auditorium[] Auditoriums = new Auditorium[]{null,null,null};
        //System.out.println(fileName);
        for(int i =0; i < 3 ;i++)
        {
            File in = new File(fileName[i]);//open new file
            try
            {//try catch block to prevent object doesnt exit error
                inFS = new Scanner(in);//Scanner for reading file
                Auditoriums[i] = createAuditorium(inFS);
            }
            //end of catch block
            catch (FileNotFoundException e)
            {
                System.out.println("Auditorium "+(i+1)+" couldnt be found");
            }
        }

        File in = new File ("userdb.dat");
        Scanner userScanner = new Scanner (in);
        HashMap <String,String[]> users = new HashMap<String,String[]>();

        while (userScanner.hasNextLine())
        {
            String[] rawString = userScanner.nextLine().split(" ");
            String userName = rawString[0];
            String[] information = new String[]{null,null,null,null,null,null};
            //[password][Tickets][auditorium nuber][Na][Nc][Ns]
            information[0]=rawString[1];
            users.put(userName,information);
        }
        boolean logout = false;
        while (!logout)
        {
            String userKey = logIn(scnr, users);
            boolean found = false;
            while (!found)
            {
                System.out.println("1. Reserve Seats\n2. View Orders\n3. Update Order\n4. Display Receipt\n5. Log Out");
                int selection = scnr.nextInt();
                String[] listOfOrders;
                String[] listofAdults;
                String[] listofChilds;
                String[] listofSeniors;
            
                switch (selection)
                {
                    case 1:
                        System.out.println("1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3");
                        selection = scnr.nextInt();
                        if (users.get(userKey)[1]==null||users.get(userKey)[1].compareTo(", ")==0)
                        {
                            users.get(userKey)[1]=reserveTicketsString(Auditoriums[selection-1], scnr,users, userKey);
                        }
                        else
                        {
                            users.get(userKey)[1]=users.get(userKey)[1]+"/"+reserveTicketsString(Auditoriums[selection-1], scnr, users, userKey);
                        }
                        if (users.get(userKey)[2]==null)
                        {
                            users.get(userKey)[2]=""+selection;
                        }
                        else
                        {
                            users.get(userKey)[2]=users.get(userKey)[2]+selection;
                        }
                        //System.out.println(users.get(userKey)[1]);
                        continue;
                    case 2:
                        listOfOrders=users.get(userKey)[1].split("/");
                        for (int i =0; i<listOfOrders.length;i++)
                        {
                            listOfOrders[i] = removeDashes(listOfOrders[i]);
                        }
                        listofAdults=users.get(userKey)[3].split("/");
                        listofChilds=users.get(userKey)[4].split("/");
                        listofSeniors=users.get(userKey)[5].split("/");
                        for (int i=0; i<users.get(userKey)[2].length();i++)
                        {
                            System.out.println("Auditorium "+ users.get(userKey)[2].charAt(i)+", "+ listOfOrders[i]+"\n"+listofAdults[i+1]+" adult, "+listofChilds[i+1]+" child, "+listofSeniors[i+1]+" senior");
                        }
                        
                        continue;    
                    case 3:
                        
                        System.out.println("Select Order");
                        listOfOrders=users.get(userKey)[1].split("/");
                        
                        listofAdults=users.get(userKey)[3].split("/");
                        listofChilds=users.get(userKey)[4].split("/");
                        listofSeniors=users.get(userKey)[5].split("/");

                        for (int i=0; i<users.get(userKey)[2].length();i++)
                        {
                            System.out.println("Order "+(i+1)+":");
                            System.out.println("Auditorium "+ users.get(userKey)[2].charAt(i)+", "+ removeDashes(listOfOrders[i])+"\n"+listofAdults[i+1]+" adult, "+listofChilds[i+1]+" child, "+listofSeniors[i+1]+" senior\n");
                        }
                        int selectedOrder = scnr.nextInt();

                        System.out.println("1. Add tickets to order\n2. Delete tickets from order\n3. Cancel Order");
                        selection = scnr.nextInt();
                        updateOrderSwitch(selection,userKey,users,selectedOrder,Auditoriums,scnr);
                        
                    case 4:
                        System.out.println("Auditorium "+ users.get(userKey)[2]+", "+ users.get(userKey)[1]+"\n"+users.get(userKey)[3]+" adult, "+users.get(userKey)[4]+" child, "+users.get(userKey)[5]+" senior");
                        //System.out.println()
                    case 5:
                        System.out.println("Logging out");
                        scnr.nextLine();
                        break;
                    case 6:
                        logout=true;
                        break;
                    default:
                        System.out.println("Incorrect try again");
                        continue;
                }
                found = !found;
            }
        }
        System.out.println("Thanks!");
        userScanner.close();
        scnr.close();
        inFS.close();
    }


    public static Auditorium createAuditorium(Scanner i)
    {
        Auditorium a = new Auditorium(i);
        return a;
    }
    public static void code (Auditorium A1, Scanner scnr)
    {
        boolean exit = false;//master loop control
            while (!exit)//loop till "2"
            {
                //initialize differnt input types
                String input;
                String RN="";
                String SSL="";
                String NA="";
                String NC="";
                String NS="";
                System.out.print("1. Reserve Seats\n2. Exit");//prompt for 1st choise
                input = scnr.nextLine();//store input
                boolean inputvalidation = true;//input validation flag
                if (input.equals("2"))//exit sequence starts
                {
                    FileOutputStream A1FOS=null;
                    try {
                        A1FOS = new FileOutputStream("output1.txt");
                    } catch (FileNotFoundException e) 
                    {
                        System.out.println("this broke");
                    }//create object
                    PrintWriter A1out = new PrintWriter(A1FOS); //create a writer obkect

                    //this code runs through all the seats in the auditorium and only prints the '.','A','S'&'C'
                    Node curNodeR = A1.First;//pointer node to the LL points to rows
                    while (curNodeR != null)//this goes through the linked list and prints the Ticket type of each node
                    {

                        Node curNodeC = curNodeR;//pointer node to the LL points to Columns
                        while (curNodeC != null)
                        {
                            A1out.print(""+ curNodeC.getPayload().getTicketType());
                            curNodeC = curNodeC.getNext();//increment node
                        }

                        //System.out.println("");
                        curNodeR = curNodeR.getDown();//increment row
                        if (curNodeR!=null||curNodeC!=null)
                        {
                            A1out.println("");//newline
                        }

                    }

                    exit = true;//dont loop again

                    A1.UpdateStats();//update the A1 variables
                    A1.setTotalTickets(A1.getAdultTickets() + A1.getChildTickets() + A1.getSeniorTickets());//tally the totals seats by adding all the types of seats
                    A1.setTotalSales(A1.getAdultTickets() * 10 + A1.getChildTickets() * 5 + A1.getSeniorTickets() * 7.5);//add up sales by the formula given
                    System.out.print("\nTotal Seats:\t"+ A1.getTotalSeats() +"\nTotal Tickets:\t" + A1.getTotalTickets() +"\nAdult Tickets:\t"+ A1.getAdultTickets() +"\nChild Tickets:\t"+ A1.getChildTickets() +"\nSenior Tickets:\t"+ A1.getSeniorTickets() +"\nTotal Sales:\t$");
//this behemoth above outputs the summary
                    System.out.printf("%.2f", A1.getTotalSales());
                    //close differnt parts of program
                    
                    A1out.close();
                }
                else if (input.equals("1"))//main loop
                {
                    
                }
            }
    }
    public static String reserveTicketsString(Auditorium A1, Scanner scnr, HashMap<String,String[]> users, String userKey)
    {
        scnr.nextLine();
        String input;
        String RN="";
        String SSL="";
        String NA="";
        String NC="";
        String NS="";
        boolean inputvalidation =true;
        A1.toString(A1.getSeatcount() / A1.getRow());//print the Auditoriun with "#" and "."
        //This basic code sequence puts the differnt inputs, row number, seat char, quantity of each ticket into their inputs, as well as checking input
        while (inputvalidation)
            {
                System.out.print("Enter Row Number >>");
                RN=scnr.nextLine();
                boolean num =true;//flag for if it's a number
                for(int c = 0;c<RN.length();c++)
                {
                    if(!Character.isDigit(RN.charAt(c)))
                    {
                        num=false;
                    }
                }
                if (num)// if the input is a num then exit the validation loop
                {
                    if (Integer.parseInt(RN)<= A1.getRow() &&Integer.parseInt(RN)>=1)
                    {
                        inputvalidation=false;
                    }
                }
            }
            inputvalidation =true;
            //this code works similarly to the one above except it checks to see if the input is a single Alphaebet Char
            while (inputvalidation)
            {
                System.out.print("Enter First Seat >>");
                SSL=scnr.nextLine();
                if (Character.isAlphabetic(SSL.charAt(0))&&SSL.length()==1)//checks the first and (should be)only character in the string
                {
                    for (int w = 0; w<(A1.getSeatcount() / A1.getRow()); w++)
                    {
                        if (SSL.charAt(0)==A1.alphabet.charAt(w))
                        {
                            inputvalidation=false;//if true then exit the loop
                            break;
                        }
                    }
                }
                else
                {
                    inputvalidation=true;//else repeat
                }
            }
                    //convert the seat given to a number so that passing it to functions is easier, by looking through the global letter arrays and finding a match.
            for (int i =0; i<26 ; i++)
            {
                if (SSL.charAt(0)==(A1.alphabet.charAt(i)))
                {
                    SSL= i+"";
                    break;
                }
            }
            //same input validation as before, except this has to be a number and has to be >0 (adult)
            inputvalidation =true;
            while (inputvalidation)
            {
                System.out.print("Enter Number of Adult tickets >>");
                NA=scnr.nextLine();
                inputvalidation=false;
                for (int numbervalidate = 0; numbervalidate<NA.length();numbervalidate++)
                {
                    if (!Character.isDigit(NA.charAt(numbervalidate))||NA.charAt(numbervalidate)=='.')
                    {
                        inputvalidation=true;
                        break;
                    }
                }
                if (!inputvalidation&&(Double.parseDouble(NA)<0||Double.parseDouble(NA)%1!=0))
                {
                    inputvalidation=true;
                }
            }
            //same input validation as before, except this has to be a number and has to be >0 (Child)
            inputvalidation =true;
            while (inputvalidation)
            {
                System.out.print("Enter Number of Child tickets >>");
                NC=scnr.nextLine();
                inputvalidation=false;
                for (int numbervalidate = 0; numbervalidate<NC.length();numbervalidate++)
                {
                    if (!Character.isDigit(NC.charAt(numbervalidate))||NC.charAt(numbervalidate)=='.')
                    {
                        inputvalidation=true;
                        break;
                    }
                }
                if (!inputvalidation&&(Double.parseDouble(NC)<0||Double.parseDouble(NC)%1!=0))
                {
                    inputvalidation=true;
                }
            }
            //same input validation as before, except this has to be a number and has to be >0 (Senior)

            inputvalidation =true;
            while (inputvalidation)
            {
                System.out.print("Enter Number of Senior tickets >>");
                NS=scnr.nextLine();
                inputvalidation=false;
                for (int numbervalidate = 0; numbervalidate<NS.length();numbervalidate++)
                {
                    if (!Character.isDigit(NS.charAt(numbervalidate))||NS.charAt(numbervalidate)=='.')
                    {
                        inputvalidation=true;
                        break;
                    }
                }
                if (!inputvalidation&&(Double.parseDouble(NS)<0||Double.parseDouble(NS)%1!=0))
                {
                    inputvalidation=true;
                }
            }
            //this calles the Auditorium method to check if a sequence of seats is avalible and returns a boolean
            boolean availability = A1.CheckAvalibility(Integer.parseInt(RN),Integer.parseInt(SSL),Integer.parseInt(NA),Integer.parseInt(NS),Integer.parseInt(NC));
            //If it is avalible Reserve
            if (availability)
            {
                A1.ReserveSeats(Integer.parseInt(RN),Integer.parseInt(SSL),Integer.parseInt(NA),Integer.parseInt(NS),Integer.parseInt(NC));
                System.out.print("Seats Reserved\n");
                int starti= Integer.parseInt(RN);
                char startc = (char)(Integer.parseInt(SSL)+65);
                String returnString ="";
                for (int looper=0; looper<Integer.parseInt(NA)+Integer.parseInt(NS)+Integer.parseInt(NC);looper++)
                {
                    String add="";
                    if (looper<Integer.parseInt(NA))
                    {
                        add="A";
                    }
                    else if (looper<Integer.parseInt(NA)+Integer.parseInt(NC))
                    {
                        add="C";
                    }
                    else
                    {
                        add="S";
                    }
                    returnString = returnString+starti+startc+"-"+add+",";
                    startc++;
                }
                users.get(userKey)[3]=users.get(userKey)[3]+"/"+NA;
                users.get(userKey)[4]=users.get(userKey)[4]+"/"+NC;
                users.get(userKey)[5]=users.get(userKey)[5]+"/"+NS;
                return returnString.substring(0,returnString.length()-1);
            }
            //if seats are not avalible then repeat loop
            else
            {
                String test = A1.bestavalible(Integer.parseInt(RN),Integer.parseInt(SSL),Integer.parseInt(NA)+Integer.parseInt(NS)+Integer.parseInt(NC));//check for best avalible if none found then return '-'
                if (test.charAt(0)!='-')// not found  check
                {
                    if (Integer.parseInt(NA)+Integer.parseInt(NS)+Integer.parseInt(NC)>1)//if there are moer than one seat print a range
                    {
                        int endSeat=A1.alphabet.indexOf(test.charAt(1));
                        System.out.print("Those seats are not available, but seats found at: ");
                        for(int l= 1; l<Integer.parseInt(NA)+Integer.parseInt(NS)+Integer.parseInt(NC);l++)
                        {
                            endSeat++;
                        }
                        System.out.println(test+"-"+ test.charAt(0)+A1.alphabet.charAt(endSeat));
                    }
                    else// print only the one seat if only one was selected
                    {
                        System.out.println("Those seat is not available, but seat found at :"+test);
                    }
                    //Ask if User wants to book these tickets
                    System.out.println("Would you Like to book?: Y or N");
                    //Input valididation for Y and N
                    inputvalidation=true;
                    input = scnr.nextLine();
                    while (inputvalidation)
                    {
                        if (input.length()==1&&input.charAt(0)=='Y')//If Y then Reserve seats
                        {
                            if (test.length()==2)//if Row has 2 digits
                            {
                                A1.ReserveSeats(Integer.parseInt(test.charAt(0)+""),A1.alphabet.indexOf(test.charAt(1)),Integer.parseInt(NA),Integer.parseInt(NS),Integer.parseInt(NC));
                                System.out.print("Seats Reserved\n");
                               
                                int starti= Integer.parseInt(test.charAt(0)+"");
                                char startc = (char)(A1.alphabet.indexOf(test.charAt(1))+65);
                                String returnString ="";
                                for (int looper=0; looper<Integer.parseInt(NA)+Integer.parseInt(NS)+Integer.parseInt(NC);looper++)
                                {
                                    String add="";
                                    if (looper<Integer.parseInt(NA))
                                    {
                                        add="A";
                                    }
                                    else if (looper<Integer.parseInt(NA)+Integer.parseInt(NC))
                                    {
                                        add="C";
                                    }
                                    else
                                    {
                                        add="S";
                                    }
                                    returnString = returnString+starti+startc+"-"+add+",";
                                    startc++;
                                }
                                users.get(userKey)[3]=users.get(userKey)[3]+"/"+NA;
                                users.get(userKey)[4]=users.get(userKey)[4]+"/"+NC;
                                users.get(userKey)[5]=users.get(userKey)[5]+"/"+NS;
                                return returnString.substring(0,returnString.length()-1);
                            }
                            else//if Row has 1 digit
                            {
                                A1.ReserveSeats(Integer.parseInt(test.charAt(0)+"")+Integer.parseInt(test.charAt(1)+""),A1.alphabet.indexOf(test.charAt(2)),Integer.parseInt(NA),Integer.parseInt(NS),Integer.parseInt(NC));
                                
                                int starti= Integer.parseInt(test.charAt(0)+"");
                                char startc = (char)(A1.alphabet.indexOf(test.charAt(1))+65);
                                String returnString ="";
                                for (int looper=0; looper<Integer.parseInt(NA)+Integer.parseInt(NS)+Integer.parseInt(NC);looper++)
                                {
                                    String add="";
                                    if (looper<Integer.parseInt(NA))
                                    {
                                        add="A";
                                    }
                                    else if (looper<Integer.parseInt(NA)+Integer.parseInt(NC))
                                    {
                                        add="C";
                                    }
                                    else
                                    {
                                        add="S";
                                    }

                                    returnString = returnString+starti+startc+"-"+add+",";
                                    startc++;
                                }
                                users.get(userKey)[3]=users.get(userKey)[3]+"/"+NA;
                                users.get(userKey)[4]=users.get(userKey)[4]+"/"+NC;
                                users.get(userKey)[5]=users.get(userKey)[5]+"/"+NS;
                                return returnString.substring(0,returnString.length()-1)+"If you find this then fix this";
                            }
                            //inputvalidation=false;
                        }
                        else if (input.length()==1&&input.charAt(0)=='N')
                        {
                            inputvalidation=false;
                            break;
                        }
                    }
                }
                else
                {
                    System.out.println("seats not available");
                }
            }
        return "null";
    }
    public static String logIn(Scanner scnr, HashMap<String,String[]> users)
    {
        boolean found = false;
        while (!found)
        {
            System.out.println("Enter Username");
            String username = scnr.nextLine();
            if(users.get(username)==null)
            {
                System.out.println("No user exits");
            }
            else
            {
                for(int i = 0; i<3;i++)
                {
                    System.out.println("Enter Password:");
                    String password = scnr.nextLine();
                    if(users.get(username)[0].compareTo(password) == 0)
                    {
                        System.out.println("User Found!");
                        return username;

                    }
                    else
                    {
                        System.out.println("Wrong password");
                    }

                }
                if (!found){System.out.println("Too many incorrect passwords");}   
            }
        }
        return "";
    }
    public static void updateOrderSwitch(int selection, String userKey, HashMap<String,String[]> users,int selectedOrder,Auditorium Auditoriums[], Scanner scnr)
    {
        String[] listOfOrders=users.get(userKey)[1].split("/");
        String[] listofAdults=users.get(userKey)[3].split("/");
        String[] listofChilds=users.get(userKey)[4].split("/");
        String[] listofSeniors=users.get(userKey)[5].split("/");
        switch(selection)
        {
            
            case 1:
                System.out.println("1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3");
                        selection = scnr.nextInt();
                        if (users.get(userKey)[1]==null||users.get(userKey)[1].compareTo(", ")==0)
                        {
                            listOfOrders[selectedOrder-1]=listOfOrders[selectedOrder-1]+","+reserveTicketsString(Auditoriums[selection-1], scnr,users, userKey);
                        }
                return;
            case 2:
                return;
            case 3:
                return;

        }
    }
    public static String removeDashes(String s)
    {
        return s.replaceAll("-\\w","");
    }
}