package com.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	ServerSocket serverSocket = new ServerSocket(9091);

		System.out.println("waitig for a client on port "+serverSocket.getLocalPort());

		Socket server = serverSocket.accept();

		System.out.println("just connected to "+server.getRemoteSocketAddress());

		DataInputStream input = new DataInputStream(server.getInputStream());

		System.out.println(input.readLine());

		server.close();
    }
}
