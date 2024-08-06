package com.watermelon.server;

public class Constants {

    public static final String TEST_UID = "uid";
    public static final String TEST_NAME = "name";
    public static final String TEST_ADDRESS = "address";
    public static final String TEST_PHONE_NUMBER = "phoneNumber";
    public static final String TEST_TOKEN = "token";
    public static final int TEST_RANK = 1;
    public static final long TEST_PARTS_ID = 1L;
    public static final String TEST_IMGSRC = "imgsrc";
    public static final int TEST_PAGE_NUMBER = 1;
    public static final int TEST_PAGE_SIZE = 2;
    public static final String TEST_URI = "link";
    public static final String TEST_SHORTED_URI = "shortedUri";

    public static final String HEADER_NAME_AUTHORIZATION = "Authorization";
    public static final String HEADER_NAME_LOCATION = "Location";
    public static final String HEADER_VALUE_BEARER = "Bearer";
    public static final String HEADER_VALUE_SPACE = " ";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_SIZE = "size";

    public static final String PATH_ADMIN_APPLIER = "/admin/event/applier";
    public static final String DOCUMENT_NAME_ADMIN_APPLIER = "admin/event/parts";
    public static final String PATH_ADMIN_LOTTERY_WINNERS = "/admin/lotteries";
    public static final String DOCUMENT_NAME_LOTTERY_WINNERS = "admin/lotteries";
    public static final String PATH_ADMIN_PARTS_WINNERS = "/admin/event/parts";
    public static final String DOCUMENT_NAME_PARTS_WINNERS = "admin/event/parts";
    public static final String PATH_ADMIN_LOTTERY_WINNER_CHECK = "/admin/event/lotteries/{uid}/done";
    public static final String DOCUMENT_NAME_ADMIN_LOTTERY_WINNER_CHECK = "admin/event/lotteries/done";

    public static final String PATH_ADMIN_PARTS_WINNER_CHECK = "/admin/event/parts/{uid}/done";
    public static final String DOCUMENT_NAME_ADMIN_PARTS_WINNER_CHECK = "admin/event/parts/done";

    public static final String PATH_LOTTERY = "/admin/event/lotteries";
    public static final String DOCUMENT_NAME_LOTTERY = "admin/event/lotteries";

    public static final String PATH_PARTS_LOTTERY = "/admin/event/parts";
    public static final String DOCUMENT_NAME_PARTS_LOTTERY = "admin/event/parts";

}


