package org.af1_0.gui;

import java.net.JarURLConnection;
import java.net.URL;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Luca
 * Date: 29-nov-2004
 * Time: 9.49.14
 * To change this template use File | Settings | File Templates.
 */
public class PreliminarTests {

    public PreliminarTests() {
        testXmlLoaderFromDir();
        testXmlLoaderFromJar();
    }

    private void testXmlLoaderFromDir() {
       System.out.println("carico: ptk_resource/xml1_0/Jade_transform.xsl dal File System");
        try {
            URL indexUrl = getClass().getResource("/ptk_resource/xml1_0/Jade_transform.xsl");
            InputStream stream = indexUrl.openStream();
            stream.read();

            System.out.println("OK!");
        } catch (java.lang.NullPointerException e) {
            System.out.println("Impossibile accedere al file system per caricare il file XSL: file non presente");

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Impossibile accedere al file system per caricare il file XSL: errore nei permessi");
        }
    }

    private void testXmlLoaderFromJar() {
        JarURLConnection jarConnection = null;

        System.out.println("carico: ptk_resource/xml1_0/Jade_transform.xsl dal JAR");
        try {
            URL resource_url = getClass().getClassLoader().getResource("ptk_resource/xml1_0/Jade_transform.xsl");
            jarConnection = (JarURLConnection) resource_url.openConnection();
            jarConnection.getInputStream();

            System.out.println("OK!");
        } catch (java.lang.ClassCastException e) {
            System.out.println("Impossibile accedere al jar per caricare il file XSL: jar non presente nel classpath");
        } catch (java.lang.NullPointerException e) {
            System.out.println("Impossibile accedere al jar per caricare il file XSL: file non presente");
        } catch (java.io.IOException ex) {
            System.out.println("Impossibile accedere al jar system per caricare il file XSL: errore nei permessi");
        }
    }

    public static void main(String[] args) {
        PreliminarTests tests = new PreliminarTests();
    }
}
