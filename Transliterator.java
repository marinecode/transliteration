package forbear;


import java.util.HashMap;
import java.util.Map;

public class Transliterator {
	private Map<Character, String> transliterationMap = new HashMap<Character, String>();
	private String[] latinDown = {"a","b","v","g","d","e","yo","zh","z","i","j","k","l","m","n","o","p","r","s","t","u","f","x","c","ch","sh","shh","``","ó`","`","å`","yu","óà"};
	private String[] latinAll = new String[latinDown.length*2];
	private Character[] kirill = new Character[66];
	
	//ñîçäàåì íàáîðû êèðèëëè÷åñêèõ è ëàòèíñêèõ ñèìâîëîâ (çàãëàâíûõ è ñòðî÷íûõ)
	{
		int letter = 1040;// кодировка Cp1251
		for( int i=0; i < kirill.length; i++ ) {
			if( i == 39 ) { //ñòàâèì ¸ íà ïðàâèëüíîå ìåñòî â àëôàâèòíîì ïîðÿäêå
				kirill[i] = 'ё';
				continue;
			}
			if( i == 6 ) { //ñòàâèì ¨ íà ïðàâèëüíîå ìåñòî â àëôàâèòíîì ïîðÿäêå
				kirill[i] = 'Ё';
				continue;
			}
			kirill[i] = (char)(letter++);
		}
		//ñîçäàåì çàãëàâíûå ëàòèíñêèå áóêâû è äîáàâëÿåì èõ â íàáîð
		for( int i = 0 ; i< latinDown.length; i++ ) {
			String upper;
			if( latinDown[i].length() == 1 ) {
				upper = latinDown[i].toUpperCase();
			}else {
				upper = latinDown[i].substring( 0, 1 ).toUpperCase() + latinDown[i].substring(1);
			}
			
			latinAll[i] = upper;
		}
		//äîáàâëÿåì â ëàòèíñêèé íàáîð ñòðî÷íûå áóêâû
		int j = 0;
		for( int i = latinDown.length ; i < latinAll.length; i++ ) {
			latinAll[i] = latinDown[j];
			j++;
		}
	}
	
	public Transliterator(){
		for( int i =0; i < kirill.length; i++ ) {
			transliterationMap.put( kirill[i], latinAll[i] );
		}
	}
	
	private String wordTrans( String inputword ) {
		char[] letters = inputword.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for( char letter : letters ) {
			
			String letterTrans = transliterationMap.get( letter );
			if( letterTrans == null ) {
				letterTrans = String.valueOf( letter );
			}
			sb.append( letterTrans );
		}
		return sb.toString();
	}
	
	
	
	public String transliterate( String inputString ) {
		String[] words = inputString.split(" ");
		StringBuilder result = new StringBuilder();
		for( String word : words ) {
			result.append(" ");
			result.append( wordTrans( word ));
		}
		result.deleteCharAt(0); // óäàëÿåì ïðîáåë ïåðåä ïðåäëîæåíèåì
		return result.toString();
	}
	
//	public static void main(String[] arg ) {
//		Transliterator t = new Transliterator();
//		System.out.println( Arrays.toString(t.slavian));
//		System.out.println( Arrays.toString(t.latinAll));
//		System.out.println(t.transliterationMap.get('ú'));
//		System.out.println((char)1040);
//		String sentens = "Ïðèâåò, ìèð< æóê æèìîëîñòü!";
//		System.out.println( t.transliterate( sentens ));
//	}
}
