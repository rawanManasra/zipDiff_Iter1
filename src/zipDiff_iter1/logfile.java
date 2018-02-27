package zipDiff_iter1;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class logfile {
	public final String username = System.getProperty("user.name");
	public Timestamp logTime;
	OperationType opType;
	final static Logger logger = Logger.getLogger(logfile.class);

	public static void main(String[] args) {
		String username = System.getProperty("user.name");
		System.out.println("username = " + username);
	}

}
