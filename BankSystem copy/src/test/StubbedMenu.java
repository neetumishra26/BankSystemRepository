package test;

import main.Menu;

public class StubbedMenu extends Menu {
    public StubbedMenu(){}

    @Override
    public String[] performSelection(){
        String[] toReturn = {"456","123","10","INR"};
        return toReturn;
    }

}
