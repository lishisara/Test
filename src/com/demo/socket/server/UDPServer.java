package com.demo.socket.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

	public static void main(String[] args) throws IOException {
		final String str_send = "hello udpclient";
		final byte[] buf = new byte[1024];
		@SuppressWarnings("resource")
		final DatagramSocket ds = new DatagramSocket(3000);
		final DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
		System.out.println("server is on, waiting for client to send data...");
		final boolean f = true;
		while (f) {
			ds.receive(dp_receive);
			System.out.println("server received data from client:");
			final String str_receive = new String(dp_receive.getData(), 0, dp_receive.getLength())
					+ " form" + dp_receive.getAddress().getHostAddress() + ":"
					+ dp_receive.getPort();
			System.out.println(str_receive);
			final DatagramPacket dp_send = new DatagramPacket(str_send.getBytes(),
					str_send.length(), dp_receive.getAddress(), 9000);
			ds.send(dp_send);
			dp_receive.setLength(1024);
		}
	}

}
