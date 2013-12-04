package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.util.StringTokenizer;

	public class GetProcessList
	{
	 
	 private String GetProcessListData()
	 {
	 Process p;
	 Runtime runTime;
	 String process = null;
	 try {
	 System.out.println("Processes Reading is started...");
	 
	 //Get Runtime environment of System
	 runTime = Runtime.getRuntime();
	 
	 //Execute command thru Runtime
//	 p = runTime.exec("tasklist");      // For Windows
	 p= runTime.exec("ps ux");              //For Linux
	 
	 //Create Inputstream for Read Processes
	 InputStream inputStream = p.getInputStream();
	 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	 
	 //Read the processes from sysrtem and add & as delimeter for tokenize the output
	 String line = bufferedReader.readLine();
	 process = "&";
	 while (line != null) {
	 line = bufferedReader.readLine();
	 process += line + "&";
	 }
	 
	 //Close the Streams
	 bufferedReader.close();
	 inputStreamReader.close();
	 inputStream.close();
	 
	 System.out.println("Processes are read.");
	 } catch (IOException e) {
	 System.out.println("Exception arise during the read Processes");
	 e.printStackTrace();
	 }
	 return process;
	 }
	 
	 public void showProcessData()
	 {
	 try {
	 
	 //Call the method For Read the process
	 String proc = GetProcessListData();
	 
	 //Create Streams for write processes
	 //Given the filepath which you need.Its store the file at where your java file.
	 OutputStreamWriter outputStreamWriter =
	 new OutputStreamWriter(new FileOutputStream("ProcessList.txt"));
	 BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
	 
	 //Tokenize the output for write the processes
	 StringTokenizer st = new StringTokenizer(proc, "&");
	 
	 while (st.hasMoreTokens()) {
	 bufferedWriter.write(st.nextToken());  //Write the data in file
	 bufferedWriter.newLine();               //Allocate new line for next line
	 }
	 
	 //Close the outputStreams
	 bufferedWriter.close();
	 outputStreamWriter.close();
	 
	 } catch (IOException ioe) {
	 ioe.printStackTrace();
	 }
	 
	 }
	 
	 public void killProcess(String processName){
		 
		 Runtime runTime;
		 String process = null;
		 //Get Runtime environment of System
		 runTime = Runtime.getRuntime();
		 
		 try {
				
				Process p = runTime.exec("ps aux");
				InputStream inputStream = p.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				
				 String line = bufferedReader.readLine();
				 process = "&";
				 
				 while (line != null) {
				 line = bufferedReader.readLine();
				 
				 if((line + "&").contains(processName)){
					System.out.println(line);
					String[] lineVal = line.split("  ");
					process = lineVal[1];
				 }
				 
				 }
				 
				 Runtime.getRuntime().exec("kill " + process);
				 
				
//				Runtime.getRuntime().exec("kill -9 $(pgrep fileFlusher)");
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 
	 
	public static void main(String[] args) {
//		Thread.currentThread().getThreadGroup().getPa
		
		while (true) {
			System.out.println(ManagementFactory.getRuntimeMXBean().getName());
			GetProcessList gpl = new GetProcessList();
			gpl.showProcessData();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

