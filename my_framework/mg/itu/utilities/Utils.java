package utilities;

import Annotation.AnnotationController;
import Annotation.UrlMapping;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {
 public static List<Class<?>> chargerClasses(String nomPackage) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String chemin = nomPackage.replace('.', '/');
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> ressources = classLoader.getResources(chemin);

        while (ressources.hasMoreElements()) {
            URL ressource = ressources.nextElement();
            String protocole = ressource.getProtocol();

            if ("file".equals(protocole)) {
                scannerDossier(new File(ressource.getFile()), nomPackage, classLoader, classes);
            } else if ("jar".equals(protocole)) {
                String cheminJar = ressource.getPath().substring(5, ressource.getPath().indexOf("!"));
                scannerJar(cheminJar, chemin, classLoader, classes);
            }
        }
        return classes;
    }

    private static void scannerDossier(File dossier, String nomPackage, ClassLoader classLoader, List<Class<?>> classes) throws ClassNotFoundException {
        if (!dossier.exists() || dossier.listFiles() == null) return;

        for (File fichier : dossier.listFiles()) {
            if (fichier.isDirectory()) {
                scannerDossier(fichier, nomPackage + "." + fichier.getName(), classLoader, classes);
            } else if (fichier.getName().endsWith(".class")) {
                String nomClasse = nomPackage + '.' + fichier.getName().substring(0, fichier.getName().length() - 6);
                classes.add(classLoader.loadClass(nomClasse));
            }
        }
    }

    private static void scannerJar(String cheminJar, String cheminPackage, ClassLoader classLoader, List<Class<?>> classes) throws IOException, ClassNotFoundException {
        try (JarFile jar = new JarFile(cheminJar)) {
            Enumeration<JarEntry> entrees = jar.entries();
            while (entrees.hasMoreElements()) {
                JarEntry entree = entrees.nextElement();
                String nomEntree = entree.getName();

                if (nomEntree.startsWith(cheminPackage) && nomEntree.endsWith(".class") && !entree.isDirectory()) {
                    String nomClasse = nomEntree.substring(0, nomEntree.length() - 6).replace('/', '.');
                    classes.add(classLoader.loadClass(nomClasse));
                }
            }
        }
    }

    public static List<String> getAnnotationClasses(List<Class<?>> classes , Class<? extends AnnotationController> a ){
        List<String> ListClasses = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(a)){
                ListClasses.add(clazz.getSimpleName());
            }
        }
        return ListClasses;
    }

    /*
    public static List<Mapping> getAnnotationMethods(List<Class<?>> classes ){
        List<Mapping> ListMappé = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(AnnotationController.class)){
                Method[] tabMethods = clazz.getDeclaredMethods();
                for(Method met : tabMethods){
                    if(met.isAnnotationPresent(UrlMapping.class)){       
                        UrlMapping annotation = met.getAnnotation(UrlMapping.class);
                        String urlValue = annotation.url();
                        ListMappé.add(new Mapping(clazz, met.getName()));
                        
                    }
                }
            }
        }
        return ListMappé;
    }
    */

    public static Map<String,Mapping> get_url_allowed(List<Class<?>> classes){
        Map<String,Mapping> map_list = new HashMap<>();
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(AnnotationController.class)){
                Method[] tabMethods = clazz.getDeclaredMethods();
                for(Method met : tabMethods){
                    if(met.isAnnotationPresent(UrlMapping.class)){       
                        UrlMapping annotation = met.getAnnotation(UrlMapping.class);
                        String urlValue = annotation.url();
                        Mapping map = new Mapping(clazz, met.getName());
                        map_list.put(urlValue,map);
                    }
                }
            }
        }
        return map_list;
    }
}
