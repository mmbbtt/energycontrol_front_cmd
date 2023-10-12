package energycontrol.front.cmd;

import java.util.Scanner;

import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Command;

import energycontrol.back.entities.DateHour;
import energycontrol.back.services.EnergyControlBackService;

/**
 * Command para el MenuItem que genera y persiste DateHours entre las fechas introducidas desde consola
 * 
 */
public class GenerateDateHoursCommand extends Command 
{
	private ResourceBundleReader resourceBundleReader;
	private DateHour dateHourFrom;
	private DateHour dateHourTo;
	
	private EnergyControlBackService backService;
	
	public GenerateDateHoursCommand(ResourceBundleReader resourceBundleReader, Scanner scanner, EnergyControlBackService backService)
	{
		super(scanner);
		
		this.resourceBundleReader = resourceBundleReader;
		this.backService = backService;
	}
	
	@Override
	public ActionResult execute() 
	{
		ActionResult result = new ActionResult();
		
		System.out.println();
		DateHourConsoleInput ci = new DateHourConsoleInput(scanner);
		
		DateHourInputValidator inputDateHourValidator = new DateHourInputValidator();
		
		//Recoger entrada para DateHourFrom
		GenericActionResult<DateHour> garDateHour = ci.getBussinesInut(
			this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputDateHourFrom.stringValue, null), //Introduce el valor para dateHourFrom (yyyy MM dd HH)
			inputDateHourValidator
			);
				
		if(garDateHour.getResult() != EResult.OK)
		{
			result.setActionResult(EResult.KO);
			System.out.println(garDateHour.getExceptionsMessages());
		}
		else
		{
			dateHourFrom = garDateHour.getResultObject();
			
			//Recoger entrada para DateHourTo
			garDateHour = ci.getBussinesInut(
				this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputDateHourTo.stringValue, null), //Introduce el valor para dateHourTo (yyyy MM dd HH)
				inputDateHourValidator
				);
					
			if(garDateHour.getResult() != EResult.OK)
			{
				result.setActionResult(EResult.KO);
				System.out.println(ActionResult.getLocalUserMessage(garDateHour, resourceBundleReader));
			}
			else
			{
				dateHourTo = garDateHour.getResultObject();
				
				//Generar los DateHours
				GenericActionResult<Integer> gar = this.backService.generateDateHours(this.dateHourFrom, this.dateHourTo);
				
				if(gar.getResult() == EResult.OK)
				{
					System.out.print(String.format(
						"%s. %s\n"
						,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.CommandExecutedOk.stringValue, null)
						,this.resourceBundleReader.getLocalizedMessage(
								ECmdMessagesKeys.RowsAffected.stringValue
								,new Object[] {gar.getResultObject()}
								)
						)
						);
				}
				else
				{
					System.out.printf("%s: %s\n",
						 this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.CommandExecutedKo.stringValue, null)
						,ActionResult.getLocalUserMessage(gar, this.resourceBundleReader)
						);
				}
				
				result = gar;
			}
		}
		
		return result;
	}
}
