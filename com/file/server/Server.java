package com.file.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			// String Path = new String("C:/Temp/Server/test.txt");

			Object objectKey = new Object();

			ServerAction s = new ServerAction(objectKey);

			s.start();

			// input comment to controll the flow
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String comment = br.readLine();

			while (true) {
				if ("stop".equals(comment)) {
					s.interrupt();
					break;
				}

				comment = br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
