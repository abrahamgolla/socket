package socketprogram.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerListen {

	public static void main(String[] args) throws IOException {

		byte[] returnArr = new byte[10];
		ServerSocket serverSocket = new ServerSocket(11221);
		int i = 2;
		System.out.println("waiting for a client on port "+serverSocket.getLocalPort());
		while(i > 0){
			Socket server = serverSocket.accept();
			try{
				System.out.println("just connected to "+server.getRemoteSocketAddress());

				DataInputStream input = new DataInputStream(server.getInputStream());
				
				DataOutputStream output = new DataOutputStream(server.getOutputStream());
				byte[] buffer = new byte[22];
//				byte[] buffer = new byte[]{120,120,13,1,1,35,69,103,(byte)137,1,35,69,0,1,(byte)140,(byte)221,13,10};
				input.readFully(buffer);
				for(byte b: buffer){
				System.out.print(String.format("%02X ", b));
				}
				
				byte[] slice = Arrays.copyOfRange(buffer, 2, buffer.length-4);

				CRCCheckSum crc = new CRCCheckSum();
				String errorCode = crc.getCrc16(slice);
				byte[] err = errorCode.getBytes();
				String[] split = errorCode.split("(?<=\\G.{2})");
				
				returnArr[0] = buffer[0];
				returnArr[1] = buffer[1];
				returnArr[2] = 5;
				returnArr[3] = buffer[3];
				returnArr[4]=buffer[16];
				returnArr[5]= buffer[17];
				returnArr[6] = (byte)hex2decimal(split[0]);
				returnArr[7] = (byte)hex2decimal(split[1]);
				returnArr[8] = buffer[20];
				returnArr[9] = buffer[21];
				for(byte b: returnArr)
					System.out.println(String.format("%02X ", b));
				output.write(returnArr);
				
				/*byte[] slice = Arrays.copyOfRange(buffer, 2, buffer.length-4);
				
				CRCCheckSum crc = new CRCCheckSum();
				
				String errorCode = crc.getCrc16(slice);
				System.out.println(errorCode);*/
/*				int startbit[] = new int[2];
				int pcLength[] = new int[1];
				int protocol[] = new int[1];
				int imei[] = new int[8];
				int idType[] = new int[2];
				int extBit[] = new int[2];
				int serial[] = new int[2];
				int eCheck[] = new int[2];
				int sbit[] = new int[2];

				startbit[0] = buffer[0];
				startbit[1] = buffer[1];
				11 01 03 58 74 00 50 20 67 46 10 1C 32 02 00 03
				11 01 03 58 74 00 50 20 67 46 10 1C 32 02 00 04
				

				pcLength[0] = buffer[2];
				protocol[1] = buffer[3];

				imei[0] = buffer[4];
				imei[1] = buffer[5];
				imei[2] = buffer[6];
				imei[3] = buffer[7];
				imei[4] = buffer[8];
				imei[5] = buffer[9];
				imei[6] = buffer[10];
				imei[7] = buffer[11];

				idType[0] = buffer[12];
				idType[1] = buffer[13];

				extBit[0] = buffer[14];
				extBit[1] = buffer[15];

				serial[0] = buffer[16];
				serial[1] = buffer[17];

				eCheck[0] = buffer[18];
				eCheck[1] = buffer[19];

				sbit[0] = buffer[20];
				sbit[1] = buffer[21];*/
				
				

//				byte[] returnP = new byte[10];
//				returnP[0] = buffer[0];
//				returnP[1] = buffer[1];
//				returnP[2] = buffer[2];
//				returnP[3] = buffer[3];
//				returnP[4] = buffer[16];
//				returnP[5] = buffer[17];
//				returnP[6] = buffer[18];
//				returnP[7] = buffer[19];
//				returnP[8] = buffer[20];
//				returnP[9] = buffer[21];				
//				
//				byte mama[] = new byte[20];
//				DataInputStream tset = new DataInputStream(new ByteArrayInputStream(returnP));
//				tset.readFully(mama);
//				
//				for(byte b: mama){
//					System.out.print(b+" ");
//				}
				
				//output.write(returnP);
				server.close();
				i--;
			}catch (Exception e) {
				serverSocket.close();
			}
		}
	}
	public static int hex2decimal(String s){
		String digits = "0123456789ABCDEF";
		s= s.toUpperCase();
		int val =0;
		for(int i = 0; i < s.length();i++){
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16*val+d;
		}
		return val;
	}

}
