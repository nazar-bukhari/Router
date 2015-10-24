import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: nazar
 * Date: 10/19/15
 * Time: 11:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    private Model model;
    private String hostOne;
    private String hostTwo;
    private String hostThree;
    private int portZero;
    private int portOne;
    private int portTwo;
    private int receivingPort;
    private String jsonText;
    private Gson gson;
    private List<Integer> portList;
    private List<String> hostList;

    public Client(Model model) {

        this.model = model;

        portList = new LinkedList<Integer>();
        hostList = new LinkedList<String>();

        hostOne = model.getHostOne();
        hostTwo = model.getHostTwo();
        hostThree = model.getHostThree();

        portZero = model.getPortZero();
        portOne = model.getPortOne();
        portTwo = model.getPortTwo();

        portList.add(portZero);
        portList.add(portOne);
        portList.add(portTwo);

        hostList.add(hostOne);
        hostList.add(hostTwo);
        hostList.add(hostThree);

        gson = new Gson();
        jsonText = gson.toJson(model);

        try {

            /**
             * Creating separate Thread.
             * Each Thread will handle one router.
             */
            for(int i=0;i<3;i++){

                BroadCast broadCast = new BroadCast(hostList.get(i),portList.get(i),gson,jsonText);
                broadCast.start();
            }

        } catch (Exception ex) {
            System.out.println("Start Server First.....");
        }
    }

}
