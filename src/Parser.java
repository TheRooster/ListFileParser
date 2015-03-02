import java.sql.SQLException;
import java.util.Scanner;

public class Parser
{

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException  
	{
		System.out.println("enter a filename: ");
		String s;
		Scanner keyboard = new Scanner(System.in);
		s = keyboard.nextLine();
		
		FileParser p;
		switch(s.substring(s.lastIndexOf('\\')+1))
		{
			case "genres.list":p = new GenreFileParser(s);break;
			case "actors.list":p = new ActorFileParser(s);break;
			case "actresses.list":p = new ActorFileParser(s);break;
			case "ratings.list":p = new RatingFileParser(s);break;
			case "directors.list":p = new DirectorFileParser(s);break;
			case "aka-names.list":p = new AKAFileParser(s);break;
			default: p = new FileParser(s);break;
		
		}
		
	}

}
