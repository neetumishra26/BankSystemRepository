package main;

import main.UserInterface.ConsoleInterfaceImpl;

import java.util.ArrayList;

public class Menu {

    ArrayList <String> optionList;
    ArrayList <String> optionListIfTransferFundsselected;
    String [] userInput = new String[4];
    ConsoleInterfaceImpl consoleInterface;

    public Menu(ConsoleInterfaceImpl consoleInterface) {
        this.consoleInterface = consoleInterface;
        optionList = new ArrayList<String>();
        optionListIfTransferFundsselected = new ArrayList<String>();

        optionList.add("1-Transfer Funds");
        optionList.add("0-Exit");

        optionListIfTransferFundsselected.add("From Account Number");
        optionListIfTransferFundsselected.add("To Account Number");
        optionListIfTransferFundsselected.add("Amount Value");
        optionListIfTransferFundsselected.add("Amount Currency");
    }

    public Menu() {
    }

    private void showMenuForAllUser()
    {
        for(String menu : optionList)
            System.out.println(menu);
    }

    private String[] showMenuIfTransferFundsSelected()
    {
        int i = 0;
        for(String menu : optionListIfTransferFundsselected){
            System.out.println(menu);
            userInput[i++] = consoleInterface.getUserInput();
        }
        return userInput;
    }

    public String[] performSelection() {
        showMenuForAllUser();
        int choice = consoleInterface.getOptionNumber();
        if(choice == 1)
        {
            return showMenuIfTransferFundsSelected();
        }
        else {
            return null;
        }
    }
}
