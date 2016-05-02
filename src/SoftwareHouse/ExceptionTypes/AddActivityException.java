package SoftwareHouse.ExceptionTypes;

public class AddActivityException{
	private boolean isSomethingAmiss = false;
	private boolean isMissingTitle = false;
	private boolean isMissingDetailText = false;
	private boolean isMissingEmployees = false;
	private boolean isMissingStartDay = false;
	private boolean isMissingEndDay = false;
	private boolean isNotCorrectOrderTime = false;
	private boolean isBudgetedTimeNonNegative = false;
	private boolean isThereEmployeesWhoCantWorkOnMoreActivities = false;
	
	public void setIsMissingTitle(boolean isMissingTitle) {
		this.isMissingTitle = isMissingTitle;
		if (isMissingTitle) isSomethingAmiss = true;
	}
	public void setIsMissingDetailText(boolean isMissingDetailText) {
		this.isMissingDetailText = isMissingDetailText;
		if (isMissingDetailText) isSomethingAmiss = true;
	}
	public void setIsMissingEmployees(boolean isMissingEmployees) {
		this.isMissingEmployees = isMissingEmployees;
		if (isMissingEmployees) isSomethingAmiss = true;
	}	
	public boolean isSomethingAmiss() {
		return isSomethingAmiss;
	}
	
	public void setIsMissingStartDay(boolean isMissingStartDay) {
		this.isMissingStartDay = isMissingStartDay;
		if (isMissingStartDay) isSomethingAmiss = true;
	}
	
	public void setIsMissingEndDay(boolean isMissingEndDay) {
		this.isMissingEndDay = isMissingEndDay;
		if (isMissingEndDay) isSomethingAmiss = true;
	}
	
	public void setIsNotCorrectOrderTime(boolean isNotCorrectOrderTime) {
		this.isNotCorrectOrderTime = isNotCorrectOrderTime;
		if (isNotCorrectOrderTime) isSomethingAmiss = true;
	}
	public void setIsBudgetedTimeNonNegative(boolean isBudgetedTimeNonNegative) {
		this.isBudgetedTimeNonNegative = isBudgetedTimeNonNegative;
		if (isBudgetedTimeNonNegative) isSomethingAmiss = true;
	}
	
	public void setIsThereEmployeesWhoCantWorkOnMoreActivities(boolean isThereEmployeesWhoCantWorkOnMoreActivities){
		this.isThereEmployeesWhoCantWorkOnMoreActivities = isThereEmployeesWhoCantWorkOnMoreActivities;
		if (isThereEmployeesWhoCantWorkOnMoreActivities) isSomethingAmiss = true;
	}

}
