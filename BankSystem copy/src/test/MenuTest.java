package test;

import main.Menu;
import main.UserInterface.ConsoleInterfaceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.verification.VerificationMode;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class MenuTest {

    Menu menu;
    ConsoleInterfaceImpl consoleInterfaceMock;

    @Before
    public void setUp(){
        consoleInterfaceMock = mock(ConsoleInterfaceImpl.class);
        menu = new Menu(consoleInterfaceMock);
    }

//    @Test
//    public void testMenuOptionPerformed(){
//        when(consoleInterfaceMock.getOptionNumber()).thenReturn(1);
//        when(consoleInterfaceMock.getUserInput()).thenReturn("123","256","23","CAD");
//
//        String [] userInput = menu.performSelection();
//
//        verify(consoleInterfaceMock).getOptionNumber();
//        verify(consoleInterfaceMock).getUserInput();
//        verify(consoleInterfaceMock).getUserInput();
//        verify(consoleInterfaceMock).getUserInput();
//        verify(consoleInterfaceMock).getUserInput();
//
//        assertEquals(userInput.length, 4);
//    }
}
