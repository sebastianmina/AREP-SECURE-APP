/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.securtiyprimer.sparkwebsecurity;

/**
 *
 * @author mac
 */
import edu.escuelaing.arep.securtiyprimer.sparkwebsecurity.security.HashPass;
import edu.escuelaing.arep.securtiyprimer.sparkwebsecurity.security.login.User;
import edu.escuelaing.arep.securtiyprimer.sparkwebsecurity.security.login.persistence.DataContentHTML;
import spark.Request;
import spark.Response;
import java.util.HashMap;

import static spark.Spark.*;


public class SparkWebSecure {
    
    private static final String SALT =  "Mina", LOGGED = "logged";
    private static final HashMap<String, User> USER = new HashMap<>();

    public static void main(String[] args) {
        port(getPort());
        secure("keystores/ecikeystore.p12", "123456", null, null);
        staticFiles.location("/");
        //get("/hello", (req, res) -> "Hello world.");
        //get("/", "text/html", ((req, res) -> {res.redirect("index.html"); return null;}));
        get("/", SparkWebSecure::login);
        post("/login", SparkWebSecure::loginVerify);
        get("logout", SparkWebSecure::logout);
    }
    
        /**
     * Verify Login in server
     * @param req The request param
     * @param res The response param
     * @return nothing, redirect to /
     */
    private static String loginVerify(Request req, Response res) {
        String name = req.queryParams("user");
        if (USER.get(name) != null) {
            String passHash = HashPass.get_SHA_512_SecurePassword(req.queryParams("pass"),SALT);
            if(USER.get(name).getPassword().equals(passHash)) {
                req.session().attribute(LOGGED, name);
            }
        }
        res.redirect("/");
        return null;
    }
    
    
    /**
     * Login in server
     * @param req The request param
     * @param res The response param
     * @return html to login data
     */
    private static String login(Request req, Response res) {
        String logged = req.session().attribute(LOGGED);
        if(logged == null){
            return DataContentHTML.getInputData();
        }
        return "Hola muy buenas "+logged;
    }
    
    /**
     * Logout in server
     * @param req The request param
     * @param res The response param
     * @return html to logout data
     */
    private static String logout(Request req, Response res) {
        req.session().removeAttribute(LOGGED);
        res.redirect("/");
        return null;
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
