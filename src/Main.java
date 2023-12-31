//importing different libraries
//Syed Naqvi
//SAN190003
import java.io.*;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //file stream initialize
        Scanner scnr;// Scanner Object initialize
        scnr = new Scanner(System.in);//Scanner for user input
        Scanner inFS = null;//Scanner object for file
        String[] fileName = new String[]{"A1.txt","A2.txt","A3.txt"};//file name string
        //System.out.println("Enter File Name");//prompt for file name
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
            if (userKey.compareTo("admin")==0)
            {
                while (!found)
                {
                    System.out.println("1. Print Report\n2. Logout\n3. Exit");
                    int selection = scnr.nextInt();
                    switch(selection)
                    {
                        case 1:
                            double []total = new double []{0,0,0,0,0,0};
                            for (int i =0; i<Auditoriums.length;i++)
                            {
                                Auditoriums[i].UpdateStats();//update the A1 variables
                                Auditoriums[i].setTotalTickets(Auditoriums[i].getAdultTickets() + Auditoriums[i].getChildTickets() + Auditoriums[i].getSeniorTickets());//tally the totals seats by adding all the types of seats
                                Auditoriums[i].setTotalSales(Auditoriums[i].getAdultTickets() * 10 + Auditoriums[i].getChildTickets() * 5 + Auditoriums[i].getSeniorTickets() * 7.5);//add up sales by the formula given
                                System.out.print("Auditorium "+(i+1)+"\t"+ (Auditoriums[i].getTotalSeats()-Auditoriums[i].getTotalTickets()) +"\t" + Auditoriums[i].getTotalTickets() +"\t"+ Auditoriums[i].getAdultTickets() +"\t"+ Auditoriums[i].getChildTickets() +"\t"+ Auditoriums[i].getSeniorTickets() +"\t$");
//this behemoth abov            e outputs the summary
                                System.out.printf("%.2f", Auditoriums[i].getTotalSales());
                                System.out.println();
                                total[0]+=Auditoriums[i].getTotalSeats()-Auditoriums[i].getTotalTickets();
                                total[1]+=Auditoriums[i].getTotalTickets();
                                total[2]+=Auditoriums[i].getAdultTickets();
                                total[3]+=Auditoriums[i].getChildTickets();
                                total[4]+=Auditoriums[i].getSeniorTickets();
                                total[5]+=Auditoriums[i].getTotalSales();
                            }
                            System.out.print("Total\t");
                            for (int i =0; i<total.length-1;i++)
                            {
                                System.out.print("\t"+(int)total[i]);
                            }
                            System.out.print("\t$");
                            System.out.printf("%.2f", total[5]);
                            System.out.println();
                            
                            //close differnt parts of program

                            
                            continue;
                        case 2:
                            System.out.println("Logging out");
                            scnr.nextLine();
                            break;
                        case 3:
                            logout = !logout;
                            System.out.println("Logging out");
                            FileOutputStream A1FOS=null;
                            PrintWriter A1out=null;
                            for (int i =0; i<Auditoriums.length;i++)
                            {
                                
                                try {
                                    A1FOS = new FileOutputStream("A"+i+"Final.txt");
                                } catch (FileNotFoundException e) 
                                {
                                    System.out.println("this broke");
                                }//create object
                                A1out = new PrintWriter(A1FOS); //create a writer obkect
                            
                                //this code runs through all the seats in the auditorium and only prints the '.','A','S'&'C'
                                Node curNodeR = Auditoriums[i].First;//pointer node to the LL points to rows
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
                                A1out.close();
                            }


                            break;
                        default:
                            System.out.println("Incorrect option chosen");
                            break;
                    }
                    found =!found;
                }
                
            }
            else
            {
                while (!found)
            {
                System.out.println("1. Reserve Seats\n2. View Orders\n3. Update Order\n4. Display Receipt\n5. Log Out");
                int selection=0;
                try {
                    selection = scnr.nextInt();
                }
                catch (InputMismatchException e)
                {
                    selection=0;
                    scnr.nextLine();
                };
                
                String[] listOfOrders;
                String[] listofAdults;
                String[] listofChilds;
                String[] listofSeniors;
            
                switch (selection)
                {
                    case 1:
                        String t;
                        System.out.println("1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3");
                        try 
                        {
                            selection = scnr.nextInt();
                        }
                        catch (InputMismatchException e)
                        {
                            System.out.println("No Auditorium");
                            scnr.nextLine();
                            continue;
                        }
                        if (selection>3||selection<1)
                        {
                            System.out.println("No Auditorium");
                            continue;
                        }
                        if (users.get(userKey)[1]==null||users.get(userKey)[1].compareTo(", ")==0)
                        {
                            t =reserveTicketsString(Auditoriums[selection-1], scnr,users, userKey);
                            if(!reserveTicketIsNull(t))
                            {
                                users.get(userKey)[1]=t;
                            }
                            
                        }
                        else
                        {
                            t =reserveTicketsString(Auditoriums[selection-1], scnr,users, userKey);
                            if(!reserveTicketIsNull(t))
                            {
                                users.get(userKey)[1]=users.get(userKey)[1]+"/"+t;
                            }
                            
                        }
                        if (users.get(userKey)[2]==null && !reserveTicketIsNull(t))
                        {
                            users.get(userKey)[2]=""+selection;
                        }
                        else if (!reserveTicketIsNull(t))
                        {

                            users.get(userKey)[2]=users.get(userKey)[2]+selection;
                        }
                        //System.out.println(users.get(userKey)[1]);
                        continue;
                    case 2:
                        //System.out.println("output"+users.get(userKey)[1]);
                        //for (int i =0; i<users.get(userKey).length;i++){System.out.println(users.get(userKey)[i]);}
                        if (users.get(userKey)[1]==null || users.get(userKey)[1].compareTo("")==0)
                        {
                            System.out.println("No orders");
                        }
                        else
                        {
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
                                System.out.println("Auditorium "+ users.get(userKey)[2].charAt(i)+", "+ sortString(listOfOrders[i]) +"\n"+listofAdults[i+1]+" adult, "+listofChilds[i+1]+" child, "+listofSeniors[i+1]+" senior\n");
                            }
                        }
                        continue;    
                    case 3:
                        if (users.get(userKey)[1]==null|| users.get(userKey)[1].compareTo("")==0)
                        {
                            System.out.println("No orders");
                        }
                        else
                        {
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
                            int selectedOrder = 0;
                            try 
                            {
                                selectedOrder = scnr.nextInt();
                            }
                            catch (InputMismatchException e)
                            {
                                selectedOrder =0;
                                //System.out.println("Invalid");
                                scnr.nextLine();
                            }
                            System.out.println(users.get(userKey)[2].length());
                            if (selectedOrder>0&&selectedOrder<=users.get(userKey)[2].length())
                            {
                                System.out.println("1. Add tickets to order\n2. Delete tickets from order\n3. Cancel Order");
                                try 
                                {
                                    selection = scnr.nextInt();
                                }
                                catch (InputMismatchException e)
                                {
                                    selection =0;
                                    //System.out.println("Invalid");
                                    scnr.nextLine();
                                }
                                //selection = scnr.nextInt();
                                if (selection>0&& selection<4)
                                {
                                    updateOrderSwitch(selection,userKey,users,selectedOrder,Auditoriums,scnr,listOfOrders);
                                }
                                else
                                {
                                    System.out.println("Incorrect");
                                }
                                
                            }
                            else
                            {
                                System.out.println("No such order");
                            }
                            
                        }
                        
                        continue;
                        
                    case 4:
                        if (users.get(userKey)[1]==null|| users.get(userKey)[1].compareTo("")==0)
                        {
                            System.out.println("Customer Total: $0.00");
                            continue;
                        }
                        double runninTotal=0;
                        double orderTotal=0;
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
                            System.out.println("Auditorium "+ users.get(userKey)[2].charAt(i)+", "+ sortString(listOfOrders[i])+"\n"+listofAdults[i+1]+" adult, "+listofChilds[i+1]+" child, "+listofSeniors[i+1]+" senior\n");
                            orderTotal=Integer.parseInt(listofAdults[i+1])*10.0+Integer.parseInt(listofChilds[i+1])*5.0+Integer.parseInt(listofSeniors[i+1])*7.50;
                            runninTotal+=orderTotal;
                            System.out.println("Order Total: $");
                            System.out.printf("%.2f", orderTotal);
                            System.out.println();
                            orderTotal=0;
                        }
                        System.out.print("Customer Total: $");
                        System.out.printf("%.2f", runninTotal);
                        System.out.println();
                        //hi
                        //System.out.println()
                        continue;
                    case 5:
                        System.out.println("Logging out");
                        scnr.nextLine();
                        break;
                    default:
                        System.out.println("Incorrect try again");
                        continue;
                }
                found = !found;
            }
        
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
                            System.out.println("seats not reserved");
                            if (users.get(userKey)[1]==null)
                            {
                                //System.out.println("a");

                                String[] s =new String []{users.get(userKey)[0],null,null,null,null,null};
                                users.put(userKey,s);
                            }
                            /* 
                            else
                            {
                                //System.out.println("b");
                                //flag!!!
                                users.get(userKey)[2] = users.get(userKey)[2].substring(0,users.get(userKey)[2].length()-1);
                            }*/
                            return null;
                        }
                    }
                }
                else
                {
                    System.out.println("seats not available");
                }
            }
        return null;
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
                        System.out.println("Invalid password");
                    }

                }
                if (!found){System.out.println("Too many incorrect passwords");}   
            }
        }
        return "";
    }
    public static void updateOrderSwitch(int selection, String userKey, HashMap<String,String[]> users,int selectedOrder,Auditorium Auditoriums[], Scanner scnr, String[] listOfOrders)
    {
        String[] listofAdults=users.get(userKey)[3].split("/");
        String[] listofChilds=users.get(userKey)[4].split("/");
        String[] listofSeniors=users.get(userKey)[5].split("/");
        String adultString ="";
        String childString="";
        String senriorString="";
        String[] temp = null;
        String output="";
        String[] tempListOfOrders=null;
        //String[] listOfSeats= listOfOrders[selectedOrder-1].split(",");
        switch(selection)
        {
            case 1:
                //System.out.println("1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3");
                selection=Integer.parseInt(""+users.get(userKey)[2].charAt(selectedOrder-1));
                //selection = scnr.nextInt();
                //System.out.println(selection);
                listOfOrders[selectedOrder-1]=listOfOrders[selectedOrder-1]+","+reserveTicketsString(Auditoriums[selection-1], scnr,users, userKey);
                //System.out.println(listOfOrders[selectedOrder-1]);
                output ="";
                for (int i =0; i<listOfOrders.length;i++)
                {
                    output=output+listOfOrders[i]+"/";
                }
                temp = users.get(userKey);
                temp[1]=output.substring(0,output.length()-1);
                //temp[2]=temp[2].substring(0,temp[2].length()-1);
                listofAdults[selectedOrder]=Integer.parseInt(listofAdults[selectedOrder])+Integer.parseInt(temp[3].substring(temp[3].lastIndexOf("/")+1))+"";
                listofChilds[selectedOrder]=Integer.parseInt(listofChilds[selectedOrder])+Integer.parseInt(temp[4].substring(temp[4].lastIndexOf("/")+1))+"";
                listofSeniors[selectedOrder]=Integer.parseInt(listofSeniors[selectedOrder])+Integer.parseInt(temp[5].substring(temp[5].lastIndexOf("/")+1))+"";
                adultString ="";
                childString="";
                senriorString="";
                for (int i =0; i<listOfOrders.length+1;i++)
                {
                    adultString=adultString+listofAdults[i]+"/";
                    childString=childString+listofChilds[i]+"/";
                    senriorString=senriorString+listofSeniors[i]+"/";
                }
                temp[3]=adultString.substring(0,adultString.length()-1);
                temp[4]=childString.substring(0,childString.length()-1);
                temp[5]=senriorString.substring(0,senriorString.length()-1);
                //for (int i = 0 ; i< temp.length;i++){ System.out.println(temp[i]);}
                users.put(userKey,temp);
                return;
            case 2:
                tempListOfOrders = removeDashes(listOfOrders[selectedOrder-1]).split(",");

                //for (int i =0; i<tempListOfOrders.length;i++){System.out.println(tempListOfOrders[i]);}
                System.out.println("Which row?");
                scnr.nextLine();
                String seat = scnr.nextLine();
                System.out.println("Which Seat?");
                seat += scnr.nextLine();
                removeseat(listOfOrders, selectedOrder, scnr, selection, Auditoriums, userKey, users, seat);
                return;
            case 3:
                tempListOfOrders = removeDashes(listOfOrders[selectedOrder-1]).split(",");
                for (int i =tempListOfOrders.length-1; i>=0;i--)
                {
                    //System.out.print(i);
                    //System.out.print(tempListOfOrders[i]);
                    if (tempListOfOrders[i]!=null)
                    {
                        removeseat(listOfOrders, selectedOrder, scnr, selection, Auditoriums, userKey, users, tempListOfOrders[i]);

                    }
                }
                return;
        }
    }
    public static String removeDashes(String s)
    {
        return s.replaceAll("-\\w","");
    }
    public static void removeseat(String[] listOfOrders, int selectedOrder, Scanner scnr, int selection, Auditorium[] Auditoriums,String userKey, HashMap<String,String[]> users, String seat)
    {
        String[] tempListOfOrders = removeDashes(listOfOrders[selectedOrder-1]).split(",");
        String[] listofAdults=users.get(userKey)[3].split("/");
        String[] listofChilds=users.get(userKey)[4].split("/");
        String[] listofSeniors=users.get(userKey)[5].split("/");
        String adultString ="";
        String childString="";
        String senriorString="";
        String[] temp = null;
        String output="";
        String[] listOfSeats= listOfOrders[selectedOrder-1].split(",");
        /* 
                for (int i =0; i<tempListOfOrders.length;i++)
                {
                    System.out.println(tempListOfOrders[i]);
                }
                System.out.println("Which seat?");
                scnr.nextLine();
                String seat = scnr.nextLine();*/
                int found =-1;
                
                for (int i =0; i<tempListOfOrders.length;i++)
                {
                    if (tempListOfOrders[i].compareTo(seat)==0)
                    {
                        found=i;
                        break;
                    }
                }
                if (found==-1)
                {
                    System.out.println("Seat not in Order");
                    return;
                }
                else
                {
                    //System.out.println("selection:"+selection+"\n"+users.get(userKey)[2]+"selectedOrder"+selectedOrder);
                    selection=Integer.parseInt(""+users.get(userKey)[2].charAt(selectedOrder-1));
                    String s = Integer.parseInt(""+seat.charAt(0))+""+ (seat.charAt(1)-65);
                    System.out.println("Unreserving: "+s);
                    Auditoriums[selection-1].UnReserveSeats(Integer.parseInt(""+seat.charAt(0)), (seat.charAt(1)-65));
                    tempListOfOrders[found]="";
                }
                output="";
                adultString ="";
                childString="";
                senriorString="";
                //System.out.println("selectedOrder"+selectedOrder);
                for (int j=0; j<listOfOrders.length;j++)
                {
                    if(j==selectedOrder-1)
                    {
                        for (int i =0; i<listOfSeats.length;i++)
                        {
                            if(i!=found)
                            {
                                output+=listOfSeats[i]+",";
                            }
                        }
                        if (output.length()!=0)
                        {
                            output=output.substring(0, output.length()-1)+"/";
                        }
                    }
                    else
                    {
                        output+=listOfOrders[j]+"/";
                    }
                    
                }
                //System.out.println(output);
                switch (listOfSeats[found].charAt(listOfSeats[found].length()-1))
                {
                    case 'A':
                        //System.out.print("A");
                        //for (int i =0; i<listofAdults.length;i++){System.out.println(listofAdults[selectedOrder]);}
                        listofAdults[selectedOrder]=""+(Integer.parseInt(listofAdults[selectedOrder])-1);
                        break;
                    case 'S':
                        //System.out.print("S");
                        listofSeniors[selectedOrder]=""+(Integer.parseInt(listofSeniors[selectedOrder])-1);
                        break;
                    case 'C': 
                        listofChilds[selectedOrder]=""+(Integer.parseInt(listofChilds[selectedOrder])-1);
                        //System.out.print("C");
                        break;
                }
                for (int i =0; i< listofAdults.length;i++)
                {
                    adultString+=listofAdults[i]+"/";
                    childString+=listofChilds[i]+"/";
                    senriorString+=listofSeniors[i]+"/";
                }
                temp = users.get(userKey);
                //for (int i=0; i< temp.length;i++){System.out.println(temp[i]);}
                //System.out.println("____");

                if ((listofAdults[selectedOrder]+listofChilds[selectedOrder]+listofSeniors[selectedOrder]).compareTo("000")==0)
                {
                    for (int i=3; i<temp.length; i++)
                    {
                        if (i!=2)
                        {
                            temp[i]=null+"/";
                        }
                        
                    }
                    temp[1]="";
                    System.out.println("There is an order to be removed");
                    for (int i =0; i<listOfOrders.length;i++)
                    {
                        if(i!=selectedOrder-1)
                        {
                            temp[1]=temp[1]+listOfOrders[i]+"/";
                            temp[3]=temp[3]+listofAdults[i+1]+"/";
                            temp[4]=temp[4]+listofChilds[i+1]+"/";
                            temp[5]=temp[5]+listofSeniors[i+1]+"/";
                            
                        }
                    }
                    if (temp[1].length()>0)
                    {
                        if (temp[1].charAt(temp[1].length()-1)=='/')
                        {
                            temp[1]=temp[1].substring(0,temp[1].length()-1);
                            temp[3]=temp[3].substring(0,temp[3].length()-1);
                            temp[4]=temp[4].substring(0,temp[4].length()-1);
                            temp[5]=temp[5].substring(0,temp[5].length()-1);
                        }
                    }
                    
                    temp[2]=temp[2].substring(0,selectedOrder-1)+temp[2].substring(selectedOrder);
                    if (temp[1].compareTo("")==0)
                    {
                        temp= new String[]{users.get(userKey)[0],null,null,null,null,null};
                    }
                    users.put(userKey,temp);
                    //for (int i=0; i< temp.length;i++){System.out.println(temp[i]);}
                    System.out.println("Ticekt removed");
                    return;
                }



                temp[1]=output.substring(0,output.length()-1);
                //temp[2]=temp[2].substring(0,temp[2].length()-1);
                temp[3]=adultString.substring(0,adultString.length()-1);
                temp[4]=childString.substring(0,childString.length()-1);
                temp[5]=senriorString.substring(0,senriorString.length()-1);
                //for (int i=0; i< temp.length;i++){System.out.println(temp[i]);}
                users.put(userKey,temp);
                System.out.println("Ticekt removed");
                return;
    }
    public static boolean reserveTicketIsNull(String s)
    {
        if (s==null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static String sortString(String s)
    {
        String[] A =s.split(",");
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = 0; j < A.length - i - 1; j++) {
                // Swap if the element found is greater than the next element
                if (Integer.parseInt(""+A[j].charAt(0)) > Integer.parseInt(""+A[j + 1].charAt(0))) {
                    // Swap array[j] and array[j + 1]
                    String temp = (A[j]);
                    A[j] = A[j + 1];
                    A[j + 1] = temp;
                }
                else if (Integer.parseInt(""+A[j].charAt(0)) == Integer.parseInt(""+A[j + 1].charAt(0)))
                {
                    if (A[j].charAt(1) > A[j + 1].charAt(1)) {
                    // Swap array[j] and array[j + 1]
                    String temp = (A[j]);
                    A[j] = A[j + 1];
                    A[j + 1] = temp;
                }
                }
            }
        }
        s = String.join(",",A);
        return s;
    }
}