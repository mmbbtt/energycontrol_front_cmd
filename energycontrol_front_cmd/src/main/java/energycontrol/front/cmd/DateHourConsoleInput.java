package energycontrol.front.cmd;

import java.util.Scanner;

import mbt.utilities.consolemenu.ConsoleInput;

import energycontrol.back.entities.DateHour;

/**
 * ConsoleInput para entradas DateHour.
 * 
 */
public class DateHourConsoleInput extends ConsoleInput<DateHour>
{

	public DateHourConsoleInput(Scanner inputScanner) 
	{
		super(inputScanner);
	}

	@Override
	protected DateHour getInstanceOfT(String[] paramsForConstructor) 
	{
		return new DateHour(
				Integer.parseInt(paramsForConstructor[0]),  //Año (yyyy)
				Integer.parseInt(paramsForConstructor[1]),  //Mes (MM)
				Integer.parseInt(paramsForConstructor[2]),	//Dia (dd)
				Integer.parseInt(paramsForConstructor[3])	//Hora (1 a 24)
				);
	}


}
