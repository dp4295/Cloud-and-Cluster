
import com.mongodb.*;
//import com.mongodb.client.MongoClient;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;

public class query5 {


    public static void main(String arg[]){

        // Find a route from Johnson Creek to Columbia Blvd on I-205 NB
        // using the upstream and downstream fields
        MongoClientURI uri = new MongoClientURI("mongodb+srv://deep:Dpmongo42@cluster0.b1nxf.mongodb.net/freeway?retryWrites=true&w=majority&connectionTimeoutMS=30000&socketTimeoutMS=30000");

        try (MongoClient mongoClient = new MongoClient(uri)) {
            MongoDatabase   database = mongoClient.getDatabase("freeway");
            MongoCollection<Document> freewayStation = database.getCollection("station");

            // Store all the documents from freeway.station collection
            FindIterable<Document> iterDoc = freewayStation.find();

            String startStation = "Johnson Cr NB";
            String endStation = "Columbia to I-205 NB";
            String storedownstream = null;
            Boolean routefindend = false;
            Boolean routefindStart = false;

            // Use to store the route information
            ArrayList<String> listOfRoute = new ArrayList<String>();


            // Loop throw each documents
            for(Document loop : iterDoc){

                if(loop.get("locationtext").equals(startStation)){

                    listOfRoute.add((String) loop.get("locationtext"));
                    routefindStart = true;
                }
                if(routefindStart.equals(true) && !(loop.get("locationtext").equals("Columbia to I-205 NB")))
                {

                    if(!(loop.get("downstream").equals("1047"))){
                        listOfRoute.add((String) loop.get("locationtext"));
                    }

                }
                if(routefindStart.equals(true) && loop.get("locationtext").equals(endStation))
                {
                    listOfRoute.add((String) loop.get("locationtext"));
                    routefindend = true;
                    break;
                }


            }

            System.out.println("Route from Johnson Cr NB to Columbia Blvd");
            System.out.println("__________________________________________");
            System.out.println(listOfRoute);


        }catch (Exception e){

        }

    }
}
