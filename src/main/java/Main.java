import java.io.*;

import com.google.gson.Gson;


public class Main {

    public static void main(String[] args) {

        /**
         * Self Value Declaration
         */
        Model model = new Model();
        model.setRouterZero("0");
        model.setWeightRZero("0");

        model.setRouterOne("1");
        model.setWeightROne("1");

        model.setRouterTwo("2");
        model.setWeightRTwo("3");

        model.setRouterThree("3");
        model.setWeightRThree("7");

        model.setHostOne("localhost"); //Router One Ip (Change this to Server(Router1) Ip for this version)
        model.setHostTwo("localhost"); //Router Two Ip
        model.setHostThree("localhost"); //Router Three Ip

        model.setPortOne(1502);   //Connecting port --> R1
        model.setPortZero(1501); //Connecting Port --> R2
        model.setPortTwo(1503);  //Connecting Port --> R3

        Gson gson = new Gson();
        String json = gson.toJson(model);

        /**
         * Writing all configuration into json file
         */
        try {
            FileWriter writer = new FileWriter("conf.json");
            writer.write(json);
            writer.close();

            FileWriter ownInfo = new FileWriter("pathInformation.txt");
            ownInfo.write(model.getRouterZero()+" "+model.getRouterOne()+" "+model.getWeightROne()+"\n");
            ownInfo.write(model.getRouterZero()+" "+model.getRouterTwo()+" "+model.getWeightRTwo()+"\n");
            ownInfo.write(model.getRouterZero()+" "+model.getRouterThree()+" "+model.getWeightRThree());

            ownInfo.close();
//            ownInfo.write(model.getRouterZero()+" "+model.getRouterOne()+" "+model.getWeightROne());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /**
         * Printing Self Information
         */
        System.out.println("Destination   Link-Cost (Initial Cost)");
        System.out.println("From Router-0");
        System.out.println("-------------------------------");
        System.out.println(model.getRouterZero() + "            " + model.getWeightRZero());
        System.out.println(model.getRouterOne() + "            " + model.getWeightROne());
        System.out.println(model.getRouterTwo() + "            " + model.getWeightRTwo());
        System.out.println(model.getRouterThree() + "            " + model.getWeightRThree());
        System.out.println();

        new Client(model);
    }


}
