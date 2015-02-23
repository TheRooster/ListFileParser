import java.util.Scanner;


public class DirectorFileParser extends FileParser 
{

	public DirectorFileParser(String filename) 
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
	}

	private void insertDirection(String personName, String movieName, String movieDate)
	{
		System.out.printf("%s:%s:%s\n", personName, movieName, movieDate);
		
	}

	private void insertPerson(String personName) {
		
	}

}
