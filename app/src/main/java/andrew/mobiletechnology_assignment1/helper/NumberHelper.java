package andrew.mobiletechnology_assignment1.helper;

/**
 * Created by Andrew on 7/03/2017.
 */

public class NumberHelper
{
    public static boolean isInteger(String string)
    {
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException exception)
        {
            exception.printStackTrace();
        }

        return false;
    }

}
