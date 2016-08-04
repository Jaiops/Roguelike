package game;

import java.util.Random;

/**
 * Created by Johan on 2016-07-28.
 */
public class RandomNaming{
    char[] softVocals = {'e','i','y'};
    char[] hardVocals = {'a','u','o'};
    char[] vocals = {'a','u','o','e','i','y'};
    char[] consonant = {'b','c','d','f','g','h','j','k','l','m','n','p'
            ,'q','r','s','t','v','w','x','z'};
    // char[] softVocals = {'a','b'};
    public  String getRandomName(){
        String s = "";
        Random r = new Random();
        int nameLen = r.nextInt(3)+4;
        boolean voc = r.nextBoolean();
        for(int i = 0; i<nameLen;i++){
            if(voc){

                s += vocals[r.nextInt(vocals.length)];
                voc = false;

            }
            else{
                boolean doubleCon = r.nextBoolean();
                if(i>0 && doubleCon){
                    char c = consonant[r.nextInt(consonant.length)];
                    s += c;
                    s += c;
                }
                else {
                    s += consonant[r.nextInt(consonant.length)];
                }
                voc = true;
            }
        }
        s = s.substring(0, 1).toUpperCase() + s.substring(1);
        return s;
    }
}