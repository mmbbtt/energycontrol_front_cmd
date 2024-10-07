package energycontrol.front.cmd;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Command;

import energycontrol.back.entities.Source;
import energycontrol.back.services.EnergyControlBackService;

public class LoadUfdConsumptionsFromCsvCommand extends Command 
{
	private ResourceBundleReader resourceBundleReader;
	private EnergyControlBackService backService;
	
	public LoadUfdConsumptionsFromCsvCommand(ResourceBundleReader resourceBundleReader, Scanner scanner, EnergyControlBackService backService) 
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
		
		CsvConsoleInput csvConsoleInput = new CsvConsoleInput(scanner);
		CsvInputValidator csvInputValidator = new CsvInputValidator();
		
		LocalDateConsoleInput localDateConsoleInput = new LocalDateConsoleInput(scanner);
		LocalDateInputValidator localDateValidator = new LocalDateInputValidator();
		
		//Recoger entrada nombre del fichero csv
		GenericActionResult<File> garCsv = csvConsoleInput.getBussinesInut(
			this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputCsvFileName.stringValue, null), //Introduce el nombre del csv
			csvInputValidator
			);
		if(garCsv.getResult() != EResult.OK)
		{
			result.setActionResult(EResult.KO);
			System.out.println(garCsv.getExceptionsMessages());
		}
		else
		{
			File fCsv = garCsv.getResultObject();
			
			//Recoger la fecha de los datos
			GenericActionResult<LocalDate> garSourcelDate = localDateConsoleInput.getBussinesInut(
				this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputSourceDate.stringValue, null), 
				localDateValidator
				);
			
			if(garSourcelDate.getResult() != EResult.OK)
			{
				result.setActionResult(EResult.KO);
				System.out.println(garSourcelDate.getExceptionsMessages());
			}
			else
			{
				LocalDate sourceDate = garSourcelDate.getResultObject();
				
				//Cargar los consumos
				GenericActionResult<Source> garSource = this.backService.loadUfdConsumptionsFromCsv(fCsv.getAbsolutePath(), sourceDate);
				
				if(garSource.getResult() != EResult.OK)
				{
					result.setActionResult(EResult.KO);
					System.out.println(garSource.getExceptionsMessages());
				}
				else
				{
					result = garSource;
					
					System.out.printf(
						"%s. %s\n"
						,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.CommandExecutedOk.stringValue, null)
						,this.resourceBundleReader.getLocalizedMessage(
							ECmdMessagesKeys.SourceConsumptionsLoaded.stringValue		//Consumos del origen de datos {0} cargados correctamente
							,new Object[] {garSource.getResultObject().getId()}
							)
						);
				}
			}
		}
		
		return result;
	}

}
