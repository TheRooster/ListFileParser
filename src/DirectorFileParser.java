import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class DirectorFileParser extends FileParser 
{
	PreparedStatement directionStatement, personStatement;
	int numBatched;
	public DirectorFileParser(String filename) 
	{
		super(filename);
		numBatched = 0;

	}
	
	public void parse(Scanner infile)
	{
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			conn = DriverManager.getConnection(DB_CONN);
			personStatement = conn.prepareStatement("INSERT IGNORE INTO person(person_name) VALUES(?);");
			directionStatement = conn.prepareStatement("INSERT INTO direction SELECT person_id, movie_id "
																			+ "FROM person, movie "
																			+ "WHERE person_name = ? AND movie_title = ? AND movie_year = ?;");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do
		{
			String line = infile.nextLine();
			String personName = line.substring(0, line.indexOf('\t'));
			insertPerson(personName);
			line = line.substring(line.lastIndexOf('\t'));
			
			do
			{
				Scanner s = new Scanner(line);
				
				//Grab all the words in the movie name
				String movieName = "";
				String temp = s.next();
				while(!(temp.length() >= 6 && temp.charAt(0) == '(' && temp.endsWith(")") && (temp.charAt(1) == '1' || temp.charAt(1) == '2' || temp.charAt(1) == '?')))
				{
					movieName += temp + " ";
					temp= s.next();
				}
				if(temp.equals("(????)"))
				{
					temp = "(0000)";
				}
				//We know temp now holds the date
				String movieDate = temp.substring(1,5);
				
				
				insertDirection(personName, movieName, movieDate);
				line = infile.nextLine();
			}while(!line.equals(""));
		}while(infile.hasNextLine());
		
		
		try {
			personStatement.executeBatch();
			directionStatement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void insertDirection(String personName, String movieName, String movieDate)
	{
		try {
			directionStatement.setString(1, personName);
			directionStatement.setString(2, movieName);
			directionStatement.setString(3, movieDate);
			directionStatement.addBatch();
			
			if(numBatched++ > 5000)
			{
				numBatched = 0;
				personStatement.executeBatch();
				directionStatement.executeBatch();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private void insertPerson(String personName) 
	{
		try {
			personStatement.setString(1, personName);
			personStatement.addBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
