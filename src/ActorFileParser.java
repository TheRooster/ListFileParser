import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class ActorFileParser extends FileParser 
{

	PreparedStatement personStatement;
	PreparedStatement characterStatement;
	PreparedStatement performanceStatement;
	
	public ActorFileParser(String filename) 
	{
		super(filename);
		
	}
	
	public void parse(Scanner infile)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_CONN);
			
			personStatement = conn.prepareStatement("INSERT IGNORE INTO person(person_name) VALUES(?);");
			
			performanceStatement = conn.prepareStatement("INSERT INTO `performance`(person_id, character_id, movie_id) "
					+ "SELECT person_id, character_id, movie_id "
					+ "FROM person, `character`, movie "
					+ "WHERE movie_title = ? AND movie_year = ? AND person_name = ? AND character_name = ?;");
			
			characterStatement = conn.prepareStatement("INSERT IGNORE INTO `character`(character_name) VALUES(?);");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int numBatched = 0;
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
				
				String charName;
				if(line.contains("["))
				{
					charName = line.substring(line.lastIndexOf('[')+1, line.lastIndexOf(']'));
				}
				else
				{
					charName = "Unknown";
				}
				
				insertPerformance(personName, movieName, movieDate, charName);
				
				if(numBatched ++> 5000)
				{
					numBatched = 0;
					try {
						personStatement.executeBatch();
						characterStatement.executeBatch();
						performanceStatement.executeBatch();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				line = infile.nextLine();
			}while(!line.equals(""));
		}while(infile.hasNextLine());
		try {
			personStatement.executeBatch();
			characterStatement.executeBatch();
			performanceStatement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertPerformance(String personName, String movieName, String movieDate, String charName)
	{
		try {
			characterStatement.setString(1, charName);
			characterStatement.addBatch();
			
			performanceStatement.setString(1, movieName);
			performanceStatement.setString(2, movieDate);
			performanceStatement.setString(3, personName);
			performanceStatement.setString(4, charName);
			performanceStatement.addBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertPerson(String personName) 
	{
		try {
			personStatement.setString(1, personName);
			personStatement.addBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
