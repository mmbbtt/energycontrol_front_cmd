package energycontrol.front.cmd;

public enum ECmdWordsKeys 
{
	Day("Day"),
	Month("Month"),
	Year("Year"),
	Hour("Hour"),
	Date("Date"),
	ExpectedFormatHourIdValue("ExpectedFormatHourIdValue"),
	CommandExecutedOk("CommandExecutedOk"),
	CommandExecutedKo("CommandExecutedKo"),
	ExpectedFormatDateValue("ExpectedFormatDateValue");
	
	public final String stringValue;
	
	private ECmdWordsKeys(String stringValue)
	{
		this.stringValue = stringValue;
	}
}
