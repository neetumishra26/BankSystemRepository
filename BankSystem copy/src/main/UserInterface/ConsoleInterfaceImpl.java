package main.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterfaceImpl implements UserInterface{
    public void print(String value) {
        System.out.print(value);
    }

    public String getUserInput() {

        return readLine();
    }

    public int getOptionNumber() {

        return Integer.decode(readLine());
    }

    private String readLine()
    {
        String inputLine = null;
        try
		{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		inputLine=br.readLine();
		}
		catch (IOException e)
		{
			System.out.println("IOException: " +e);
		}
        return inputLine;
    }
}
