/**
 * Created by IntelliJ IDEA.
 * User: Luca
 * Date: 27-nov-2004
 * Time: 19.56.29
 * To change this template use File | Settings | File Templates.
 */
package org.af1_0.gui.xml;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class LogMessage extends java.io.PrintStream {
    private static ByteArrayOutputStream Buffer = new ByteArrayOutputStream();
    private static LogMessage ourInstance = new LogMessage();

    public static LogMessage getInstance() {
        return ourInstance;
    }

    private LogMessage() {
        super(Buffer);
    }

    public String toString() {
        return LogMessage.Buffer.toString();
    }

    public static void main(String args[]) {
        LogMessage aLogMessage = LogMessage.getInstance();

        System.setErr(aLogMessage);

        System.err.println("ciao");
        System.err.println("questa");
        System.err.println("è una");
        System.err.println("provola");

        //System.out.println(aLogMessage.toString());
    }
}
