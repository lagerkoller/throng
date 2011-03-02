package flosc;
import java.util.Calendar;

public class Debug {
	public static boolean isDebug=false;
    /**
     * Writes a message to System.out.println in the format
     * [mm/dd/yy hh:mm:ss] message.
     * @param   activity    The message.
    */
    public static void writeActivity(String activity, Object instance) {

	// comment this line to turn on the debug output
//	if (true) return;

        // --- get the current date and time
        Calendar cal = Calendar.getInstance();
        activity = "[" + cal.get(Calendar.MONTH) 
                 + "/" + cal.get(Calendar.DAY_OF_MONTH) 
                 + "/" + cal.get(Calendar.YEAR) 
                 + " " 
                 + cal.get(Calendar.HOUR_OF_DAY) 
                 + ":" + cal.get(Calendar.MINUTE) 
                 + ":" + cal.get(Calendar.SECOND) 
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
