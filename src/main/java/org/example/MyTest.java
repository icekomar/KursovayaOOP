package org.example;
import Exceptions.CheckJtextfield;
import org.junit.*;


import javax.swing.*;

public class MyTest {
    @Test(expected = CheckJtextfield.class)
    public void testEmptyJtextFieldString() throws CheckJtextfield{
            new CheckJtextfield().CheckFieldString(new JLabel(""),new JTextField(""));
    }
    @Test
    public void testNotEmptyJtextFieldString() throws CheckJtextfield {
        new CheckJtextfield().CheckFieldString(new JLabel(""), new JTextField("Hi"));
    }

    @Test(expected = CheckJtextfield.class)
    public void testEmptyJtextFieldInt() throws CheckJtextfield{
        new CheckJtextfield().CheckFieldInt(new JLabel(""),new JTextField(""));
    }
    @Test(expected = NumberFormatException.class)
    public void testNotEmptyWithStringJtextFieldInt() throws CheckJtextfield {
        new CheckJtextfield().CheckFieldInt(new JLabel(""), new JTextField("Not Number"));
    }
    @Test
    public void testNotEmptyStringJtextFieldInt() throws CheckJtextfield {
        new CheckJtextfield().CheckFieldInt(new JLabel(""), new JTextField("1"));
    }
    @BeforeClass // Фиксируем начало тестирования
    public static void allTestsStarted() {
        System.out.println("Начало тестирования");
    }
    @AfterClass // Фиксируем конец тестирования
    public static void allTestsFinished() {
        System.out.println("Конец тестирования");
    }
    @Before // Фиксируем запуск теста
    public void testStarted() {
        System.out.println("Запуск теста");
    }
    @After // Фиксируем завершение теста
    public void testFinished() {
        System.out.println("Завершение теста");
    }

}
