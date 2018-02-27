package zipDiff_iter1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class logfile {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	public final String username = System.getProperty("user.name");
	public Timestamp logTime;
	OperationType opType;

	public static void main(String[] args) {
		String username = System.getProperty("user.name");
		System.out.println("username = " + username);
	}

}
