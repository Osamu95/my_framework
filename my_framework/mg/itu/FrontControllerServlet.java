
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.Annotation.AnnotationController;
import utilities.*;

import java.io.IOException;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import Annotation.AnnotationController;

public class FrontControllerServlet extends HttpServlet{
    List<String> Controller;
    List<Mapping> mapps;
    Map<String,Mapping> map_list;

    public void init() throws ServletException {
        String packageName = this.getInitParameter("PackageName");
        try {
            List<Class<?>> classes = Utils.chargerClasses(packageName);
            Controller = Utils.getAnnotationClasses(classes ,AnnotationController.class);
            map_list = Utils.get_url_allowed(classes);

        }catch(Exception e){
            System.err.println("Erreur lors du chargement : " + e.getMessage());
            e.printStackTrace();
        } 
    }
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

        for(String controller : Controller) {
                out.println(controller );
            }

        for(Mapping map : mapps){

        }
    }
}