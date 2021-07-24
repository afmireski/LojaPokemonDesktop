/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author afmireski
 */
public class ErrorTools {

    public void showExceptionStackTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        System.out.println("Stack Trace: " + stackTrace);
    }

    public void showExceptionMessage(Exception exception) {
        System.out.println("Exception: " + exception.getMessage());
    }

    public void showIOExceptionStackTrace(IOException exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        System.out.println("Stack Trace: " + stackTrace);
    }

    public void showIOExceptionMessage(IOException exception) {
        System.out.println("Exception: " + exception.getMessage());
    }

}
