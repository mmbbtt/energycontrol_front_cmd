package energycontrol.front.cmd;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Command;
import mbt.utilities.consolemenu.ConsoleInput;

import energycontrol.back.entities.Bill;
import energycontrol.back.entities.ECollectionMethod;
import energycontrol.back.services.EnergyControlBackService;

/**
 * Command para el MenuItem que carga una factura de Naturgy desde su csv con el detalle de consumos.
 * 
 */
public class LoadBillNaturgyFromCsvCommand extends Command 
{
	private ResourceBundleReader resourceBundleReader;
	private EnergyControlBackService backService;
	
	public LoadBillNaturgyFromCsvCommand(ResourceBundleReader resourceBundleReader, Scanner scanner, EnergyControlBackService backService) 
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
			
			//Recoger la fecha de la factura
			GenericActionResult<LocalDate> garBillDate = localDateConsoleInput.getBussinesInut(
				this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.InputBillDate.stringValue, null), 
				localDateValidator
				);
			
			if(garBillDate.getResult() != EResult.OK)
			{
				result.setActionResult(EResult.KO);
				System.out.println(garBillDate.getExceptionsMessages());
			}
			else
			{
				LocalDate billDate = garBillDate.getResultObject();
				
				//Recoger el método de obtención de los consumos
				List<String> lItemsToSelect = new ArrayList<String>();
				lItemsToSelect.add(this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.TextMenuItemCollectionMethodReal.stringValue, null));
				lItemsToSelect.add(this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.TextMenuItemCollectionMethodSimulated.stringValue, null));
				lItemsToSelect.add(this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.TextMenuItemCollectionMethodUnknow.stringValue, null));
				
				List<String> exitKeys = new ArrayList<String>();
				exitKeys.add("v");
				exitKeys.add("V");
				
				GenericActionResult<String> garCollectionMethod = ConsoleInput.selectItemList(
					lItemsToSelect, 
					this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.TextMenuCaptionCollectionMethod.stringValue, null),
					exitKeys, 
					this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.TextMenuInputCollectionMethod.stringValue, null)
					);
				
				if(garCollectionMethod.getResult() == EResult.OK)
				{
					String sSelection = garCollectionMethod.getResultObject();
					
					if(exitKeys.contains(sSelection))
					{
						result.setActionResult(EResult.NOT_EXECUTED);
					}
					else
					{
						ECollectionMethod ecm = ECollectionMethod.UNKNOWN;
						
						if("1".equals(sSelection))
						{
							ecm = ECollectionMethod.REAL;
						}
						else if("2".equals(sSelection))
						{
							ecm = ECollectionMethod.SIMULATED;
						}
						
						//Cargar los consumos de la factura
						GenericActionResult<Bill> garBill = this.backService.loadBillNaturgyFromCsv(
							fCsv.getAbsolutePath(), 
							billDate, 
							ecm
							);
						
						if(garBill.getResult() != EResult.OK)
						{
							result.setActionResult(EResult.KO);
							System.out.println(garBill.getExceptionsMessages());
						}
						else
						{
							result = garBill;
							
							System.out.printf(
								"%s. %s\n"
								,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.CommandExecutedOk.stringValue, null)
								,this.resourceBundleReader.getLocalizedMessage(
									ECmdMessagesKeys.BillLoaded.stringValue
									,new Object[] {garBill.getResultObject().getBillNumber()}
									)
								);
						}
					}
				}
				else
				{
					result.setActionResult(EResult.KO);
					System.out.println(ActionResult.getLocalUserMessage(garCollectionMethod, resourceBundleReader));
				}
			}
		}
		
		return result;
	}

}
