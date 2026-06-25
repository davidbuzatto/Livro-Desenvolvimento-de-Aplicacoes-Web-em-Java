/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fibanacci.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author fabricio
 */
@WebServlet(name = "Servlet_Fibonacci", urlPatterns = {"/fibon"})
public class Servlet_Fibonacci extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        fibonacci(30);
    }

    protected void fibonacci(int termo) {
        System.out.println("Sequencia Fibonacci");

        int t1 = 0;
        int t2 = 1;

        for (int i = 1; i <= termo; i++) {
            if (i == 1) {
                System.out.println(i + " Termo = " + t1);
            } else if (i == 2) {
                System.out.println(i + " Termo = " + t1);
            } else {
                int resultado = t1 + t2;
                System.out.println(i + " Termo = " + resultado);
                t1 = t2;
                t2 = resultado;
            }
        }
    }
}
