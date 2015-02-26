import java.util.Scanner;

public class RatingFileParser extends FileParser 
{

	public RatingFileParser(String filename)
	{
		super(filename);	
	}
	
	public void parse(Scanner infile)
	{
		
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
	}

	private void insert(String movieName, String movieDate, String rating, String votes)
	{
		System.out.printf("%s:%s:%s:%s\n", movieName, movieDate, rating, votes);
	}
	

}
