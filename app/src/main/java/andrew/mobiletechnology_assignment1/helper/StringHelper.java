package andrew.mobiletechnology_assignment1.helper;

import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;

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
        String temp = "Not Found!";

        for (Line line: region.lines)
        {
            for(Word word : line.words)
            {
                if(word.text.matches(pattern))
                    temp += word.text;
            }
        }

        return temp;
    }
}
