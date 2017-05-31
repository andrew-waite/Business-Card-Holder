package andrew.mobiletechnology_assignment1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andrew on 22/02/2017.
 */

public class BusinessCard implements Parcelable
{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String address;
    private String companyName;
    private String website;

    public BusinessCard(int id, String firstName, String lastName, String email, String mobileNumber, String address, String companyName, String website) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.companyName = companyName;
        this.website = website;
    }

    public int getId()
    {
        return this.id;
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

    public String getAddress()
    {
        return this.address;
    }

    public String getCompanyName()
    {
        return this.companyName;
    }

    public String getWebsite()
    {
        return this.website;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public void setFirstName(String name)
    {
        this.firstName = name;
    }

    public void setLastName(String name)
    {
        this.lastName = name;
    }

    public void setFirstLastName(String fullName)
    {
        String[] parts = fullName.split(" ");

        if(parts.length == 1)
        {
            this.firstName = parts[0];
            this.lastName = "Not Found!";
        }
        else if(parts.length == 2)
        {
            this.firstName = parts[0];
            this.lastName = parts[1];
        }
        else
        {
            this.firstName = "Not Found!";
            this.lastName = "Not Found!";
        }
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setMobileNumber(String number)
    {
        this.mobileNumber = number;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCompanyName(String name)
    {
        this.companyName = name;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }


    //Formatting the data for parcelling
    public BusinessCard(Parcel in)
    {
        String[] data = new String[8];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = Integer.valueOf(data[0]);
        this.firstName = data[1];
        this.lastName = data[2];
        this.email = data[3];
        this.mobileNumber = data[4];
        this.address = data[5];
        this.companyName = data[6];
        this.website = data[7];
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[] { Integer.toString(this.id),
                                             this.firstName,
                                             this.lastName,
                                             this.email,
                                             this.mobileNumber,
                                             this.address,
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
