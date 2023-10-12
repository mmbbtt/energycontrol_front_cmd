package energycontrol.front.cmd;

import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import mbt.utilities.PropertiesFileReader;
import mbt.utilities.PropertiesFileReaderFactory;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.ResourceBundleReaderFactory;

import energycontrol.back.services.EnergyControlBackService;
import energycontrol.back.services.EnergyControlBackServiceImpl;

public class Main 
{
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) 
	{
		logger.debug("Entrando en Main");
		
		try
		{
			PropertiesFileReader pfr = PropertiesFileReaderFactory.getPropertiesFileReader("energycontrol-front-cmd.properties");
			String sLocaleLanguage = pfr.getProperty("Locale.Language", "es");
			String sLocaleCountry= pfr.getProperty("Locale.Country", "ES");
			Locale locale = new Locale(sLocaleLanguage, sLocaleCountry);
			ResourceBundleReader rb = ResourceBundleReaderFactory.getResourceBundleReader("CmdMessages", locale);
			
			EnergyControlBackService backService = new EnergyControlBackServiceImpl();
			MainMenu mainMenu = new MainMenu(rb, backService);
			mainMenu.run();
		}
		catch(Exception e)
		{
			logger.error("Se ha producido un error inesperado en Main.main()", e);
			e.printStackTrace();
		}
		
		logger.debug("Saliendo de Main");
	}

}
