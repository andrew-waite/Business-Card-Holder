package andrew.mobiletechnology_assignment1.helper;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;

import java.io.InputStream;

/**
 * Created by Andrew on 9/03/2017.
 */

public class ImageHelper
{
    private static final int IMAGE_MAX_SIDE_LENGTH = 1280;

    public static Bitmap loadSizeLimitedBitmapFromUri(Uri imageUri, ContentResolver contentResolver)
    {
        try
        {
            // Load the image into InputStream.
            InputStream imageInputStream = contentResolver.openInputStream(imageUri);

            // For saving memory, only decode the image meta and get the side length.
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Rect outPadding = new Rect();
            BitmapFactory.decodeStream(imageInputStream, outPadding, options);

            // Calculate shrink rate when loading the image into memory.
            int maxSideLength = options.outWidth > options.outHeight ? options.outWidth : options.outHeight;
            options.inSampleSize = 1;
            options.inSampleSize = calculateSampleSize(maxSideLength, IMAGE_MAX_SIDE_LENGTH);
            options.inJustDecodeBounds = false;
            imageInputStream.close();

            // Load the bitmap and resize it to the expected size length
            imageInputStream = contentResolver.openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageInputStream, outPadding, options);
            maxSideLength = bitmap.getWidth() > bitmap.getHeight() ? bitmap.getWidth(): bitmap.getHeight();
            double ratio = IMAGE_MAX_SIDE_LENGTH / (double) maxSideLength;
            if (ratio < 1)
            {
                bitmap = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth() * ratio), (int)(bitmap.getHeight() * ratio), false);
            }

            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static int calculateSampleSize(int maxSideLength, int expectedMaxImageSideLength)
    {
        int inSampleSize = 1;

        while (maxSideLength > 2 * expectedMaxImageSideLength)
        {
            maxSideLength /= 2;
            inSampleSize *= 2;
        }

        return inSampleSize;
    }
}
