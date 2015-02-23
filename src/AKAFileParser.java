import java.util.Scanner;

public class AKAFileParser extends FileParser
{

	public AKAFileParser(String filename)
	{
		super(filename);
	}
	
	public void parse(Scanner infile)
	{
		do
		{
			String personName = infile.nextLine();
			String akaName = infile.nextLine();
			do
			{
				akaName = akaName.substring(akaName.indexOf('(')+3, akaName.lastIndexOf(')')-1);
				
				insert(personName, akaName);
				akaName = infile.nextLine();
			}while(!akaName.equals(""));
		}while(infile.hasNextLine());
	}

	private void insert(String personName, String akaName)
	{
		System.out.println(personName + ":" + akaName);
	}

}
