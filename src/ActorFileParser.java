import java.util.Scanner;


public class ActorFileParser extends FileParser 
{

	public ActorFileParser(String filename) 
	{
		super(filename);
		// TODO Auto-generated constructor stub
	}
	
	public void parse(Scanner infile)
	{
		
		do
		{
			String line = infile.nextLine();
			String personName = line.substring(0, line.indexOf('\t'));
			
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
				line = infile.nextLine();
			}while(!line.equals(""));
		}while(infile.hasNextLine());
		
	}

	private void insertPerformance(String personName, String movieName, String movieDate, String charName)
	{
		System.out.printf("%s:%s:%s:%s\n", personName, movieName, movieDate, charName);
		
	}

	private void insertPerson(String personName) 
	{
		System.out.println(personName);
		
	}

}
