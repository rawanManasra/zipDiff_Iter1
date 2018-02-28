package zipDiff_iter1;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class logfile {
	public final String username = System.getProperty("user.name");
	public String opType;
	final static Logger logger = Logger.getLogger(logfile.class);
	public final String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	public logfile(String string) {
		opType = string;
		BasicConfigurator.configure();
		logger.info("jjj");
	}

	public String toString() {
		return "time : " + timeStamp + ", user: " + username + " ,Operation: " + opType;
	}
}
