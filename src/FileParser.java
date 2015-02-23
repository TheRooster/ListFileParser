import java.io.*;
import java.util.*;
import java.sql.*;

public class FileParser
{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://54.201.253.2:3306/imdb";
	
	Connection conn = null;
	PreparedStatement stmt = null;
	
	public FileParser(String filename)
	{
		
		try
		{
			File infile = new File(filename);
			Scanner in = new Scanner(infile);
			parse(in);
		} catch(FileNotFoundException e){
			System.out.println("file not found");
			System.exit(0);
		}
		
		
	}
	
	public void parse(Scanner infile) {};

}
