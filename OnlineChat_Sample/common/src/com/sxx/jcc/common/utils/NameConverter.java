package com.sxx.jcc.common.utils;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Convert names to specified style <br>
 * 2007-5-15
 * 
 * @author wangcl
 * 
 */
public class NameConverter {
    private static NameConverter instance = null;

    private NameConverter() {
    }

    public static NameConverter getInstance() {
        if (instance == null) {
            instance = new NameConverter();
        }
        return instance;
    }

    /**
     * convert database names separated by '_' to java field names <br>
     * eg. <code>user_id -> userId</code>
     * 
     * @param dbname
     * @return
     */
    public String convert(String dbname) {
        String name = "";
        StringTokenizer st = new StringTokenizer(dbname, "_");
        if (!st.hasMoreTokens()) {
            throw new RuntimeException("name is null");
        }
        name += st.nextToken().toLowerCase();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.length() == 1) {
                name += token.toUpperCase();
            } else {
                String first = token.substring(0, 1);
                String others = token.substring(1, token.length());
                name += (first.toUpperCase() + others.toLowerCase());
            }

        }
        return name;
    }

    /**
     * convert field names in vo to database column names <br>
     * eg. <code>bankBranchId -> BANK_BRANCH_ID</code>
     * 
     * @param voname
     * @return
     */
    public String convertToDb(String voname) {
        String name = "";
        ArrayList positions = new ArrayList();
        positions.add(new Integer(0));
        char[] letters = voname.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            char letter = letters[i];
            if (letter > 64 && letter < 91) { // an uppercase letter
                positions.add(new Integer(i));
            }
        }
        positions.add(new Integer(letters.length));
        for (int j = 1; j < positions.size(); j++) {
            int from = ((Integer) (positions.get(j - 1))).intValue();
            int to = ((Integer) (positions.get(j))).intValue();
            name += voname.substring(from, to).toUpperCase() + "_";
        }
        return name.substring(0, name.length() - 1);
    }
    public static void main(String []a){
    	String str = "CreateDate";
    	System.out.println(NameConverter.class.getSimpleName());
    }
    /**
     * change the first letter of a string to upper case <br>
     * eg. <code>userId -> UserId</code>
     * 
     * @param name
     * @param caseOfFirstLetter
     * @return
     */
    public String setUpperCaseForFirstLetter(String name) {
        if (name.length() == 1) {
            return name.toUpperCase();
        }
        String firstLetter = name.substring(0, 1);
        String others = name.substring(1, name.length());
        return firstLetter.toUpperCase() + others;
    }
    public String setLowerCaseForFirstLetter(String name) {
        if (name.length() == 1) {
            return name.toLowerCase();
        }
        String firstLetter = name.substring(0, 1);
        String others = name.substring(1, name.length());
        return firstLetter.toLowerCase() + others;
    }
}
