import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class RatingFileParser extends FileParser 
{
	PreparedStatement ratingStatement;
	PreparedStatement ratingStatement2;
	int numBatched;

	public RatingFileParser(String filename)
	{
		super(filename);
		numBatched = 0;
	}
	
	public void parse(Scanner infile)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_CONN);
			ratingStatement = conn.prepareStatement("INSERT INTO ratings(movie_id) SELECT movie_id FROM movie WHERE movie_title = ? AND movie_year = ?;");
			ratingStatement2 = conn.prepareStatement("INSERT INTO ratings(votes, rank) VALUES(?,?);");
			
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do
		{
			String line = infile.nextLine();
			String votes = line.substring(17, 24);
			String rating = line.substring(27, 30);
			
			line = line.substring(32);
			
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
			
			insert(movieName, movieDate, rating, votes);
		}while(infile.hasNextLine());
		
		try {
			ratingStatement.executeBatch();
			ratingStatement2.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void insert(String movieName, String movieDate, String rating, String votes)
	{
		try {
			ratingStatement.setString(1, movieName);
			ratingStatement.setString(2, movieDate);
			
			ratingStatement2.setString(1, votes);
			ratingStatement2.setString(2, rating);
			
			ratingStatement.addBatch();
			ratingStatement2.addBatch();
			
			if(numBatched ++> 5000)
			{
				numBatched = 0;
				ratingStatement.executeBatch();
				ratingStatement2.executeBatch();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
