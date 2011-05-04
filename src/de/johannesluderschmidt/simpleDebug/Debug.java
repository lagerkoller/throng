/*
 	This file is part of Throng - Tuio multiplexeR that crOps and Globalizes.
	For more information see http://code.google.com/p/throng
	
	Copyright (c) 2010-2011 Johannes Luderschmidt <ich@johannesluderschidt.de>

    Throng is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Throng is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Throng. If not, see <http://www.gnu.org/licenses/>.
 */
package de.johannesluderschmidt.simpleDebug;
import java.text.NumberFormat;
import java.util.Calendar;

public class Debug {
	public static boolean isDebug=false;
    
	/**
     * Writes a message to System.out.println in the format
     * [mm/dd/yy hh:mm:ss] message.
     * @param   activity    The message.
    */
    public static void writeActivity(String activity, Object instance) {

    	NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumIntegerDigits(2);
		
        // --- get the current date and time
        Calendar cal = Calendar.getInstance();
        activity = "[" + nf.format(cal.get(Calendar.DAY_OF_MONTH)) 
                 + "/" +  nf.format(cal.get(Calendar.MONTH)+1)
                 + "/" + cal.get(Calendar.YEAR) 
                 + " " 
                 + nf.format(cal.get(Calendar.HOUR_OF_DAY)) 
                 + ":" + nf.format(cal.get(Calendar.MINUTE)) 
                 + ":" + nf.format(cal.get(Calendar.SECOND)) 
                 + "] "
                 + activity 
                 + " in "+instance.getClass().getName() + "\n";

        // --- display the activity
        if(isDebug){
        	System.out.print(activity);
        }
    }
    public static void writeException(String error, Object instance, Exception ex) {
    	Calendar cal = Calendar.getInstance();
    	error = "[" + cal.get(Calendar.MONTH) 
                 + "/" + cal.get(Calendar.DAY_OF_MONTH) 
                 + "/" + cal.get(Calendar.YEAR) 
                 + " " 
                 + cal.get(Calendar.HOUR_OF_DAY) 
                 + ":" + cal.get(Calendar.MINUTE) 
                 + ":" + cal.get(Calendar.SECOND) 
                 + "] "
                 + error 
                 + " in "+instance.getClass().getName() + "\n";
    	System.err.print(error);
    	ex.printStackTrace();
    }
    public static void enableDebug(boolean debug) {
    	isDebug = debug;
    }
}
