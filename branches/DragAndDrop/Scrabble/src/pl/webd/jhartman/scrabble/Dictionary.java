package pl.webd.jhartman.scrabble;

//import com.eaio.stringsearch.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class Dictionary {
	private TreeSet<String> ts = new TreeSet<String>();
	
	public Dictionary()
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dictionary/slowa-utf.txt"),"UTF8"));
			String line = new String();
			
			while((line = br.readLine()) != null)
			{
				ts.add(line.toUpperCase());
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nie mogę otworzyć pliku słownika", "Błąd", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean checkWord(String w)
	{
		return ts.contains(w);
	}
}
