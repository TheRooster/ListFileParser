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
		
		//FileParser p = new GenreFileParser(s);
		FileParser p = new DirectorFileParser(s);
		//switch(s.substring(s.lastIndexOf('/')+1))
		//{
		//case "genres.list":p = new GenreFileParser(s);
		
		//}
		
	}

}
