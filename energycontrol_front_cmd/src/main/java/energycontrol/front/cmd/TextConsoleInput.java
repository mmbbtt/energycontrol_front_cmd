package energycontrol.front.cmd;

import java.util.Scanner;

import mbt.utilities.consolemenu.ConsoleInput;

/**
 * ConsoleInput para cadenas de texto. 
 * Sólo una palabra.
 * 
 */
public class TextConsoleInput extends ConsoleInput<String>
{
	public TextConsoleInput(Scanner inputScanner) 
	{
		super(inputScanner);
	}

	@Override
	protected String getInstanceOfT(String[] paramsForConstructor) 
	{
		return paramsForConstructor[0].trim();
	}

}
