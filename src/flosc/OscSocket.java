package flosc;
import java.net.*;
import java.io.IOException;

/*
 * OscSocket is for sending OSC packets
 *
 */


public class OscSocket extends DatagramSocket {

    public OscSocket() throws SocketException 
    {
    	super();
    }

    /**
     * The only override, to send an OscPacket
     *
     * @param oscPacket OscPacket
     */
    public void send(OscPacket oscPacket) throws IOException 
    {

        byte[] byteArray = oscPacket.getByteArray();
        DatagramPacket packet =
	    new DatagramPacket( byteArray, byteArray.length,
	    		InetAddress.getLocalHost(), oscPacket.getPort() );
        send(packet);
    }
    
    public void sendToOutputPort(OscPacket oscPacket, int outputPort) throws IOException {

        byte[] byteArray = oscPacket.getByteArray();
        DatagramPacket packet =
	    new DatagramPacket( byteArray, byteArray.length,
	    		InetAddress.getLocalHost(), outputPort);
        send(packet);
    }
    
    public void sendToOutputPortAndIP(OscPacket oscPacket, InetAddress ip, int outputPort) throws IOException {

        byte[] byteArray = oscPacket.getByteArray();
        DatagramPacket packet =
	    new DatagramPacket( byteArray, byteArray.length,
	    		ip, outputPort);
        send(packet);
    }
}
