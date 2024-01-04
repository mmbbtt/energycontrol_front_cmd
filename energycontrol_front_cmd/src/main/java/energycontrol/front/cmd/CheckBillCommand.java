package energycontrol.front.cmd;

import java.util.Scanner;

import energycontrol.back.services.EnergyControlBackService;
import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Command;

public class CheckBillCommand extends Command 
{
	private ResourceBundleReader resourceBundleReader;
	private EnergyControlBackService backService;

	public CheckBillCommand(ResourceBundleReader resourceBundleReader, Scanner scanner, EnergyControlBackService backService) 
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
		
		String sBillNumber = null;
		String sMSourceCode = null;
		TextConsoleInput tci = new TextConsoleInput(scanner);
		
		//Recoger el número de factura
		GenericActionResult<String> garBillNumber = tci.getBussinesInut(
			 this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputBillNumber.stringValue, null)
			,null
			); //Introduce el número de factura
		
		if(garBillNumber.getResult() != EResult.OK)
		{
			result.setActionResult(EResult.KO);
			System.out.println(garBillNumber.getExceptionsMessages());
		}
		else
		{
			sBillNumber = garBillNumber.getResultObject();
			
			//Recoger el código del maestro del orgien de datos verificados
			GenericActionResult<String> garMSourceCode = tci.getBussinesInut(
				 this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputMSourceCode.stringValue, null)
				,null
				); //Introduce el código del maestro del origen de consumos a usar para verificar la factura
			
			if(garMSourceCode.getResult() != EResult.OK)
			{
				result.setActionResult(EResult.KO);
				System.out.println(garMSourceCode.getExceptionsMessages());
			}
			else
			{
				sMSourceCode = garMSourceCode.getResultObject();
				
				//Comprobar la factura
				GenericActionResult<String>  gar = this.backService.checkBill(sBillNumber, sMSourceCode);
				
				System.out.println(gar.getResultObject());
				if(gar.getResult() != EResult.OK)
				{
					result.setActionResult(EResult.KO);
					System.out.println(gar.getExceptionsMessages());
				}
				else
				{
					result = gar;
					
					System.out.printf(
						"%s\n"
						,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.CommandExecutedOk.stringValue, null)
						);
				}
				
			}
		}
		
		return result;
	}

}
