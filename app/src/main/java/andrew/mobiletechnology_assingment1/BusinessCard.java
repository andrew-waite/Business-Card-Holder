package andrew.mobiletechnology_assingment1;

import android.graphics.Picture;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Andrew on 22/02/2017.
 */

public class BusinessCard implements Parcelable
{
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String workNumber;
    private String companyName;
    private String website;

   // private Picture cardPhoto;

    /*public BusinessCard()
    {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.mobileNumber = null;
        this.workNumber = null;
        this.companyName = null;
        this.website = null;
    }*/

    public BusinessCard(String firstName, String lastName, String email, String mobileNumber, String workNumber, String companyName, String website) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.workNumber = workNumber;
        this.companyName = companyName;
        this.website = website;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getMobileNumber()
    {
        return this.mobileNumber;
    }

    public String getWorkNumber()
    {
        return this.workNumber;
    }

    public String getcompanyName()
    {
        return this.companyName;
    }

    public String getWebsite()
    {
        return this.website;
    }

    public void setFirstName(String name)
    {
        this.firstName = name;
    }

    public void setLastName(String name)
    {
        this.lastName = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setMobileNumber(String number)
    {
        this.mobileNumber = number;
    }

    public void setWorkNumber(String number)
    {
        this.workNumber = number;
    }

    public void setCompanyName(String name)
    {
        this.companyName = name;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }


    // Parcelling part
    public BusinessCard(Parcel in)
    {
        String[] data = new String[7];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.firstName = data[0];
        this.lastName = data[1];
        this.email = data[2];
        this.mobileNumber = data[3];
        this.workNumber = data[4];
        this.companyName = data[5];
        this.website = data[6];
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[] { this.firstName,
                                             this.lastName,
                                             this.email,
                                             this.mobileNumber,
                                             this.workNumber,
                                             this.companyName,
                                             this.website});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public BusinessCard createFromParcel(Parcel in)
        {
            return new BusinessCard(in);
        }

        public BusinessCard[] newArray(int size)
        {
            return new BusinessCard[0];
        }
    };
}
