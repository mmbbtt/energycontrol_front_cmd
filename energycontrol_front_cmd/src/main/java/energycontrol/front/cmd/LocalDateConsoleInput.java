package energycontrol.front.cmd;

import java.time.LocalDate;
import java.util.Scanner;

import mbt.utilities.Helper;
import mbt.utilities.consolemenu.ConsoleInput;

/**
 * ConsoleInput para entradas LocalDate.
 */
public class LocalDateConsoleInput extends ConsoleInput<LocalDate>
{
	public LocalDateConsoleInput(Scanner inputScanner) 
	{
		super(inputScanner);
	}

	@Override
	protected LocalDate getInstanceOfT(String[] paramsForConstructor) 
	{
		return Helper.string2Date(paramsForConstructor[0]); //Espera un formato de fecha como el indicado en la entrada DefaultDatePattern del fichero de propiedades
	}

}
