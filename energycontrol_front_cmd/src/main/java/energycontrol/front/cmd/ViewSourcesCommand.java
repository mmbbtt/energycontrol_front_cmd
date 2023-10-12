package energycontrol.front.cmd;

import java.util.List;
import java.util.Scanner;

import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Command;

import energycontrol.back.entities.Source;
import energycontrol.back.services.EnergyControlBackService;

/**
 * Command para el MenuItem que muestra las Sources registradas.
 */
public class ViewSourcesCommand extends Command 
{
	private ResourceBundleReader resourceBundleReader;
	private EnergyControlBackService backService;
	
	public ViewSourcesCommand(ResourceBundleReader resourceBundleReader, Scanner scanner, EnergyControlBackService backService) 
	{
		super(scanner);
		
		this.resourceBundleReader = resourceBundleReader;
		this.backService = backService;
	}

	@Override
	public ActionResult execute() 
	{
		GenericActionResult<List<Source>> result = new GenericActionResult<List<Source>> ();
		
		System.out.println();
		
		result = this.backService.getSources();
		
		if(result.getResult() != EResult.OK)
		{
			System.out.print(String.format(
				"%s: \n%s"
				,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.CommandExecutedKo.stringValue, null)
				,ActionResult.getLocalUserMessage(result, this.resourceBundleReader)
				));	
		}
		else
		{
			List<Source> lSources = result.getResultObject();
			
			if((lSources != null) && (lSources.size() > 0))
			{
				for(Source s : lSources)
				{
					System.out.println(s.toString());
				}
			}
			else
			{
				System.out.print(String.format(
					"%s.\n"
					,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.NoData.stringValue, null)
					));
			}
		}
		
		return result;
	}

}
