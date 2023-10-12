package energycontrol.front.cmd;

import java.util.List;
import java.util.Scanner;

import mbt.utilities.ActionResult;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Command;

import energycontrol.back.entities.MSource;
import energycontrol.back.services.EnergyControlBackService;

/**
 * Command para el MenuItem que muestra las MSources registradas.
 */
public class ViewMSourcesCommand extends Command 
{
	private ResourceBundleReader resourceBundleReader;
	private EnergyControlBackService backService;
	
	public ViewMSourcesCommand(ResourceBundleReader resourceBundleReader, Scanner scanner, EnergyControlBackService backService) 
	{
		super(scanner);
		
		this.resourceBundleReader = resourceBundleReader;
		this.backService = backService;
	}

	@Override
	public ActionResult execute() 
	{
		GenericActionResult<List<MSource>> result = new GenericActionResult<List<MSource>> ();
		
		System.out.println();
		
		result = this.backService.getMSources();
		
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
			List<MSource> lMSources = result.getResultObject();
			
			if((lMSources != null) && (lMSources.size() > 0))
			{
				for(MSource ms : lMSources)
				{
					System.out.println(ms.toString());
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
