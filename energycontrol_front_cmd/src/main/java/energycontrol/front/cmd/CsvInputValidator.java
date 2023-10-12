package energycontrol.front.cmd;

import java.io.File;

import mbt.utilities.BussinesException;
import mbt.utilities.BussinesObjectsValidator;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.Helper;

/**
 *  Validator de entrada por consola de ficheros csv.
 *  
 */
public class CsvInputValidator implements BussinesObjectsValidator<String> 
{
	@Override
	public GenericActionResult<BussinesException> validate(String objectToValidate) 
	{
		GenericActionResult<BussinesException> result = new GenericActionResult<BussinesException>();
		result.setActionResult(EResult.NOT_EXECUTED);
		
		if(Helper.stringIsNullOrEmpty(objectToValidate))
		{
			result.setActionResult(EResult.KO);
			BussinesException be = new BussinesException(
					 "Parameter is null"
					,null
					,ECmdMessagesKeys.NullParameter.stringValue
					);
			result.addException(be);
		}
		else
		{
			File f = new File(objectToValidate);
			
			if(f.exists())
			{
				result.setActionResult(EResult.OK);
			}
			else
			{
				result.setActionResult(EResult.KO);
				
				BussinesException be = new BussinesException(
					 String.format("El fichero %s no existe", objectToValidate)
					,null
					,ECmdMessagesKeys.FileNotExist.stringValue
					);
					be.addUserMessageArgument(objectToValidate);
				
				result.addException(be);
			}
			
			f = null;
		}
		
		return result;
	}

}
