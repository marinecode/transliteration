package forbear;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
	
	private Transliterator t = new Transliterator();
	
	
	//--------------------------------------------------------
	public void startServer() {
		try (ServerSocket serv = new ServerSocket( 666 )) 	{
			System.out.println( "Server is waiting...");
			while(true) {
				Socket newClient = serv.accept();
								
				Thread t = new Thread( new ServerWork( newClient ) );
				t.start();
				System.out.println("Connection is established");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	//--------------------------------------------------------
	class ServerWork implements Runnable{
		
		private Socket client;
		
		ServerWork ( Socket client ){
			this.client = client;
		}
		
		
		//--------------------------------------------------------
		public void run() {
			try ( 
					BufferedReader reader = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
					PrintWriter writer = new PrintWriter( new OutputStreamWriter ( client.getOutputStream() ) );
				){
				
				String input  = reader.readLine();
				System.out.println( "From user: " + input );
				writer.write( t.transliterate( input ) );
				writer.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	//--------------------------------------------------------
	public static void main(String[] args) {
		SimpleServer server = new SimpleServer();
		server.startServer();
		
	}

}


