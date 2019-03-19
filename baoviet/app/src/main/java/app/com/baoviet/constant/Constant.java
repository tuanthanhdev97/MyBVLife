package app.com.baoviet.constant;

import app.com.baoviet.entity.MenuDTO;

public class Constant {
    // Navigate from login screen
    public static final String TRANSFER_LOGIN_TO_UNDEFINE = "TRANSFER_LOGIN_TO_UNDEFINE";
    public static final String TRANSFER_LOGIN_TO_SECURITY = "TRANSFER_LOGIN_TO_SECURITY";
    public static final String TRANSFER_LOGIN_TO_TERMS = "TRANSFER_LOGIN_TO_TERMS";
    public static final String TRANSFER_LOGIN_TO_CONTACT = "TRANSFER_LOGIN_TO_CONTACT";
    public static final String TRANSFER_LOGIN_TO_REGISTER = "TRANSFER_LOGIN_TO_REGISTER";
    public static final String TRANSFER_LOGIN_TO_FORGOT_PASSWORD = "TRANSFER_LOGIN_TO_FORGOT_PASSWORD";
    public static final String TRANSFER_LOGIN_TO_UPDATE_PASSWORD = "TRANSFER_LOGIN_TO_UPDATE_PASSWORD";
    public static final String TRANSFER_LOGIN_TO_PAYMENT = "TRANSFER_LOGIN_TO_PAYMENT";
    public static final String TRANSFER_LOGIN_TO_HOME = "TRANSFER_LOGIN_TO_HOME";
    public static final String TRANSFER_TO_PAYMENT_NAPAS = "TRANSFER_PAYMENT_NAPAS";
    public static final String TRANSFER_TO_PAYMENT_BAOVIET_BANK = "TRANSFER_PAYMENT_BAOVIET_BANK";
    public static final String TRANSFER_TO_NOTIFICATION_PAGE = "TRANSFER_NOTIFICATION";
    public static final String TRANSFER_CONTRACT_TO_GENERAL_INFOR = "TRANSFER_CONTRACT_TO_GENERAL_INFOR";
    public static final String TRANSFER_CONTRACT_TO_CUSTOMER_INFOR = "TRANSFER_CONTRACT_TO_CUSTOMER_INFOR";
    public static final String TRANSFER_CONTRACT_TO_VALUE_ACCOUNT = "TRANSFER_CONTRACT_TO_VALUE_ACCOUNT";
    public static final String TRANSFER_CONTRACT_TO_PAYMENT_PROCESS = "TRANSFER_CONTRACT_TO_PAYMENT_PROCESS";
    public static final String TRANSFER_CONTRACT_TO_FEE_AND_COST = "TRANSFER_CONTRACT_TO_FEE_AND_COST";
    public static final String TRANSFER_CONTRACT_TO_BENEFIT = "TRANSFER_CONTRACT_TO_BENEFIT";
    public static final String TRANSFER_CONTRACT_TO_EBILL = "TRANSFER_CONTRACT_TO_EBILL";
    public static final String TRANSFER_CONTRACT_TO_ANNUAL_REPORT = "TRANSFER_CONTRACT_TO_ANNUAL_REPORT";
    public static final String TRANSFER_TO_TRADE_ONLINE = "TRANSFER_TO_TRADE_ONLINE";
    public static final String TRANSFER_TO_PRODUCT = "TRANSFER_TO_PRODUCT";
    public static final String TRANSFER_TO_SERVICE = "TRANSFER_TO_SERVICE";
    public static final String TRANSFER_TO_INVEST = "TRANSFER_TO_INVEST";
    public static final String TRANSFER_TO_SAVE = "TRANSFER_TO_SAVE";
    public static final String TRANSFER_TO_PROTECTED = "TRANSFER_TO_PROTECTED";
    public static final String TRANSFER_TO_RETIRE = "TRANSFER_TO_RETIRE";
    public static final String TRANSFER_TO_BUSINESS = "TRANSFER_TO_BUSINESS";
    public static final String TRANSFER_TO_BANK = "TRANSFER_TO_BANK";
    public static final String TRANSFER_TO_CHAT = "TRANSFER_TO_CHAT";
    public static final String TRANSFER_TO_HOTLINE = "TRANSFER_TO_HOTLINE";
    public static final String TRANSFER_TO_VIEW_PDF = "TRANSFER_TO_VIEW_PDF";

    // Navigate list draw layout
    public static final String NAV_PAYMENT_LV0 = "THANH TOÁN PHÍ BẢO HIỂM";
    public static final String NAV_PAYMENT_LV1 = "Thanh toán phí bảo hiểm";
    public static final String NAV_RETRIEVE_CONTRACT = "TRUY VẤN HỢP ĐỒNG";
    public static final String NAV_CATEGORY_SYSTEM_PARAMETER = "Danh mục tham số hệ thống";
    public static final String NAV_CATEGORY_FUNCTION = "Danh mục chức năng";
    public static final String NAV_INFOR_PAYMENT = "Tra cứu thông tin thu phí";
    public static final String NAV_CREATE_NEW_ACCOUNT = "Tạo tài khoản mới";
    public static final String NAV_PERMISSION_USER = "Phân quyền người dùng";
    public static final String NAV_MANAGEMENT_PERMISSION = "Quản lý nhóm quyền";
    public static final String NAV_CREATE_NEW_ACCOUNT_NO_LOGIN = "TẠO TÀI KHOẢN EPOS";
    public static final String NAV_ONLINE_TRADING = "Giao dịch trực tuyến";
    public static final String NAV_INFOR_ACCOUNT = "Thông tin tài khoản";
    public static final String NAV_UPDATE_ACCOUNT = "Cập nhật tài khoản";
    public static final String NAV_LOG_OUT = "Đăng xuất";

    // Message
    public static final String LOADING_PROCESS = "Đang tải dữ liệu...";

    //Message Error
    public static final String MSG_LOGIN_ERROR = "Có lỗi xảy ra, bạn vui lòng thử lại sau!";

    //symbol
    public static final String SYMBOL_SLASH = "/";
    public static final String SYMBOL_HYPHEN = "-";
    public static final String SYMBOL_COLON = ":";
    public static final String SYMBOL_DOT = ".";
    public static final String SYMBOL_PERCENT = "%";
    public static final String SYMBOL_UNDERSCORE = "_";
    public static final String SYMBOL_QUESTION = "?";

    // Format Date
    public static final String DDMMYY = "dd/MM/yyyy";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String DDMMYYYYTHHmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    // Dimen
    public static final int DOT_MARGIN_LEFT = 10;
    public static final int DOT_MARGIN_TOP = 5;
    public static final int DOT_MARGIN_RIGHT = 10;
    public static final int DOT_MARGIN_BOTTM = 5;

    // Timer
    public static final int TIMER_DELAY_SLIDESHOW = 2000;
    public static final int TIMER_PERIOD_SLIDESHOW = 5000;

    // Number
    public static final int INT_0 = 0;
    public static final int INT_1 = 1;
    public static final int INT_2 = 2;
    public static final int INT_3 = 3;
    public static final int INT_4 = 4;
    public static final int INT_5 = 5;
    public static final int INT_6 = 6;
    public static final int INT_7 = 7;
    public static final int INT_10 = 10;
    public static final int INT_200 = 200;
    public static final int INT_1000 = 1000;

    // String number
    public static final String STR_INT_0 = "0";
    public static final String STR_INT_1 = "1";
    public static final String STR_INT_2 = "2";
    public static final String STR_INT_3 = "3";
    public static final String STR_INT_4 = "4";
    public static final String STR_INT_5 = "5";
    // negative value
    public static final int INT_NEGATIVE_1 = -1;

    // Link chat
    public static final String CHAT_LINK = "https://chat.baovietnhantho.com.vn/chatnhantho.asp";
    public static final String INVEST_LINK = "https://baovietnhantho.com.vn/san-pham/dau-tu-2";
    public static final String SAVE_MONEY_LINK = "https://baovietnhantho.com.vn/san-pham/Tich-luy-3";
    public static final String PROTECTED_LINK = "https://baovietnhantho.com.vn/san-pham/Bao-ve-4";
    public static final String RETIRE_LINK = "https://baovietnhantho.com.vn/san-pham/Huu-tri-23";
    public static final String BUSINESS_LINK = "https://baovietnhantho.com.vn/san-pham/Danh-cho-doanh-nghiep-5";
    public static final String BANK_LINK = "https://baovietnhantho.com.vn/san-pham/Bao-hiem-lien-ket-ngan-hang-6";

    public static final String CHANNEL_PAYMENT_LINK = "https://baovietnhantho.com.vn/dich-vu/Kenh-thanh-toan-2";
    public static final String CHANGE_INFOR_CONTRACT_LINK = "https://baovietnhantho.com.vn/dich-vu/Thay-doi-thong-tin-hop-dong-bao-hiem-3";
    public static final String BENEFIT_LINK = "https://baovietnhantho.com.vn/dich-vu/Giai-quyet-quyen-loi-bao-hiem-4";
    public static final String ACKNOWLEDGE_LINK = "https://baovietnhantho.com.vn/dich-vu/Nhung-dieu-can-biet-cho-khach-hang-5";
    public static final String TAKE_CARE_LINK = "https://baovietnhantho.com.vn/dich-vu/Cham-soc-khach-hang-6";

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final float WIDTH_OF_STROKE = 0.5f;
    //URL
//    public static final String URL_CONNECTION = "https://esb.baoviet.com.vn/eposwsTest";
    public static final String URL_CONNECTION = "https://esb.baoviet.com.vn/eposws";
    public static final String URL_LOGIN = "/api/log/login";
    public static final String URL_GET_TREE_MENU = "/api/menu/getTreeMenu";
    public static final String URL_GET_TREE_MENU_PUBLIC = "/api/menu/getTreeMenuPublic";
    public static final String URL_GET_SWITCH_USER_MODE = "/api/admin/switchUsermode";
    public static final String URL_GET_GENERAL_INFOR = "/api/persproduct/generalInfo";
    public static final String URL_GET_CUSTOMER_INFOR = "/api/persproduct/customerInfo";
    public static final String URL_GET_PREMIUM_TRANS_INFOR = "/api/persproduct/paymentProcess/premiumTransInfo";
    public static final String URL_GET_PREMIUM_TRANS = "/api/persproduct/paymentProcess/premiumTrans";
    public static final String URL_GET_FEES_CHARGES_TRANS = "/api/persproduct/feesAndCosts/feesChargesTrans";
    public static final String URL_GET_BENEFIT_RESOLVE_PAID = "/api/persproduct/benefitResolve/paid";
    public static final String URL_GET_BENEFIT_RESOLVE = "/api/persproduct/benefitResolve/benefitResolving";
    public static final String URL_GET_BILL = "/api/bill/personal/ebill";
    public static final String URL_GET_ANNUAL_REPORT = "/api/report/personal/getAnnualReport";
    public static final String URL_GET_PAV = "/api/persproduct/accountValue/searchPav";
    public static final String URL_GET_LOAN = "/api/persproduct/loanValue/searchLoans";
    public static final String URL_SEARCH_INVOICE = "/api/public/searchInvoice";
    public static final String URL_PAYMENT_NAPAS = "/api/public/payViaNapas";
    public static final String URL_PAYMENT_BAOVIET_BANK = "/api/public/payViaBaovietBank";
    public static final String URL_PREMINUM_RESULT = "/api/public/premiumResult";
    public static final String URL_GET_BACK_RETURN_URL = "/api/public/getBackReturnURL";
    public static final String URL_UPDATE_USER_ALIAS = "/api/user/updateUserAlias";
    public static final String URL_UPDATE_PASSWORD = "/api/user/updatePassword";
    public static final String URL_GET_ACCOUNT_INFOR = "/api/admin/accountInfo";
    public static final String URL_GET_USER_INFOR = "/api/user/userinfo";
    public static final String URL_POST_DOWNLOAD_REPORT_ANNUAL = "/api/report/downloadFile";
    public static final String URL_POST_DOWNLOAD_EBILL = "/api/bill/downloadFile";
    public static final String URL_POST_DOWNLOAD_XML_EBILL = "/api/bill/downloadXml";
    public static final String URL_POST_GROUP_INFOR = "/api/groupProduct/groupInfo";
    public static final String URL_VALIDATE_REGISTER_USER = "/api/public/validateClientRegister";
    public static final String URL_REGISTER_USER = "/api/public/clientRegisterNew";
    public static final String URL_FORGOT_PASSWORD = "/api/user/forgotPassword";
    public static final String URL_FORGOT_PASSWORD_VALID = "/api/user/forgotPasswordValid";
    public static final String URL_INSERT_TOKEN_NOTIFICATION = "/api/notify/insertDeviceToken";
    public static final String URL_GET_PER_GENERAL_INFOR = "/api/common/persGroupPro/generalInfo";
    public static final String URL_GET_NOTIFICATION = "/api/notify/notifySentByUserCode";


    // METHOD
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    //TIMEOUT
    public static final int TIMEOUT_READ = 15000;
    public static final int TIMEOUT_CONNECT = 15000;

    //RESPONSE CODE
    public static final int RESPONSE_CODE_200 = 200;
    public static final int RESPONSE_CODE_220 = 220;
    public static final int RESPONSE_CODE_412 = 412;
    public static final int RESPONSE_CODE_426 = 426;

    //token
    public static String TOKEN = "";
    public static String USER_CODE = "";
    public static String PASSWORD_LOCAL = "";

    //USERCODE search
    public static String USER_CODE_FOR_SEARCH = "";

    public static final String VALUE_APPCODE = "EPOS";
    public static final String VALUE_CONTENT_TYPE = "application/json";

    public static final String RATE_ZERO_PERCENT = "0%";
    public static final String MORTALITY_TYPE_BASIC = "Cơ bản";
    public static final String MORTALITY_TYPE_ADVANCE = "Vượt trội";
    public static final String BENEFIT_100PERCENT = "Bằng 100% Quyền lợi bảo hiểm tử vong";
    //mortality code
    public static final String ACCOUNT_STATUS_CODE_CPUP = "CPUP";
    public static final String MORTALITY_TYPE_CODE_EMBD = "EMBD";
    public static final String MORTALITY_TYPE_CODE_NOEM = "NOEM";

    //client type code
    public static final String CLIENT_TYPE_CODE_COMP = "COMP";

    //product type code
    public static final String PRODUCT_TYPE_CODE_PENG = "PENG";
    public static final String PRODUCT_TYPE_CODE_PENI = "PENI";
    public static final String PRODUCT_TYPE_CODE_UNIV = "UNIV";
    public static final String PRODUCT_TYPE_CODE_TRLF = "TRLF";

    //Freq type code
    public static final String FREQ_TYPE_CODE_TRLF = "TRLF";
    public static final String FREQ_TYPE_CODE_SING = "SING";

    //Product code
    public static final String PRODUCT_CODE_AUVL04 = "AUVL04";

    //Client doc type ID
    public static final String CLIENT_DOC_TYPE_VNID = "VNID";
    public static final String CLIENT_DOC_TYPE_GTTT = "GTTT";
    public static final String CLIENT_DOC_TYPE_GPKD = "GPKD";
    public static final String CLIENT_DOC_TYPE_PSPT = "PSPT";
    public static final String CLIENT_DOC_TYPE_BCER = "BCER";
    public static final String CLIENT_DOC_TYPE_THCC = "THCC";
    public static final String CLIENT_DOC_TYPE_KHAC = "KHAC";

    //Client doc type value
    public static final String CLIENT_DOC_TYPE_VALUE_VNID = "CMND";
    public static final String CLIENT_DOC_TYPE_VALUE_GTTT = "Giấy tờ tùy thân";
    public static final String CLIENT_DOC_TYPE_VALUE_GPKD = "Giấy phép đăng ký kinh doanh";
    public static final String CLIENT_DOC_TYPE_VALUE_PSPT = "Hộ chiếu";
    public static final String CLIENT_DOC_TYPE_VALUE_BCER = "Giấy khai sinh";
    public static final String CLIENT_DOC_TYPE_VALUE_THCC = "Thẻ căn cước công dân";
    public static final String CLIENT_DOC_TYPE_VALUE_KHAC = "Giấy tờ khác";

    //user type code
    public static final String USER_TYPE_CODE_STAFF = "STAF";
    public static final String USER_TYPE_CODE_COMP = "COMP";
    public static final String USER_TYPE_CODE_PERS = "PERS";

    //user type code value
    public static final String USER_TYPE_CODE_VALUE_STAFF = "Cán bộ TCT";
    public static final String USER_TYPE_CODE_VALUE_COMP = "Tổ chức";
    public static final String USER_TYPE_CODE_VALUE_PERS = "Cá nhân";

    //relt code
    public static final String RELT_CODE_OWNR = "OWNR";
    public static final String RELT_CODE_SPON = "SPON";
    public static final String RELT_CODE_LIFE = "LIFE";
    public static final String RELT_CODE_MEMB = "MEMB";
    public static final String RELT_CODE_BENE = "BENE";

    //relt code value
    public static final String RELT_CODE_OWNR_VALUE = "Bên mua BH";
    public static final String RELT_CODE_OWNR_VALUE_2 = "Bên mua bảo hiểm";
    public static final String RELT_CODE_SPON_VALUE = "Người đại điện (Bên mua BH của Hợp đồng nhóm";
    public static final String RELT_CODE_LIFE_VALUE = "Người được BH";
    public static final String RELT_CODE_LIFE_VALUE_2 = "Người được bảo hiểm";
    public static final String RELT_CODE_MEMB_VALUE = "Người được BH thành viên";
    public static final String RELT_CODE_BENE_VALUE = "Người thụ hưởng QLBH";

    //type device
    public static final String TYPE_DEVICE_PHONE = "Phone";
    public static final String TYPE_DEVICE_TABLET = "Tablet";

    // number records per page
    public static final int NUMBER_RECORDS_PER_PAGE = 10;

    //type validate
    public static final String VALIDATE_PATTERN_NULL_OR_EMPTY = "PATTERN_NULL_OR_EMPTY";
    public static final String VALIDATE_PATTERN_PHONE = "PATTERN_PHONE";
    public static final String VALIDATE_PATTERN_PASSWORD = "PATTERN_PASSWORD";
    public static final String VALIDATE_PATTERN_NULL_EMPTY_BTN = "PATTERN_NULL_EMPTY_BTN";
    public static final String VALIDATE_PATTERN_COMPARE_PASSWORD = "PATTERN_COMPARE_PASSWORD";
    public static final String VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD = "PATTERN_CONFIRM_NEW_PASSWORD";
    public static final String VALIDATE_PATTERN_EMAIL = "VALIDATE_PATTERN_EMAIL";

    //type alert dialog
    public static final String TYPE_ALERT_DIALOG_INFOR = "TYPE_INFOR";
    public static final String TYPE_ALERT_DIALOG_LINK = "TYPE_LINK";
    public static final String TYPE_ALERT_DIALOG_WARNING = "TYPE_WARING";
    public static final String TYPE_ALERT_DIALOG_ERROR = "TYPE_ERROR";
    public static final String TYPE_ALERT_DIALOG_EXCEPTION = "TYPE_EXCEPTION";
    public static final String TYPE_ALERT_DIALOG_CONFIRM = "TYPE_CONFIRM";
    public static final String TYPE_ALERT_DIALOG_CONFIRM_INFOR = "TYPE_CONFIRM_INFOR";

    public static final String ERROR_NETWORK = "ERROR_NETWORK";
    public static final String ERROR_SERVER = "ERROR_SERVER";

    //type title
    public static final String TYPE_TITLE_ALERT_DIALOG_INFOR = "Thông báo";
    public static final String TYPE_TITLE_ALERT_DIALOG_LINK = "Thông báo";
    public static final String TYPE_TITLE_ALERT_DIALOG_ERROR = "Lỗi";
    public static final String TYPE_TITLE_ALERT_DIALOG_WARNING = "Cảnh báo";
    public static final String TYPE_TITLE_ALERT_DIALOG_CONFIRM = "Xác nhận";
    public static final String TYPE_TITLE_ALERT_DIALOG_CONFIRM_INFOR = "Thông báo";

    //product type code
    public static final String REGEX_PATTERN_MOBILE_PHONE = "^0(1[2689]|[345789])[0-9]{8}$";
    public static final String REGEX_PATTERN_PASSWORD = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
    public static final String REGEX_PATTERN_EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //params of premium payment process
    public static final String VPC_ADDITIONALDATA = "vpc_AdditionalData";
    public static final String VPC_AMOUNT = "vpc_Amount";
    public static final String VPC_LOCATE = "vpc_Locale";
    public static final String VPC_BATCHNO = "vpc_BatchNo";
    public static final String VPC_COMMAND = "vpc_Command";
    public static final String VPC_MESSAGE = "vpc_Message";
    public static final String VPC_VERSION = "vpc_Version";
    public static final String VPC_ORDERINFOR = "vpc_OrderInfo";
    public static final String VPC_RECEIPTNO = "vpc_ReceiptNo";
    public static final String VPC_MERCHANT = "vpc_Merchant";
    public static final String VPC_MERCHTXNREF = "vpc_MerchTxnRef";
    public static final String VPC_AUTHORIZEED = "vpc_AuthorizeId";
    public static final String VPC_TRANSACTIONNO = "vpc_TransactionNo";
    public static final String VPC_ACQRESPONSECODE = "vpc_AcqResponseCode";
    public static final String VPC_RESPONSECODE = "vpc_ResponseCode";
    public static final String VPC_CARDTYPE = "vpc_CardType";
    public static final String VPC_CURRENTCYCODE = "vpc_CurrencyCode";
    public static final String VPC_SECUREHASH = "vpc_SecureHash";
    public static final String PATH_RESULT = "pathResult";
    public static final String VPC_RETURN_URL = "vpc_ReturnURL";

    public static final String MENU_ID_ACCOUNT_INFOR = "account-info";
    public static final String MENU_ID_UPDATE_ACCOUNT_INFOR = "update-account-info";
    public static final String MENU_ID_RETRIEVE_CONTRACT = "retrieve-contact";
    public static final String MENU_ID_CHILD_GROUP = "group-product-view";
    public static final String MENU_ID_CHILD_INVIDUAL = "personal-product-view";
    public static final String MENU_ID_CHILD_EDIT_INFOR = "online-trading";

    //type Contract menu
    public static final String TYPE_CONTRACT_INVISIBLE = "type_contract_invisible";
    public static final String TYPE_CONTRACT_IS_GROUP = "type_contract_is_group";
    public static final String TYPE_CONTRACT_IS_INVIDUAL = "type_contract_is_invidual";

    public static MenuDTO PARENT_MENU_RETREIVE_CONTRACT;
    public static MenuDTO CHILD_MENU_GROUP;
    public static MenuDTO CHILD_MENU_INVIDUAL;
    public static MenuDTO CHILD_MENU_EDIT_INFOR;
    public static String DATA_UPDATE_LASTTIME = "";

    //role Account
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_GROUP = "ROLE_GROUP";

    //type Account
    public static final String TYPE_ACCOUNT_BVLIFE = "TYPE_ACCOUNT_BVLIFE";
    public static final String TYPE_ACCOUNT_TALISMAN = "TYPE_ACCOUNT_TALISMAN";

    // response text stream
    public static final String STREAM_RESPONSE_TEXT_SUCCESS = "SUCCESS";
    public static final String STREAM_RESPONSE_LOADED_VIEW = "LOADED_VIEW";
    public static final String STREAM_RESPONSE_TEXT_UNSUCCESS = "UNSUCCESS";
    public static final String STREAM_RESPONSE_TEXT_UNSUCCESS_404 = "UNSUCCESS_404";
    public static final String STREAM_RESPONSE_TEXT_NETWORK_ERROR = "NETWORK_ERROR";
    public static final String STREAM_RESPONSE_TEXT_FILE_NOT_FOUND = "FILE_NOT_FOUND";

    //path of folder save pdf
    public static final String PATH_OF_FOLDER_SAVE_PDF = "/pdfDocument";

    //type action(download or view pdf)
    public static final String PDF_TYPE_DOWNLOAD = "PDF_TYPE_DOWNLOAD";
    public static final String XML_TYPE_DOWNLOAD = "XML_TYPE_DOWNLOAD";
    public static final String PDF_TYPE_VIEW = "PDF_TYPE_VIEW";
    public static final String PATH_TREE_RAW = "/tree/raw";
    public static final String PATH_TREE_DOWNLOADS = "/tree/downloads";
    public static final String PATH_TREE_PRIMARY = "/tree/primary";
    public static final String PATH_ROOT = "/storage/emulated/0/";
    public static final String PATH_ROOT_DOWNLOAD = "/storage/emulated/0/Download/";

    // array string
    public static final String[] VALUE_RELT_CODE = {RELT_CODE_OWNR_VALUE_2, RELT_CODE_LIFE_VALUE_2};

    //Database Infor
    // Database Version
    public static final int DATABASE_VERSION = 1;
    //Database path
    public static final String DB_PATH = "/data/data/app.com.baoviet/databases/";
    // Database Name
    public static final String DATABASE_NAME = "baovietDB";
    public static final String TABLE_NAME = "DataLocal";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_API = "api";
    public static final String COLUMN_PARAMS = "params";
    public static final String COLUMN_RESPONSE = "response";

    //Notification Infor
    public static final String NUMBER_RECORD_NOTIFICATION = "ALL";
    public static final String NOTIFY_TYPE_FUNCTION = "PAGE";
    public static final String NOTIFY_TYPE_LINK = "URL";
    public static final String NOTIFY_TYPE_OTHER = "OTHER";

    public static final String NOTIFY_TYPE_DES_PAYMENT = "/pages/premium-payment";

    //request code activity for result
    public static final int REQUEST_CODE_CHOOSE_FOLDER = 1;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + Constant.TABLE_NAME + "("
                    + Constant.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + Constant.COLUMN_USERNAME + " TEXT, "
                    + Constant.COLUMN_PASSWORD + " TEXT, "
                    + Constant.COLUMN_API + " TEXT, "
                    + Constant.COLUMN_PARAMS + " TEXT, "
                    + Constant.COLUMN_RESPONSE + " TEXT"
                    + ")";

    //STATE EXCEPTION
    public static final String STATE_EXCEPTION = "STATE_EXCEPTION";

    public static final String ACTION_DIALOG_DEFAULT = "ACTION_DIALOG_DEFAULT";
    public static final String ACTION_DIALOG_LINK = "ACTION_DIALOG_LINK";
    public static final String ACTION_DIALOG_BACK = "ACTION_DIALOG_BACK";
    public static final String ACTION_DIALOG_DONE_PAYMENT = "ACTION_DIALOG_DONE_PAYMENT";
    public static final String ACTION_DIALOG_LOGOUT = "ACTION_DIALOG_LOGOUT";
    public static final String ACTION_DIALOG_ACCEPT_RESTART = "ACTION_DIALOG_ACCEPT_RESTART";
    public static final String ACTION_DIALOG_CHANGE_PASSWORD = "ACTION_DIALOG_CHANGE_PASSWORD";
    public static final String ACTION_DIALOG_VALIDATE_REGISTER = "ACTION_DIALOG_VALIDATE_REGISTER";
    public static final String ACTION_DIALOG_CLEAR_TEXT_REGISTER = "ACTION_DIALOG_CLEAR_TEXT_REGISTER";
    public static final String ACTION_DIALOG_PAYMENT_NAPAS = "ACTION_DIALOG_PAYMENT_NAPAS";
    public static final String ACTION_DIALOG_PAYMENT_BVBANK = "ACTION_DIALOG_PAYMENT_BVBANK";
    public static final String ACTION_DIALOG_CONFIRM_FORGOT_PASSWORD = "ACTION_DIALOG_CONFIRM_FORGOT_PASSWORD";

    //SAVED ACCOUNT NUMBER
    public static String ACCOUNT_NUMBER_SAVED = Constant.EMPTY;
}
