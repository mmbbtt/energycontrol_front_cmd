package energycontrol.front.cmd;

import java.util.ArrayList;
import java.util.List;

import mbt.utilities.BussinesException;
import mbt.utilities.GenericActionResult;
import mbt.utilities.ResourceBundleReader;
import mbt.utilities.consolemenu.Menu;
import mbt.utilities.consolemenu.MenuItem;

import energycontrol.back.services.EnergyControlBackService;

/**
 * Menú principal de la aplicación
 */
public class MainMenu 
{
	private ResourceBundleReader resourceBundleReader;
	private Menu menu;
	private EnergyControlBackService backService = null;
	
	/**
	 * Constructor obligatorio.
	 * 
	 * @param resourceBundleReader
	 * @param backService
	 */
	public MainMenu(ResourceBundleReader resourceBundleReader, EnergyControlBackService backService)
	{
		this.resourceBundleReader = resourceBundleReader;
		this.backService = backService;
		
		try
		{
			//Teclas para salir de la aplicación
			List<String> exitKeys = new ArrayList<String>();
			exitKeys.add("S");
			exitKeys.add("s");
			exitKeys.add("E");
			exitKeys.add("e");
			exitKeys.add("Q");
			exitKeys.add("q");
			
			//Menú principal
			menu = new Menu(
				 "Energy Control"
				,exitKeys
				,this.resourceBundleReader.getLocalizedMessage(ECmdMessagesKeys.TextInputMenu.stringValue, null) //Introduce una opción (s para salir)
				);
			
			String textMenuItem = null;
			//Menu item 1: para generar HourIds
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
					 ECmdMessagesKeys.TextMenuItemGenerateDateHours.stringValue
					,new Object[] {"1"}
					);
	
			MenuItem miGenerateHoursIds = new MenuItem(
					 new GenerateDateHoursCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
					,textMenuItem 
					,"1"
					);
				
			menu.addMenuItem(1, miGenerateHoursIds);
			
			//Menu item 2: listar MSources
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
				 ECmdMessagesKeys.TextMenuItemViewMSources.stringValue
				,new Object[] {"2"}
				);
			
			MenuItem miViewMSources = new MenuItem(
				 new ViewMSourcesCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
				,textMenuItem
				,"2"
				);
			
			menu.addMenuItem(2, miViewMSources);
			
			//Menu item 3: listar Sources
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
				 ECmdMessagesKeys.TextMenuItemViewSources.stringValue
				,new Object[] {"3"}
				);
			
			MenuItem miViewSources = new MenuItem(
				 new ViewSourcesCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
				,textMenuItem
				,"3"
				);
			
			menu.addMenuItem(3, miViewSources);
			
			//Menu item 4: cargar facturas Naturgy
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
				 ECmdMessagesKeys.TextMenuLoadBillNaturgyFromCsv.stringValue
				,new Object[] {"4"}
				);
			
			MenuItem miLoadBillNaturgyFromCsvCommand = new MenuItem(
				 new LoadBillNaturgyFromCsvCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
				,textMenuItem
				,"4"
				);
			
			menu.addMenuItem(4, miLoadBillNaturgyFromCsvCommand);
			
			//Menu item 5: cargar consumos Efergy E2
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
				 ECmdMessagesKeys.TextMenuLoadEE2ConsumptiosFromCsv.stringValue
				,new Object[] {"5"}
				);
			
			MenuItem miLoadEfergyE2ConsumptionsFromCsvCommand = new MenuItem(
				 new LoadEfergyE2ConsumptionsFromCsvCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
				,textMenuItem
				,"5"
				);
			
			menu.addMenuItem(5, miLoadEfergyE2ConsumptionsFromCsvCommand);
			
			//Menu item 6: Generar archivo de datos para GnuPlot
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
				 ECmdMessagesKeys.TextMenuGenerateGnuPlotFileVerifiedBill.stringValue
				,new Object[] {"6"}
				);
			
			MenuItem miGenerateGnuPlotFileVerifiedBillCommand= new MenuItem(
				 new GenerateGnuPlotFileVerifiedBillCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
				,textMenuItem
				,"6"
				);
			
			menu.addMenuItem(6, miGenerateGnuPlotFileVerifiedBillCommand);
			
			//Menu item 7: Comprobar una factura
			textMenuItem = this.resourceBundleReader.getLocalizedMessage(
				 ECmdMessagesKeys.TextMenuCheckBill.stringValue
				,new Object[] {"7"}
				);
			
			MenuItem miCheckBillCommand = new MenuItem(
				 new CheckBillCommand(this.resourceBundleReader, menu.getScanner(), this.backService)
				,textMenuItem
				,"7"
				);
			
			menu.addMenuItem(7, miCheckBillCommand);
		}
		catch(BussinesException be)
		{
			System.out.println(BussinesException.getLocalUserMessage(be, this.resourceBundleReader));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			GenericActionResult<String> gar = this.menu.run();
			
			System.out.printf("%s", 
				GenericActionResult.getLocalUserMessage(gar, this.resourceBundleReader)
				);
		}
		catch(BussinesException be)
		{
			System.out.println(BussinesException.getLocalUserMessage(be, this.resourceBundleReader));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
