import java.io.*;
import java.net.*;
import java.util.*;

/**A Server class. Opens connection for clients on specified port*/
public class Reversi {
    public static void main(String [] args){
        /**A Server Socket */
        ServerSocket server = null;
        /**A socket*/        
        Socket mySocket = null;
        /**A print writter*/
        PrintWriter out = null;
        /**A Scanner*/
        Scanner in = null;
        /**Port number*/        
        int port=0;
        Board myBoard= new Board();
        if(args.length == 0 || (args.length == 1 && args[0].equals("-h")) ||args.length > 3 ){
            System.out.println("Usage:\n    Reversi -s <port>       Starts the Server for a networked game on the requested port\n                            e.g. java Reversi -s 12300\n    Reversi -c <IP> <port>  Connects as a Client to an existing game on the requested socket\n                            e.g. java Reversi -c 10.10.1.4 12300\n    Reversi -h              prints this usage");
            System.exit(1);    
        }
        else if(args.length == 3 && args[0].equals("-c")){
            String host = args[1];/**Get Host name*/
            port = Integer.parseInt(args[2]); 
            try{
                mySocket = new Socket(host, port);/**Create the connection*/
                System.out.println("Game started. You are the \"Black\" player. It is Black's turn.");
                System.out.println(myBoard);
                out = new PrintWriter (mySocket.getOutputStream(), true);
                in = new Scanner(mySocket.getInputStream());
                boolean turn = true;
                Scanner console = new Scanner (System.in); 
                String serverInput;       
                String input = console.nextLine();
                while(!input.equals("/quit")){
                    if(turn){
                        System.out.println("Black's move: " +input+" It is White's turn...");
                        out.println("Black's move: " +input+" It is your turn.");
                        turn = !turn;
                    }else {
                        serverInput = in.nextLine();
                        System.out.println(serverInput);
                        String br = in.nextLine();
                        while(!br.equals("END")){
                            System.out.println(br);
                            br = in.nextLine();
                        }
                        if(serverInput.equals("/quit")){
                            input=  serverInput;
                            System.out.println("White says: Quit. I am Quiting...");
                        }
                        else{
                    
                            input = console.nextLine();
                            turn = !turn;
                        }
                    } 
        
                }
                if(input.equals("/quit")){
                    System.out.println("Quiting...");
                    out.println(input);
                }
                mySocket.close();
                out.close();
                in.close();
        
            }catch(UnknownHostException e){
                System.out.println("Error: Host Unknown!\n");
            }catch(IOException i){
                System.out.println("Error: IO Exceotion!\n");
            }
        }
       
        else if(args.length == 2 && args[0].equals("-s")){
            port = Integer.parseInt(args[1]); 
            try{/**Build a server socket at specified port*/
                server= new ServerSocket(port);
        
                System.out.println("***Server started. Waiting for another player to connect...");
                mySocket = server.accept();
                System.out.println("Connected.");
                System.out.println("Game started. You are the \"White\" player. It is Black's turn.");
                out = new PrintWriter (mySocket.getOutputStream(), true);
                in = new Scanner(mySocket.getInputStream());
      
                System.out.println(myBoard);
        
                boolean turn = false;
                Scanner console = new Scanner (System.in);
                String clientInput = "";
                String input = "";
                String W = "W";
                while(!input.equals("/quit")){
                    while(turn && (!input.equals("/quit"))){
                        input = console.nextLine();
                        if(input.length()==2){
                            input= input.toLowerCase();
                            char ch = input.charAt(0);
                            int d = input.charAt(1);
                            if((ch >= 'a' && ch <= 'h' ) && (d >48 && d< 57) && myBoard.makeMove(input, W)){
                                System.out.println(myBoard);
                                System.out.println("White's move:" + input+ ". It is Black's turn...");
                                out.println("White's move:" + input+ ". It is your turn.");
                                turn = !turn;
                                out.println(myBoard);
                                out.println("END");
                            }
              
                            else if(!input.equals("/quit")){
                                System.out.println("White entered "+ input+". That is not a legal move. Try again.");
               
                            }
                        }
                        else if(!input.equals("/quit")){
                            System.out.println("White entered "+ input+". That is not a legal move. Try again.");
              
                        }
                        
            
                    }

                    if(!input.equals("/quit")){
                        String [] cp;
                        String cInput = "";
                        String B = "B";
                        while(!turn && !clientInput.equals("/quit")){
                
                            clientInput = in.nextLine();
                            if(clientInput.equals("/quit")){
                                input=clientInput;
                                System.out.println("Black says: Quit. I am Quiting...");
                
                            }
                            if(!clientInput.equals("/quit")){
                                cp =  clientInput.split(" ");
                                cInput = cp[2];
                            }
                            if(cInput.length()==2){
                                cInput= cInput.toLowerCase();
                                char ch = cInput.charAt(0);
                                int d = cInput.charAt(1);
                                if((ch >= 'a' && ch <= 'h' ) && (d >48 && d< 57)&& myBoard.makeMove(cInput, B) ){
                                    System.out.println(clientInput);
                                    //System.out.println(myBoard);
                                    turn = !turn;
                                }
              
                                else {
                                    out.println("Black entered "+ cInput+". That is not a legal move. Try again.");
                                    out.println(myBoard);
                                    out.println("END");
                                }
                            }
                            else {
                                out.println("Black entered "+ cInput+". That is not a legal move. Try again.");
                                out.println(myBoard);
                                out.println("END");
                            }
                            System.out.println(myBoard);
                        }
                    }
          
                }
        
                if(input.equals("/quit")){
                    System.out.println("Quiting...");
                    out.println(input);
                }
                /**close all connections*/
                mySocket.close();
                out.close();
                in.close();
            }catch(UnknownHostException e){
                System.out.println("Error: Host Unknown!\n");
            }catch(IOException i){
                System.out.println("Error: IO Exceotion!\n");
            }
        }
    }
}
 
