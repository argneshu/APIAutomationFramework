package Utility;

import java.awt.GridBagLayout;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.io.output.TeeOutputStream;

public class ConsolePrinter extends JFrame {

	public static void testConsole() throws IOException {
		FileWriter fwOb = new FileWriter("D:/output/consoleOutPut.txt", false);
		PrintWriter pwOb = new PrintWriter(fwOb, false);
		pwOb.flush();
		pwOb.close();
		fwOb.close();

		PrintStream outStream = System.out;
		PrintStream errStream = System.err;
		OutputStream os = new FileOutputStream("D:/output/consoleOutPut.txt", true);
		os = new TeeOutputStream(outStream, os);
		PrintStream fileStream = new PrintStream(os);
		System.setErr(fileStream);
		System.setOut(fileStream);
	}
}
