package andrew.mobiletechnology_assignment1.helper;

import android.util.Log;

import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;

import java.util.ArrayList;
import java.util.List;

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

    public static String regexMatch(List<Region> regions, String pattern)
    {
        List<String> linesFound = new ArrayList<>();

        String defaultReturn = "Not Found!";

        for(Region region : regions)
        {
            for (Line line : region.lines)
            {
                String temp = "";

                for (Word word : line.words)
                {
                    temp += word.text;
                }

                linesFound.add(temp);
            }
        }


        for(int i = 0; i < linesFound.size(); i++)
        {
            if(linesFound.get(i).matches("(?i)^(w:|website:|e:|email:|p:|phone:|m:|mobile:|mail:|web:|address:|ph:).*$"))
            {
                linesFound.set(i, linesFound.get(i).replaceAll("(?i)w:|website:|e:|email:|p:|phone:|m:|mobile:|mail:|web:|address:|-|ph:", ""));
            }
            else if(linesFound.get(i).matches("([A-Z][a-z]*[A-Z][a-z]*)"))
            {
                String temp = linesFound.get(i);
                temp = temp.replaceAll("(?!^)([A-Z])", " $1");
                linesFound.set(i, temp);
            }
            else
                continue;
        }

        for(String string : linesFound)
        {
            if (string.matches(pattern))
            {
                return string;
            }
            else
                continue;

        }

        return defaultReturn;
    }
}
