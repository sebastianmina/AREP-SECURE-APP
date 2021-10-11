/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.securtiyprimer.otherservices;

import static spark.Spark.*;

/**
 * The type Secure spark services.
 */
public class SecureServices {

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String... args){
        port(getPort());


        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure("keystores/ecikeystore.p12", "123456", "keystores/myTrustStore", "567890");
        get("/hello",(req,res) -> "Hello Web Services!");
    }


    /**
     * Gets port.
     *
     * @return the port
     */
    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }


}