package andrew.mobiletechnology_assingment1;

import android.graphics.Picture;

/**
 * Created by Andrew on 22/02/2017.
 */

public class BusinessCard
{
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String workNumber;
    private String companyName;
    private int id;

    private Picture cardPhoto;

    public BusinessCard()
    {
        this.id = 0;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.mobileNumber = null;
        this.workNumber = null;
        this.companyName = null;
    }

    public BusinessCard(int id, String firstName, String lastName, String email, String mobileNumber, String workNumber, String companyName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.workNumber = workNumber;
        this.companyName = companyName;
    }

    public int getID()
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

    public String getWorkNumber()
    {
        return this.workNumber;
    }

    public String companyName()
    {
        return this.companyName;
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

    public void setID(int id)
    {
        this.id = id;
    }



}
