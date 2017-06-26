package com.demo.socket.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

	private static int TIMEOUT = 5000;

	private static int MAXNUM = 5;

	public static void main(String[] args) throws IOException {
		final String str_send = "Hello UDPserver";
		final byte[] buf = new byte[1024];
		final DatagramSocket ds = new DatagramSocket(9000);
		final InetAddress local = InetAddress.getLocalHost();
		final DatagramPacket dp_send = new DatagramPacket(str_send.getBytes(), str_send.length(),
				local, 3000);
		final DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
		ds.setSoTimeout(TIMEOUT);
		int tries = 0;
		boolean receivedResponse = false;
		while (!receivedResponse && tries < MAXNUM) {
			ds.send(dp_send);
			try {
				ds.receive(dp_receive);
				if (!dp_receive.getAddress().equals(local)) {
					throw new IOException("Received packet form an unknown address");
				}
				receivedResponse = true;
			} catch (final InterruptedIOException e) {
				tries += 1;
				System.out.println("Time out," + (MAXNUM - tries) + " more tries...");
			}
		}
		if (receivedResponse) {
			System.out.println("client received data form server");
			final String str_receive = new String(dp_receive.getData(), 0, dp_receive.getLength())
					+ " from " + dp_receive.getAddress().getHostAddress() + ":"
					+ dp_receive.getPort();
			System.out.println(str_receive);
			dp_receive.setLength(1024);
		} else {
			System.out.println("No response -- give up");
		}
		ds.close();
	}

}
