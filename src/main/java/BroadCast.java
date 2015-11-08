import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by nazar on 10/21/15.
 */
public class BroadCast extends Thread {

    private Socket socket;
    private String host;
    private int port;
    private String packet;
    private Gson gson;
    private String jsonText;

    public BroadCast(String host, int port,Gson gson,String jsonText) {
        this.host = host;
        this.port = port;
        this.gson = gson;
        this.jsonText = jsonText;

    }

    @Override
    public void run() {

        try{
        System.out.println("Trying to connect with "+host+" At port : "+port);
        socket = new Socket(host, port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        startNetworking(socket,writer);
        }
        catch(Exception ex){
//            ex.printStackTrace();
//            System.out.println("Please Start Server First At Port : "+port);
        }
    }

    public void startNetworking(Socket socket, Writer writer) {

        this.socket = socket;

        try {
            if (socket.isConnected()) {
                System.out.println("Connected with "+host+" At port : "+port+". Sending Client Routing Table....");
                Thread.sleep(3000);
                while (true) {

                    /**
                     * Writing data into socket
                     */
                    writer.write(jsonText);
                    writer.write("\r\n");
                    writer.flush();
                    Thread.sleep(2000);
                    writer.write(" ");
                    writer.flush();

                    /**
                     * Receiving Data from server
                     */
                    try {

                        InputStream is;
                        InputStreamReader isr;
                        BufferedReader br;

                        System.out.println("Waiting for reply from server using port :" + port);

                        is = socket.getInputStream();
                        isr = new InputStreamReader(is);
                        br = new BufferedReader(isr);
                        packet = br.readLine();

                        if (socket.isConnected()) {
                            System.out.println("Connected with Server.Accepting Server Routing Table ....");
                            while (true) {
                                if (packet != null) {
                                    Thread.sleep(2000);
                                    Model routerOneModel = gson.fromJson(packet, Model.class);
                                    DisplayTable(routerOneModel);
                                    break;
                                } else {
                                    System.out.println("null Packet");
                                }
                            }
                        } else {
                            System.out.println("Server is disconnected from Receiving Mode");
                            Thread.sleep(1000);
                        }
                        break;

                    } catch (Exception ex) {
                        Thread.sleep(3000);
                        ex.printStackTrace();
                        System.out.println("Server Still Not replying.....");
                        break;
                    }

                }
            } else {
                System.out.println("Server is disconnected from Sending Mode");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Displaying Server Routing Table
     * @param model
     */
    public void DisplayTable(Model model) {

        System.out.println();
        System.out.println("Destination    Link-Cost (From Receiver)");
        System.out.println("From Router-1");
        System.out.println("-------------------------------");
        System.out.println(model.getRouterZero() + "            " + model.getWeightRZero());
        System.out.println(model.getRouterOne() + "            " + model.getWeightROne());
        System.out.println(model.getRouterTwo() + "            " + model.getWeightRTwo());
        System.out.println(model.getRouterThree() + "            " + model.getWeightRThree());

        /**
         * Writing into file (Information From R1)
         */
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pathInformation.txt", true)));
            out.println();
            out.println(model.getRouterOne()+" "+model.getRouterZero()+" "+model.getWeightRZero());
            out.print(model.getRouterOne()+" "+model.getRouterTwo()+" "+model.getWeightRTwo());
            out.close();

            new BellmanFord();

        }catch (IOException e) {
            System.out.println("Exception while writing into file");
        }

    }
}
