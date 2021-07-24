/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.DecimalFormat;

/**
 *
 * @author afmireski
 */
public class FormatHelper {

    public FormatHelper() {
    }

    public String formatDefault(Object o) {
        DecimalFormat df = new DecimalFormat("###,##0");
        return String.valueOf(df.format(o));
    }

}
