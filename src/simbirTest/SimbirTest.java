package simbirTest;

import java.util.Scanner;

import classes.Page;

public class SimbirTest {
	
	public static void main(String[] args) {
		// String test = "Это проверка проверочных проверок, главная проверка идет после проверок запасных. Проверка-проверка-проверка"; // testing
		// String testUrl = "google.com"; // testing
		// String line = null; // в этой переменной хранился текст html страницы
		Page page = new Page();
		Scanner in = new Scanner(System.in); // scanner используется для получения адреса в консоли от пользователя
		System.out.println("Приложение для получения статистики по количеству уникальных слов");
		System.out.println("Введите адрес сайта: ");
        String testUrl = in.nextLine();
		
		// page.fillWordList(test); // testing
		/* line = page.getContentFromPage(testUrl);
		line = page.getTextFromPage(line);
		page.fillWordList(line); */
        page.getWordStats(testUrl);
		
		in.close();
	}
}
