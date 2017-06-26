package com.demo.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client1 {

	public static void main(String[] args) throws IOException {
		final Socket client = new Socket("127.0.0.1", 20006);
		client.setSoTimeout(10000);
		final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		final PrintStream out = new PrintStream(client.getOutputStream());
		final BufferedReader buf = new BufferedReader(
				new InputStreamReader(client.getInputStream()));
		boolean flag = true;
		while (flag) {
			System.out.println(" ‰»Î–≈œ¢:");
			final String str = input.readLine();
			out.println(str);
			if ("bye".equals(str)) {
				flag = false;
			} else {
				try {
					final String echo = buf.readLine();
					System.out.println(echo);
				} catch (final SocketTimeoutException e) {
					System.out.println("Time out, No response");
				}
			}
		}
		input.close();
		if (client != null) {
			client.close();
		}
	}
}
