import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
class FindIP {
	public static void main(String arg[]) throws Exception{
			Scanner sc = new Scanner(System.in);
		System.out.print("Website name: ");
		 String st = sc.nextLine();
		InetAddress ip = InetAddress.getByName(st);
		ExecutorService executor = Executors.newFixedThreadPool(500);
		AtomicInteger count = new AtomicInteger(0);
		List<Integer> openport = Collections.synchronizedList(new ArrayList<>());
		System.out.println("The IP Address is: " + ip.getHostAddress());
		String host = ip.getHostAddress();
		int timeout = 500;
		int[] ports = {21, 22, 23, 25, 53, 80, 110, 143, 443, 445, 587, 993, 995, 1433, 1521, 1723, 3306, 3389, 5900};
String[] services = {"FTP", "SSH", "Telnet", "SMTP", "DNS", "HTTP", "POP3", "IMAP","HTTPS", "SMB", "SMTP Secure", "IMAP SSL", "POP3 SSL", "MSSQL", "Oracle DB", "PPTP", "MySQL", "RDP", "VNC"};
		long startTime = System.currentTimeMillis();
		for (int port = 18; port <= 1600; port++) {
			int currentport = port;
			executor.execute(() -> {
				if (portOpen(host, currentport, timeout) == true) {
					count.incrementAndGet();
					openport.add(currentport);
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		Collections.sort(openport);
	System.out.println("Open ports are: \n");
	System.out.printf("%-10s %-15s%n", "Port No.", "Service");
	System.out.println ("——————————————————");
	int demo=0;
		for (int op : openport) {
			for(int k=0;k<ports.length;k++){
				if(op == ports[k]){
					demo= k;
			System.out.printf("%-10d %-15s%n", op, services[k]);
				}
			}
		}
		System.out.println("\nTotal open port (" + count + ")");
		long elapsedTime = System.currentTimeMillis() - startTime;
		Time(elapsedTime);
	}
	public static void Time(long eT){
		long ms = eT % 1000;
		long sec = (eT / 1000) % 60;
		long min = (eT / (1000 * 60)) % 60;
		long hr = (eT / (1000 * 60 * 60));

		System.out.println("\nScanning time: " + hr + "hr:" + min + "min:" + sec + "sec:" + ms + "ms");
	}
	public static boolean portOpen(String h, int p, int t) {
		try(Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(h, p), t);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}