
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;

public class FrontControllerServlet extends HttpServlet{
        public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
        processRequest(req,res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        processRequest(req,res);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        String url = req.getRequestURI();
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println(url);
}
}