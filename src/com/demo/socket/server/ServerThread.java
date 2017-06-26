package com.demo.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable {

	private Socket client = null;

	public ServerThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			final PrintStream out = new PrintStream(client.getOutputStream());
			final BufferedReader buf = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			boolean flag = true;
			while (flag) {
				final String str = buf.readLine();
				if (str == null || "".equals(str)) {
					flag = false;
				} else {
					if ("bye".equals(str)) {
						flag = false;
					} else {
						out.println("echo:" + str);
					}
				}
			}
			out.close();
			client.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

}
