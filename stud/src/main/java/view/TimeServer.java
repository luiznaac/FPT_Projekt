package view;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimeServer extends Thread {

//	public TimeServer() {
	public void run() {

		int datagramPort = 6667;

		DatagramSocket dSocket = null;
		try {
			dSocket = new DatagramSocket(datagramPort);
			while (true) {
				DatagramPacket packet = new DatagramPacket(new byte[5], 5);
				try {
					dSocket.receive(packet);
					new DatagramClientThread(packet, dSocket).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} finally {
			dSocket.close();
		}

	}

}

class DatagramClientThread extends Thread {

	private DatagramPacket packet;
	private DatagramSocket socket;

	public DatagramClientThread(DatagramPacket packet, DatagramSocket socket)
			throws SocketException {
		this.packet = packet;
		this.socket = socket;
	}

	public void run() {

		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		byte[] data = packet.getData();

		System.out.println("Anfrage von " + address + " vom Port " + port);
		String da = new String(data);
		Scanner sc = new Scanner(da).useDelimiter(":");
		String keyword = sc.next();

		if (keyword.equals("time")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			byte[] myDate = dateFormat.format(new Date()).getBytes();
			packet = new DatagramPacket(myDate, myDate.length, address, port);

			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			byte[] myDate = new byte[1024];
			myDate = new String("Command unknown").getBytes();
			packet = new DatagramPacket(myDate, myDate.length, address, port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sc.close();
	}
}