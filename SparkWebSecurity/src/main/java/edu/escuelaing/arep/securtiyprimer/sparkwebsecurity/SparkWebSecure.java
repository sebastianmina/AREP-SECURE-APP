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
import static spark.Spark.*;

public class SparkWebSecure {
    
    public static void main(String[] args) {
    get("/hello", (req, res) -> "Hello World");
    }
}
