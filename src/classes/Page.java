package classes;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

// import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Page {
	private ArrayList<Word> wordList = new ArrayList<>(); // список слов на странице
	private String pageURL; // адрес страницы
	private String pageText; // текст страницы

	/* Стандартный конструктор */
	public Page() {

	}
	
	/* Конструктор страницы по URL */
	public Page(String URL){
		this.pageText=getContentFromPage(URL);
	    this.pageURL=URL;
	    fillWordList(this.pageText);
	}
	
	/* Получить статистику слов (выполняет функции описанные ниже) */
	public void getWordStats(String URL) {
		String line = getContentFromPage(URL); // получает текст с адреса
		line = getTextFromPage(line); // выделяет слова из текста
		fillWordList(line); // записать текст в файл и прочитать в консоль
	}
	/* Получить полный текст по URL адресу (вместе с тегами и прочей лабудой) */
	public String getContentFromPage(String URL){
		String line="", all = ""; // line - очередная строчка, all - полный текст сайта
		String prefix="https://"; // prefix - добавляется если адрес не начинается с https://
		URL pageURL = null; // адрес сайта типа URL
		BufferedReader in = null; // для чтения страницы
		if(URL.startsWith("https://")==false) // если адрес не начинается с https://
			URL = prefix + URL; // добавить префикс
		// System.out.println("URL = " + URL); // testing
	    try {
	        pageURL = new URL(URL); // создается адрес типа URL
	        in = new BufferedReader(new InputStreamReader(pageURL.openStream())); // чтение страницы

	        while ((line = in.readLine()) != null) { // цикл - пока очередной символ не является последним
	            all += line; // символ добавляется к полному тексту
	        }

		} catch (IOException e) {
			if(e.toString().startsWith("java.net.UnknownHostException"))
				System.out.println("Неправильный адрес или отсутствует подключение к интернету");
			else
				e.printStackTrace();
		} finally { // после выполнения выше описанного закрытие чтения
	        if (in != null) { // если чтение существует
	            try {
					in.close(); // закрыть чтение
				} catch (IOException e) { // проверка на наличие ошибок
					e.printStackTrace();
				}
	        }
	    }
		return all; // возвращает весь текст страницы
	}
	
	/* Отделить от всего текста страницы только полноценные слова (без тегов) */
	public String getTextFromPage(String line) {
		Document doc = Jsoup.parse(line); // Jsoup используется для отделения тегов от общего текста
		String text = doc.body().text(); // берет текст документа
		return text; // возвращает текст без тегов
	}
	
	/* заполнить список слов[wordList] (разделить строку[str] на слова[word] с помощью разделителей[delims]) */
	public void fillWordList(String str) {
		String delims = "[\\-\\,\\.\\!\\?\\;\\:\\]\\[\\)\\(\\n\\r\\t\\s\\|]+"; // разделители
		String[] subStr; // в этом массиве будут храниться текст разделенных слов
		String fileName = "words.txt"; // название файла куда список сохраняется
		String filePath = System.getProperty("user.dir");
		subStr = str.split(delims); // разделить строку на слова
		
		for(int i=0; i<subStr.length; i++) { // цикл повторяется пока не закончатся слова в массиве subStr[]
			Word word = getWordFromList(subStr[i]); // найти объект класса Word по слову в списке wordList
			if(wordList.indexOf(word)!=-1) { // если объект Word уже есть в списке
				addAmountOfWordToList(subStr[i]); // добавить к его количеству 1
			}
			else { // иначе - если объекта нет в списке
				addWordToList(subStr[i]); // добавить слово в список
			}
		}
		
		// вывод слов в консоль и запись слов в файл
		try(FileWriter writer = new FileWriter(fileName, false)) { // запись в файл words.txt, если файл существовал - перезаписать
			System.out.println("-------Начало-------Списка-------");
			for(Word currWord : wordList) { // пока слово есть в списке
				System.out.println(currWord.getText() + " = " + currWord.getAmount()); // вывод в консоль
				writer.write(currWord.getText() + " = " + currWord.getAmount() + "\n"); // запись в файл
			}
			System.out.println("-------Конец-------Списка-------\n");
			System.out.println("Список записан в файл по пути " + filePath + "\\" + fileName );
		}
		catch(IOException ex){ // проверка ошибок
            System.out.println(ex.getMessage());
        } 
	}
	
	/* Добавить новое слово в список */
	public void addWordToList(String wordText) {
		Word word = new Word(wordText); // создать новый объект класса Word

		wordList.add(word); // добавить объект в список
	}
	
	/* Если слово уже есть в списке - добавить к его количеству 1 */
	public void addAmountOfWordToList(String wordText) {
		Word word = getWordFromList(wordText); // найти объект класса Word по слову в списке wordList, чтоб ему изменить количество 
		int amount = word.getAmount(); // получить количество повторений слова
		
		word.setAmount(amount+1); // прибавить к количеству 1
	}
	
	/* Получить объект класса Word(слово) по тексту слова */
	public Word getWordFromList(String wordText) {
		Word word = new Word(); // создать пустой объект класса Word
		
		for(int i=0; i<wordList.size(); i++) { // пока слово не найдено в списке или список не пройден
			if(wordList.get(i).getText().equals(wordText)) { // если в списке есть такое слово
				word=wordList.get(i); // получить объект
				break;
			}
			// System.out.println("Номер цикла i = " + i); // тестирование
		}
		
		return word; // вернуть объект
	}
	
	/* Стандартные геттеры и сеттеры */
	public ArrayList<Word> getWordList() {
		return wordList;
	}
	public void setWordList(ArrayList<Word> wordList) {
		this.wordList = wordList;
	}
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	public String getPageText() {
		return pageText;
	}
	public void setPageText(String pageText) {
		this.pageText = pageText;
	}
}
