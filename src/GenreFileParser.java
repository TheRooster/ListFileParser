import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class GenreFileParser extends FileParser
{

	public GenreFileParser(String filename) 
	{
		super(filename);
	}
	
	@Override 
	public void parse(Scanner infile)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://52.10.179.200:3306/imdb?user=root&password=Th3_R005t3r");
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
		
		
		
		String line = infile.nextLine();
		while(infile.hasNextLine())
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
			
			temp = s.next();
			
			//Grab the TV indicator, set to M if not there
			String tvIndicator = "";
			if(temp.charAt(0) == '(')
			{
				tvIndicator = temp.substring(1, temp.length()-1);
				temp = s.next();
			}
			else
			{
				tvIndicator = "M";
			}
			
			
			String genreName = line.substring(line.lastIndexOf('\t')+1);
			
			//System.out.println(movieName+ ":"+ movieDate);
			insert(movieName, movieDate, tvIndicator, genreName);
			
			
			line=infile.nextLine();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insert(String movieName, String movieDate, String tvIndicator, String genreName) 
	{
		try {
			
			
			stmt = conn.prepareStatement("INSERT IGNORE INTO genre(genre_name) VALUES(?);");
			stmt.setString(1, genreName);
			stmt.execute();
			
			stmt = conn.prepareStatement("INSERT IGNORE INTO movie (movie_year, movie_title, movie_tv_ind) VALUES(?, ?, ?);");
			stmt.setString(1, movieDate);
			stmt.setString(2, movieName);
			stmt.setString(3, tvIndicator);
			stmt.execute();
			
			stmt = conn.prepareStatement("INSERT INTO movieGenre (genre_id, movie_id) SELECT genre_id, movie_id FROM genre, movie WHERE genre.genre_name = ? AND movie.movie_title = ? AND movie.movie_year = ?;");
			stmt.setString(1, genreName);
			stmt.setString(2, movieName);
			stmt.setString(3, movieDate);
			stmt.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}
