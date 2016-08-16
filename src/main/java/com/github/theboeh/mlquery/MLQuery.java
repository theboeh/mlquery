package com.github.theboeh.mlquery;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.Request;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;

public class MLQuery {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws URISyntaxException,
			XccConfigException, RequestException {
		Options options = new Options();
		options.addOption(OptionBuilder.withArgName("server:port")
				.withLongOpt("server").hasArg().isRequired().create("s"));
		options.addOption(OptionBuilder.withArgName("user:password")
				.withLongOpt("user").hasArg().isRequired().create("u"));
		CommandLineParser parser = new GnuParser();

		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar mlquery.jar [options]", options);
			System.exit(1);
		}

		String server = line.getOptionValue("s");
		//System.out.println("server: " + server);
		String user = line.getOptionValue("u");
		//System.out.println("user: " + user);
		user = URLEncoder.encode(user);

		StringBuilder queryBuilder = new StringBuilder();
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter(Pattern.compile("\\z"));
		while (scanner.hasNext()) {
			queryBuilder.append(scanner.next());
		}
		scanner.close();
		String query = queryBuilder.toString();

		URI uri = new URI("xcc://" + user + "@" + server);
		ContentSource contentSource = ContentSourceFactory
				.newContentSource(uri);
		Session session = contentSource.newSession();
		Request request = session.newAdhocQuery(query);
		ResultSequence rs = session.submitRequest(request);
		System.out.println(rs.asString());
		session.close();
	}
}
