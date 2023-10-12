package energycontrol.front.cmd;

import mbt.utilities.BussinesException;
import mbt.utilities.BussinesObjectsValidator;
import mbt.utilities.EResult;
import mbt.utilities.GenericActionResult;
import mbt.utilities.Helper;
import mbt.utilities.PropertiesFileReader;
import mbt.utilities.PropertiesFileReaderFactory;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import energycontrol.back.bussines.DateHourValidator;
import energycontrol.back.bussines.EUserMessagesKeys;
import energycontrol.back.entities.DateHour;

/**
 * Validator de entrada por consola de un DateHour
 * 
 */
public class DateHourInputValidator implements BussinesObjectsValidator<String> 
{
	private static final Logger logger = LogManager.getLogger(DateHourInputValidator.class);
	
	static boolean initialized = false;
	
	static PropertiesFileReader pfr;
	static int yearMin;
	static int yearMax;
	
	private void readDefaultValues() throws IOException
	{
		pfr = PropertiesFileReaderFactory.getPropertiesFileReader("energycontrol-back.properties");
		String sYearMin = pfr.getProperty("YEAR_MIN", "2000");
		String sYearMax = pfr.getProperty("YEAR_MAX", "3000");
		
		yearMin = Integer.parseInt(sYearMin);
		yearMax = Integer.parseInt(sYearMax);
		
		initialized = true;
	}
	
	/**
	 * Valida que el texto pasado por argumento cumpla en patrón Se espera una cadena de texto con el siguiente formato: "yyyy MM dd HH" y,
	 * y que la fecha que representa esté dentro de los límites de fechas permitidos (definidos en el fichero energycontrol-back.properties)
	 * 
	 */
	@Override
	public GenericActionResult<BussinesException> validate(String objectToValidate) 
	{
		GenericActionResult<BussinesException> result = new GenericActionResult<BussinesException>();
		result.setActionResult(EResult.NOT_EXECUTED);
		
		if(!initialized)
		{
			try 
			{
				readDefaultValues();
			} 
			catch (IOException e) 
			{
				logger.error("Se ha producido un error inesperado en validate()", e);
				
				BussinesException be = new BussinesException(
						 "Se ha producido un error inesperado en DateHourInputValidator.validate()"
						,null
						,EUserMessagesKeys.InternalError.stringValue
						);
				be.addUserMessageArgument("DateHourInputValidator.validate()");
				result.addException(be);
				
				return result;
			}
		}
		
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
			//Validar entrada
			String[] inputParts = objectToValidate.split(" ");
			
			if(inputParts.length < 4)
			{
				result.setActionResult(EResult.KO);
				BussinesException be = new BussinesException(
					"Número de parámetros incorrecto",
					null,
					ECmdMessagesKeys.IncorrectNumberOfParameters.stringValue
					);
				be.addUserMessageArgument(inputParts.length);
				be.addUserMessageArgument(4);
				result.addException(be);
			}
			
			int year = 0;
			int month = 0;
			int day = 0;
			int hour = 0;
			
			try
			{
				year = Integer.parseInt(inputParts[0]);
				month = Integer.parseInt(inputParts[1]);
				day = Integer.parseInt(inputParts[2]);
				hour = Integer.parseInt(inputParts[3]);
			}
			catch(NumberFormatException e)
			{
				result.setActionResult(EResult.KO);
				BussinesException be = new BussinesException(
					"El valor introducido para hourId no es válido.",
					null,
					ECmdMessagesKeys.IncorrectParameter.stringValue
					);
				be.addUserMessageArgument("HourId");
				be.addUserMessageArgument(ECmdWordsKeys.ExpectedFormatHourIdValue.stringValue);
				result.addException(be);
				
				return result;
			}
			
			result = new DateHourValidator().validate(new DateHour(year, month, day, hour));
		}
		
		return result;
	}

}
