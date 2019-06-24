package forbear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	
	//--------------------------------------------------------
	public void makeConnection() {
		try {
			socket = new Socket("localhost", 666);
			writer = new PrintWriter( socket.getOutputStream());
			reader = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server is ready for request" +"/n"+ "Waiting your input: ");
	}

	
	//--------------------------------------------------------
	private String getClientInput() {
		Scanner scan = new Scanner( System.in );
		String input = scan.nextLine();
		scan.close();
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
		try {
			String input = c.getClientInput();
			c.makeRequest( input );
			
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
		c.cleanup();

	}

}
