package energycontrol.front.cmd;

import java.time.LocalDate;

import mbt.utilities.BussinesException;
import mbt.utilities.BussinesObjectsValidator;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.Helper;

public class LocalDateInputValidator implements BussinesObjectsValidator<String> 
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
			try
			{
				LocalDate ld = Helper.string2Date(objectToValidate);
				if(ld != null)
				{
					result.setActionResult(EResult.OK);
				}
				else
				{
					result.setActionResult(EResult.KO);
					BussinesException be = new BussinesException(
						 "Error al validar la fecha."
						,null
						,ECmdMessagesKeys.ValueNotValidFor.stringValue //El valor {0} no es válido para asignar a un dato de tipo {1}
						);
					be.addUserMessageArgument(objectToValidate);
					be.addUserMessageArgument(ECmdWordsKeys.Date); 
					result.addException(be);
				}
			}
			catch(Exception e)
			{
				result.setActionResult(EResult.KO);
				BussinesException be = new BussinesException(
					 String.format("Formato de fecha incorrecto: %s", e.getMessage())
					,e
					,ECmdMessagesKeys.ValueNotValidFor.stringValue //El valor {0} no es válido para asignar a un dato de tipo {1}
					);
				be.addUserMessageArgument(objectToValidate);
				be.addUserMessageArgument(ECmdWordsKeys.Date); 
				result.addException(be);
			}
		}
		
		return result;
	}

}
