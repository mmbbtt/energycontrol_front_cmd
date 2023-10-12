package energycontrol.front.cmd;

public enum ECmdMessagesKeys 
{
	TextInputMenu("TextInputMenu"),
	TextMenuItemGenerateDateHours("TextMenuItemGenerateDateHours"),
	TextMenuItemViewMSources("TextMenuItemViewMSources"),
	TextMenuItemViewSources("TextMenuItemViewSources"),
	TextMenuLoadUdfConsumptiosFromCsv("TextMenuLoadUdfConsumptiosFromCsv"),
	TextMenuLoadEE2ConsumptiosFromCsv("TextMenuLoadEE2ConsumptiosFromCsv"),
	TextMenuLoadBillNaturgyFromCsv("TextMenuLoadBillNaturgyFromCsv"),
	TextMenuGenerateGnuPlotFileVerifiedBill("TextMenuGenerateGnuPlotFileVerifiedBill"),
	InputDateHourFrom("InputDateHourFrom"),
	InputDateHourTo("InputDateHourTo"),
	InputCsvFileName("InputCsvFileName"),
	InputBillDate("InputBillDate"),
	InputBillNumber("InputBillNumber"),
	InputSourceDate("InputSourceDate"),
	InputSource("InputSource"),
	InputMSourceCode("InputMSourceCode"),
	InputSourceMenu("InputSourceMenu"),
	InputSourceMenuNuevo("InputSourceMenuNuevo"),
	//->Menu Collection Method
	TextMenuCaptionCollectionMethod("TextMenuCaptionCollectionMethod"),
	TextMenuItemCollectionMethodReal("TextMenuItemCollectionMethodReal"),
	TextMenuItemCollectionMethodSimulated("TextMenuItemCollectionMethodSimulated"),
	TextMenuItemCollectionMethodUnknow("TextMenuItemCollectionMethodUnknow"),
	TextMenuInputCollectionMethod("TextMenuInputCollectionMethod"),
	//<-
	GetSourcesError("GetSourcesError"),
	GetSourcesNoneRegistred("GetSourcesNoneRegistred"),
	RowsAffected("RowsAffected"),
	CommandExecutedOk("CommandExecutedOk"),
	CommandExecutedKo("CommandExecutedKo"),
	BillLoaded("BillLoaded"),
	SourceConsumptionsLoaded("SourceConsumptionsLoaded"),
	NullParameter("NullParameter"),
	IncorrectNumberOfParameters("IncorrectNumberOfParameters"),
	IncorrectParameter("IncorrectParameter"),
	ValueNotValidFor("ValueNotValidFor"),
	NoData("NoData"),
	FileNotExist("FileNotExist");
	
	public final String stringValue;
	
	private ECmdMessagesKeys(String stringValue)
	{
		this.stringValue = stringValue;
	}
}
