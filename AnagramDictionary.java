package com.example.lenovo.anagram;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    int wordLength=DEFAULT_WORD_LENGTH;

   // public static ArrayList<String> wordList =new ArrayList<String>();
    Set<String> wordset=new HashSet<>();
    HashMap<String,ArrayList<String>> lettersToWord=new HashMap<>();
    HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            //wordList.add(word);
            wordset.add(word);
            //For Size
            if(sizeToWords.containsKey(word.length())==false)
            {
                ArrayList<String> arrays=new ArrayList<>();
                arrays.add(word);
                sizeToWords.put(word.length(),arrays );
            }
            else
            {
                sizeToWords.get(word.length()).add(word);
            }

            //For Anagrams
            if(lettersToWord.containsKey(sortletters(word))==false)
            {
                ArrayList<String> array=new ArrayList<>();
                array.add(word);
                lettersToWord.put(sortletters(word),array );
            }
            else
            {
                lettersToWord.get(sortletters(word)).add(word);
            }

               //lettersToWord.put(sortletters(word), temp);
        }

    }

    public boolean isGoodWord(String word, String base) {


        if(wordset.contains(word) && word.contains(base)==false)
            return true;
        else
            return false;


    }
   ArrayList<String> result = new ArrayList<String>();


    public String sortletters(String targetWord) {
       // ArrayList<String> result = new ArrayList<String>();
        char[] chars = targetWord.toCharArray();
        Arrays.sort(chars);
        String newWord = new String(chars);

        return newWord;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String alphabets="abcdefghijklmnopqrstuvwxyz";
        char alphChar[]=alphabets.toCharArray();
        for(char ch:alphChar)
        {
            String extraWord=sortletters(word+ch);
            if(lettersToWord.containsKey(extraWord)) {
                for(String val:lettersToWord.get(extraWord))
                {
                    if(val.contains(word)==false)
                        result.add(val);
                }
                //result.addAll(lettersToWord.get(extraWord));
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        int n=0;
        String pick="";
        ArrayList<String> w=sizeToWords.get(wordLength);
        while(n<MIN_NUM_ANAGRAMS) {
            int rand = (int) (Math.random() * (w.size() - 1));
            pick = w.get(rand);
            n=lettersToWord.get(sortletters(pick)).size();
        }
        wordLength++;
        if(wordLength>MAX_WORD_LENGTH)
            wordLength=DEFAULT_WORD_LENGTH;
       return pick;
    }
}
