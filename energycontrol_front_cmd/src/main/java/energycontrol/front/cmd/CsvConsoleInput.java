package energycontrol.front.cmd;

import java.io.File;
import java.util.Scanner;

import mbt.utilities.consolemenu.ConsoleInput;

/**
 * ConsoleInput para ficheros csv.
 * 
 */
public class CsvConsoleInput extends ConsoleInput<File>
{
	public CsvConsoleInput(Scanner inputScanner) 
	{
		super(inputScanner);
	}

	@Override
	protected File getInstanceOfT(String[] paramsForConstructor) 
	{
		return new File(paramsForConstructor[0]);
	}

}
