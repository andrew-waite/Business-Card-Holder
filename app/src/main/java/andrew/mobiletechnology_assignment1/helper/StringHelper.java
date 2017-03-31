package andrew.mobiletechnology_assignment1.helper;

import android.util.Log;

import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;

import java.util.regex.Pattern;

/**
 * Created by Andrew on 7/03/2017.
 */

public class StringHelper
{
    public boolean isNullOrEmpty(String string)
    {
        if(string == null || string.length() == 0)
            return true;

        return false;
    }

    public static String regexMatch(Region region, String pattern)
    {

        for (Line line: region.lines)
        {
            String temp = "";

            for(Word word : line.words)
            {
                //if(word.text.matches(pattern))
                    temp += word.text;
            }

            Log.println(Log.DEBUG, "StringHelper", "words: " + temp);

            if(temp.matches(pattern))
                return temp;
            else
                return "Not Found!";
        }

        return "Not Found!";
    }
}
