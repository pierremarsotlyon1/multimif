package utilitaires;

import java.util.List;
/**
 * Created by Naeno on 21/10/2016.
 */
public final class UtilitairesString {
    private UtilitairesString(){
    }

    public static int stringArrayLength(String[] stringArray){
        int cpt = 0;
        for (String s :
                stringArray) {
            cpt += s.length();
        }
        cpt += stringArray.length - 1;
        return cpt;
    }

    public static String flatStringArray(String[] stringArray,String sep){
        StringBuilder builder1 = new StringBuilder();

        for (int i = 0;i<stringArray.length;i++){
            builder1.append(stringArray[i]);
            if(i != stringArray.length-1) {
                builder1.append(sep);
            }
        }
        return builder1.toString();
    }

    public static String flatStringArray(List<String> stringArray,String sep){
        StringBuilder builder1 = new StringBuilder();

        for (int i = 0;i<stringArray.size();i++){
            builder1.append(stringArray.get(i));
            if(i != stringArray.size()-1) {
                builder1.append(sep);
            }
        }
        return builder1.toString();
    }

    public static String subStringReplace(String base, String[] stringArray,String[] removed, int fromL, int fromCh, int toL, int toCh){
        StringBuilder builder = new StringBuilder(base);
        int charpos = 0;
        String[] lignes = base.split("\n",-1);
        for(int i = 0;i<fromL;i++){
            charpos+=lignes[i].length() + 1;
        }
        charpos+=fromCh;
        System.out.println(charpos);
        builder.delete(charpos,charpos+stringArrayLength(removed));
        builder.insert(charpos,flatStringArray(stringArray,"\n"));
        return builder.toString();
    }
    public static String format(String s){
        return s.replaceAll("[^A-Za-z0-9 ]","_").toLowerCase();
    }
}
