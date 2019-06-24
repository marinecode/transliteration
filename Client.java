package forbear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private BufferedReader consoleReader = new BufferedReader ( new InputStreamReader (System.in) );
	
	//--------------------------------------------------------
	public void makeConnection() {
		try {
			socket = new Socket("localhost", 666);
			writer = new PrintWriter( socket.getOutputStream());
			reader = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException ex) {
			System.out.println( "Server is down" );
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("Server is ready for request. Type 'quite' for close conection." +'\n'+ "Waiting your input:");
	}

	
	//--------------------------------------------------------
	private String getClientInput() throws IOException {
		
		String input = consoleReader.readLine();
		return input;
	}
	
	
	//--------------------------------------------------------
	private void makeRequest( String input ) throws IOException {
		
		writer.println( input );
		writer.flush();
	}
	
	
	//--------------------------------------------------------
	private String getRespond() throws IOException {
		
		String respond = reader.readLine();
		return respond;
	}
	
	
	//--------------------------------------------------------
	private void cleanup() {
		try {
			consoleReader.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		writer.close();
		try {
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//--------------------------------------------------------
	public static void main(String[] args) {
		Client c = new Client();
		c.makeConnection();
		
		while( true ) {
			try {
				String input = c.getClientInput();
				c.makeRequest( input );
				
				if( input.equalsIgnoreCase("quite")) {
					c.cleanup();
					System.out.println("Client is gone");
					break;
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String respond = c.getRespond();
				System.out.println("From server:" + respond );
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		c.cleanup();

	}

}
