package com.harlertechnologies.mkopopopoteapp;

/**
 * Created by wamari on 7/26/17.
 */

public class Config {

    //address if our CRUD scripts
    public static final String URL_ADD="http://harlertechnologies.com/mkopo/addUser.php";
    //// TODO: 7/26/17 Add other scripts

    //keys that will be used to send the request to the php scripts
    public static final String KEY_IDNO = "idno";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DOB = "dob";
    public static final String KEY_GENDER = "gender";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_IDNO = "idno";
    public static final String TAG_LASTNAME = "lastname";
    public static final String TAG_FIRSTNAME = "firstname";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_DOB = "dob";
    public static final String TAG_GENDER = "gender";

    //user id to pass with intent
    public static final String IDNO = "idno";
}
