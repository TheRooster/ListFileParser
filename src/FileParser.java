import java.io.*;
import java.util.*;
import java.sql.*;

public class FileParser
{
	
	Connection conn = null;
	
	final String DB_CONN = "";
	
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
