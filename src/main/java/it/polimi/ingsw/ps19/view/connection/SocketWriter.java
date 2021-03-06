/*
 * @author Andrea Milanta
 */
package it.polimi.ingsw.ps19.view.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Implements Writer for Socket Communication
 */
public class SocketWriter extends Writer 
{
	private ObjectOutputStream out;
	
	/**
	 * Constructor
	 * @param cs: client socket
	 */
	public SocketWriter(Socket cs)
	{
	try {
			out = new ObjectOutputStream(cs.getOutputStream());
			out.flush();
		} catch (IOException e) 
		{
			ConnectionLogger.log.log(e);
		}
	}
	
	/**
	 * Writes Message
	 * @throws IOException 
	 * @see connection.Writer#write()
	 */
	@Override
	protected void write() throws IOException
	{
		if(message == null) 
			return;
		out.reset();
		out.writeObject(message);
		out.flush();
	}

}
