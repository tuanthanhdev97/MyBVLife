package app.com.baoviet;

import android.Manifest;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.com.baoviet.adapter.CustomExpandableListAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.SaveSharedPreference;
import app.com.baoviet.entity.MenuDTO;
import app.com.baoviet.entity.MenuNavigation;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.fragment.ContactFragment;
import app.com.baoviet.fragment.FooterFragment;
import app.com.baoviet.fragment.ForgotPasswordFragment;
import app.com.baoviet.fragment.GuideFragment;
import app.com.baoviet.fragment.HomeFragment;
import app.com.baoviet.fragment.InforAccountFragment;
import app.com.baoviet.fragment.PaymentFragment;
import app.com.baoviet.fragment.ProductFragment;
import app.com.baoviet.fragment.RegisterFragment;
import app.com.baoviet.fragment.SecurityFragment;
import app.com.baoviet.fragment.ServiceFragment;
import app.com.baoviet.fragment.TermFragment;
import app.com.baoviet.fragment.UpdateAccountFragment;
import app.com.baoviet.fragment.UpdatePasswordFragment;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.GrantPermission;
import app.com.baoviet.utility.StringUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DialogEventListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View viewHeader;
    private ImageView imgvHeaderMenu;
    private TextView tvHeaderFullName;
    private TextView tvHeaderUserCode;
    private TextView tvMainTypeDevice;
    private LinearLayout lnHeaderNavLogout;
    public TextView tvActionBar;

    private List<MenuNavigation> listMenuNavigation;
    private ExpandableListView mExpandableListView;
    public ExpandableListAdapter mExpandableListAdapter;
    public List<String> mExpandableListTitle;
    public Map<String, List<String>> mExpandableListData;
    private UserDTO user;
    private String stateException = Constant.EMPTY;
    private List<MenuDTO> myModelList;
    public boolean isTransferToFunction;

    private String userCode;
    private String passwordOld;
    String keyTransfer = Constant.EMPTY;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;
    String roleAccount = Constant.EMPTY;
    String typeAccount = Constant.EMPTY;
    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    // Storage Permissions
    private static final int REQUEST_CALL_PHONE = 1;
    private static String[] PERMISSIONS_CALL_PHONE = {
            Manifest.permission.CALL_PHONE,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInterface();
        Intent intent = getIntent();
        if (intent != null) {
            //get data from bundle
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                stateException = bundle.getString(Keys.KEY_EXCEPTION);
                keyTransfer = bundle.getString(Keys.KEY_TRANSFER);
                user = (UserDTO) bundle.getSerializable(Keys.KEY_USER_LOGIN);
                userCode = bundle.getString(Keys.KEY_USER_CODE);
                passwordOld = bundle.getString(Keys.KEY_OLD_PASSWORD);
                //set token
                if (user != null) {
                    if (user.isGroup()) {
                        roleAccount = Constant.ROLE_GROUP;
                    } else if (user.isAdmin()) {
                        roleAccount = Constant.ROLE_ADMIN;
                    } else if (user.isAccount()) {
                        roleAccount = Constant.ROLE_USER;
                    }
                    if (StringUtil.isNullOrEmpty(user.getAccountOtherNumber())) {
                        typeAccount = Constant.TYPE_ACCOUNT_TALISMAN;
                    } else {
                        typeAccount = Constant.TYPE_ACCOUNT_BVLIFE;
                    }
                    tvHeaderFullName.setText(user.getFullName());
                    tvHeaderUserCode.setText(user.getUserCode());
                }
                if (StringUtil.isNullOrEmpty(keyTransfer)) {
                    message = getResources().getString(R.string.message_error_system);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
                }
            } else {
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            }

        } else {
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
        }
        //init data for list
        listMenuNavigation = getListMenuNavigation();

        //get left menu
        getLeftMenu();
        //navigate to fragment
        navigationFragment(keyTransfer);

        if (Constant.TYPE_DEVICE_TABLET.equals(tvMainTypeDevice.getText())) {
            // load footer fragment
            Fragment frFooter = new FooterFragment();
            FragmentManager fmFooter = getSupportFragmentManager();
            FragmentTransaction fmTransactionFooter = fmFooter.beginTransaction();
            fmTransactionFooter.replace(R.id.lnFooter, frFooter);
            fmTransactionFooter.commit();
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((fm.getBackStackEntryCount() == Constant.INT_1 || fm.getBackStackEntryCount() == Constant.INT_0)) {
                if (Constant.TRANSFER_TO_NOTIFICATION_PAGE.equals(keyTransfer)) {
                    navigationFragment(Constant.TRANSFER_LOGIN_TO_HOME);
                } else {
                    finish();
                }
            } else if (fm.getBackStackEntryCount() > Constant.INT_1) {
                if (Constant.TRANSFER_TO_NOTIFICATION_PAGE.equals(keyTransfer)) {
                    if (isTransferToFunction) {
                        fm.popBackStack();
                    } else {
                        finish();
                    }
                } else {
                    fm.popBackStack();
                }
            }
        }
        isTransferToFunction = false;

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Constant.TOKEN = Constant.EMPTY;
//        Constant.USER_CODE = Constant.EMPTY;
//        Constant.PASSWORD_LOCAL = Constant.EMPTY;
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //get menu
    public void getLeftMenu() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, listMenuNavigation);
        mExpandableListView.setAdapter(mExpandableListAdapter);
//        ((BaseExpandableListAdapter) mExpandableListAdapter).notifyDataSetChanged();
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                getSupportActionBar().setTitle(R.string.film_genres);
            }
        });
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                String keyTransfer = listMenuNavigation.get(groupPosition).getKeyTransfer();
                List<MenuNavigation> listChild = listMenuNavigation.get(groupPosition).getListMenuChild();

                RelativeLayout lnNavMenuGroup = (RelativeLayout) view.findViewById(R.id.lnNavMenuGroup);
                ImageView imgNavViewParent = (ImageView) view.findViewById(R.id.imgNavViewParent);
                TextView listTitleTextView = (TextView) view
                        .findViewById(R.id.listTitle);
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                if (listChild == null) {
                    navigationFragment(keyTransfer);
                    drawer.closeDrawer(GravityCompat.START);
                } else if (listChild != null) {
                    if (Constant.INT_0 == listChild.size()) {
                        navigationFragment(keyTransfer);
                        drawer.closeDrawer(GravityCompat.START);
                        lnNavMenuGroup.setBackgroundColor(getResources().getColor(R.color.transparent));
                        listTitleTextView.setTextColor(getResources().getColor(R.color.black));
                        tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                    } else {
                        if (!expandableListView.isGroupExpanded(groupPosition)) {
                            lnNavMenuGroup.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gradient_menu));
                            listTitleTextView.setTextColor(getResources().getColor(R.color.white));
                            tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                        } else {
                            lnNavMenuGroup.setBackgroundColor(getResources().getColor(R.color.transparent));
                            listTitleTextView.setTextColor(getResources().getColor(R.color.black));
                            tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.blue);
                        }

                    }
                }
                imgNavViewParent.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                return false;
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String keyTransfer = Constant.EMPTY;
                MenuNavigation menuParent = listMenuNavigation.get(groupPosition);
                List<MenuNavigation> listChild = menuParent.getListMenuChild();
                if (listChild != null) {
                    if (Constant.INT_0 < listChild.size()) {
                        keyTransfer = listChild.get(childPosition).getKeyTransfer();
                    }
                }
//                getSupportActionBar().setTitle(keyTransfer);
                navigationFragment(keyTransfer);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });


    }

    // method navigate fragment
    public void navigationFragment(String keyTransfer) {
        fm = getSupportFragmentManager();
        Intent intentContract;
        Bundle bundle;
        switch (keyTransfer) {
            case Constant.TRANSFER_LOGIN_TO_SECURITY:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_SECURITY);
                intentContract = new Intent(this, FooterActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_LOGIN_TO_TERMS:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_TERMS);
                intentContract = new Intent(this, FooterActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_LOGIN_TO_CONTACT:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_CONTACT);
                intentContract = new Intent(this, FooterActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_LOGIN_TO_REGISTER:
                fr = new RegisterFragment();
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_register_title);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_REGISTER);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_REGISTER);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_FORGOT_PASSWORD:
                fr = new ForgotPasswordFragment();
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_forgot_password_header);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_FORGOT_PASSWORD);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_FORGOT_PASSWORD);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_UPDATE_PASSWORD:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_update_password_header);
                bundle = new Bundle();
                bundle.putString(Keys.KEY_USER_CODE, this.userCode);
                bundle.putString(Keys.KEY_OLD_PASSWORD, this.passwordOld);
                fr = new UpdatePasswordFragment();
                fr.setArguments(bundle);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_UPDATE_PASSWORD);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_UPDATE_PASSWORD);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_PAYMENT:
//                fr = new PaymentFragment();
//                tvActionBar.setVisibility(View.VISIBLE);
//                tvActionBar.setText(R.string.txt_payment_header);
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_PAYMENT);
//                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_PAYMENT);
//                fragmentTransaction.commit();
                bundle = new Bundle();
                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                    bundle.putString(Keys.KEY_PHONE_NUMBER, StringUtil.setTextValue(user.getMobifone()));
                    bundle.putString(Keys.KEY_FULLNAME, StringUtil.setTextValue(user.getFullName()));
                    bundle.putStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER, (ArrayList<String>) user.getAccounts());
                }
                fr = new PaymentFragment();
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_payment_header);
                fr.setArguments(bundle);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_PAYMENT);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_PAYMENT);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_HOME:
                tvActionBar.setVisibility(View.GONE);
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                fr = new HomeFragment();
                fr.setArguments(bundle);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_HOME);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_HOME);
                fragmentTransaction.commit();
                break;
            case Constant.NAV_PAYMENT_LV1:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_instructions);
                fr = new GuideFragment();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_PAYMENT_LV1);
                fragmentTransaction.addToBackStack(Constant.NAV_PAYMENT_LV1);
                fragmentTransaction.commit();
                break;
//            case Constant.NAV_PAYMENT_LV0:
//                bundle = new Bundle();
//                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
//                    bundle.putString(Keys.KEY_PHONE_NUMBER, StringUtil.setTextValue(user.getMobifone()));
//                    bundle.putString(Keys.KEY_FULLNAME, StringUtil.setTextValue(user.getFullName()));
//                    bundle.putStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER, (ArrayList<String>) user.getAccounts());
//                }
//                fr = new PaymentFragment();
//                tvActionBar.setVisibility(View.VISIBLE);
//                tvActionBar.setText(R.string.txt_payment_header);
//                fr.setArguments(bundle);
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_PAYMENT_LV0);
//                fragmentTransaction.addToBackStack(Constant.NAV_PAYMENT_LV0);
//                fragmentTransaction.commit();
//                break;
            case Constant.NAV_CREATE_NEW_ACCOUNT_NO_LOGIN:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_sign_up);
                fr = new RegisterFragment();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_CREATE_NEW_ACCOUNT_NO_LOGIN);
                fragmentTransaction.addToBackStack(Constant.NAV_CREATE_NEW_ACCOUNT_NO_LOGIN);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;

            case Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;

            case Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;

            case Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;

            case Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_CONTRACT_TO_BENEFIT:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_BENEFIT);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_CONTRACT_TO_EBILL:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_EBILL);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT);
                intentContract = new Intent(this, ContractActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.NAV_INFOR_ACCOUNT:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_infor_account_header);
                bundle = new Bundle();
                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                    bundle.putString(Keys.KEY_USER_CODE, user.getUserCode());
                }
                fr = new InforAccountFragment();
                fr.setArguments(bundle);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_INFOR_ACCOUNT);
                fragmentTransaction.addToBackStack(Constant.NAV_INFOR_ACCOUNT);
                fragmentTransaction.commit();
                break;
            case Constant.NAV_UPDATE_ACCOUNT:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.txt_update_account_header);
                bundle = new Bundle();
                if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                    bundle.putString(Keys.KEY_USER_TYPE_CODE, user.getUserTypeCode());
                    bundle.putString(Keys.KEY_USERNAME, user.getUserCode());
                }
                fr = new UpdateAccountFragment();
                fr.setArguments(bundle);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.NAV_UPDATE_ACCOUNT);
                fragmentTransaction.addToBackStack(Constant.NAV_UPDATE_ACCOUNT);
                fragmentTransaction.commit();
                break;
            case Constant.NAV_LOG_OUT:
                Constant.TOKEN = Constant.EMPTY;
                Constant.USER_CODE = Constant.EMPTY;
                Constant.PASSWORD_LOCAL = Constant.EMPTY;
                Constant.ACCOUNT_NUMBER_SAVED = Constant.EMPTY;
                this.finish();
//                Intent myIntent = new Intent(this, LoginActivity.class);
//                this.startActivity(myIntent);
                break;
            case Constant.TRANSFER_TO_NOTIFICATION_PAGE:
//                bundle = new Bundle();
//                bundle.putString(Keys.KEY_USER_CODE, user.getUserCode());
//                tvActionBar.setVisibility(View.VISIBLE);
//                tvActionBar.setText(R.string.txt_notification_header);
//                fr = new NotificationFragment();
//                fr.setArguments(bundle);
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_TO_NOTIFICATION_PAGE);
//                fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_NOTIFICATION_PAGE);
//                fragmentTransaction.commit();

                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                intentContract = new Intent(this, NotificationActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
//                finish();
                break;

            case Constant.TRANSFER_TO_PRODUCT:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.title_home_tab_item_product_header);
                fr = new ProductFragment();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_TO_PRODUCT);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_PRODUCT);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_TO_SERVICE:
                tvActionBar.setVisibility(View.VISIBLE);
                tvActionBar.setText(R.string.title_home_tab_item_service_header);
                fr = new ServiceFragment();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_TO_SERVICE);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_SERVICE);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_TO_INVEST:
                Intent intentInvest = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.INVEST_LINK));
                startActivity(intentInvest);
                break;
            case Constant.TRANSFER_TO_SAVE:
                Intent intentSave = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.SAVE_MONEY_LINK));
                startActivity(intentSave);
                break;
            case Constant.TRANSFER_TO_PROTECTED:
                Intent intentProtected = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.PROTECTED_LINK));
                startActivity(intentProtected);
                break;
            case Constant.TRANSFER_TO_RETIRE:
                Intent intentRetire = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.RETIRE_LINK));
                startActivity(intentRetire);
                break;
            case Constant.TRANSFER_TO_BUSINESS:
                Intent intentBusiness = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BUSINESS_LINK));
                startActivity(intentBusiness);
                break;
            case Constant.TRANSFER_TO_BANK:
                Intent intentBank = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BANK_LINK));
                startActivity(intentBank);
                break;
            case Constant.TRANSFER_TO_CHAT:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHAT_LINK));
                startActivity(browserIntent);
                break;
            case Constant.TRANSFER_TO_HOTLINE:
                if (GrantPermission.hasPermissionCallPhone(this)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18006966"));
                    startActivity(callIntent);
                }

                break;
//            case Constant.TRANSFER_TO_VIEW_PDF:
//                fr = new ViewPDFFragment();
//                tvActionBar.setVisibility(View.VISIBLE);
//                tvActionBar.setText(R.string.txt_update_password_header);
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_TO_VIEW_PDF);
//                fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_VIEW_PDF);
//                fragmentTransaction.commit();
//                break;
            default:
                bundle = new Bundle();
                bundle.putSerializable(Keys.KEY_USER_LOGIN, user);
                fr = new HomeFragment();
                fr.setArguments(bundle);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, fr, Constant.TRANSFER_LOGIN_TO_HOME);
                fragmentTransaction.addToBackStack(Constant.TRANSFER_LOGIN_TO_HOME);
                fragmentTransaction.commit();
                break;
        }
    }

    public void hiddenHeader() {
        tvActionBar.setVisibility(View.GONE);
    }

    // initial view component
    public void initInterface() {
        alert = new AlertDialogCustom(this);
        alert.dialogEventListener = this;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvMainTypeDevice = (TextView) findViewById(R.id.tvMainTypeDevice);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        tvActionBar = (TextView) findViewById(R.id.tvActionBar);
        viewHeader = navigationView.getHeaderView(0);
        imgvHeaderMenu = (ImageView) viewHeader.findViewById(R.id.imgvHeaderMenu);
        tvHeaderFullName = (TextView) viewHeader.findViewById(R.id.tvHeaderFullName);
        tvHeaderUserCode = (TextView) viewHeader.findViewById(R.id.tvHeaderUserCode);
        lnHeaderNavLogout = (LinearLayout) viewHeader.findViewById(R.id.lnHeaderNavLogout);

        // event onclick
        lnHeaderNavLogout.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        //disable swipe drawer
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navigationView.getBackground().setAlpha(240);
    }

    // event onclick textview at footer
    public void onClickTextView(View v) {
        Fragment fr;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.tvMainPolicy:
                fr = new SecurityFragment();
                fragmentTransaction.replace(R.id.frameContent, fr);
                fragmentTransaction.commit();
                break;
            case R.id.tvMainTerm:
                fr = new TermFragment();
                fragmentTransaction.replace(R.id.frameContent, fr);
                fragmentTransaction.commit();
                break;
            case R.id.tvMainContact:
                fr = new ContactFragment();
                fragmentTransaction.replace(R.id.frameContent, fr);
                fragmentTransaction.commit();
                break;
            case R.id.tvMainChat:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHAT_LINK));
                startActivity(browserIntent);
                break;
            case R.id.tvMainHotline:
                if (GrantPermission.hasPermissionCallPhone(this)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18006966"));
                    startActivity(callIntent);
                }
                break;
            default:
                break;
        }
    }

    public void openCloseMenu() {
        if (!drawer.isDrawerOpen(Gravity.START)) {
            drawer.openDrawer(Gravity.START);
        } else {
            drawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnHeaderNavLogout:
                message = getResources().getString(R.string.message_confirm_logout);
                typeDialog = Constant.TYPE_ALERT_DIALOG_CONFIRM;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_LOGOUT, false);
                break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    private List<MenuNavigation> getListMenuNavigation() {

        List<MenuNavigation> listMenu = new ArrayList<>();
        //menu payment
        MenuNavigation menuPayment = new MenuNavigation();
        menuPayment.setImageLogo(R.mipmap.ic_nav_menu_payment);
        menuPayment.setTitle(getString(R.string.title_home_tab_payment));
        menuPayment.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_PAYMENT);

        //menu child of contract

        MenuNavigation menuContractGeneralInfor = new MenuNavigation();
        menuContractGeneralInfor.setImageLogo(R.mipmap.ic_nav_menu_general_infor);
        menuContractGeneralInfor.setTitle(getString(R.string.title_home_tab_item_general_infor));
        menuContractGeneralInfor.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR);

        MenuNavigation menuContractCustomerInfor = new MenuNavigation();
        menuContractCustomerInfor.setImageLogo(R.mipmap.ic_nav_menu_customer);
        menuContractCustomerInfor.setTitle(getString(R.string.title_home_tab_item_customer_infor));
        menuContractCustomerInfor.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR);

        MenuNavigation menuContractValueAccount = new MenuNavigation();
        menuContractValueAccount.setImageLogo(R.mipmap.ic_nav_menu_value);
        menuContractValueAccount.setTitle(getString(R.string.title_home_tab_item_value_account));
        menuContractValueAccount.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT);

        MenuNavigation menuContractPaymentProcess = new MenuNavigation();
        menuContractPaymentProcess.setImageLogo(R.mipmap.ic_nav_menu_payment_process);
        menuContractPaymentProcess.setTitle(getString(R.string.title_home_tab_item_payment_process));
        menuContractPaymentProcess.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS);

        MenuNavigation menuContractFee = new MenuNavigation();
        menuContractFee.setImageLogo(R.mipmap.ic_nav_menu_cost);
        menuContractFee.setTitle(getString(R.string.title_home_tab_item_fee_and_cost));
        menuContractFee.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST);

        MenuNavigation menuContractBenefit = new MenuNavigation();
        menuContractBenefit.setImageLogo(R.mipmap.ic_nav_menu_benefit);
        menuContractBenefit.setTitle(getString(R.string.title_home_tab_item_benefit));
        menuContractBenefit.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_BENEFIT);

        MenuNavigation menuContractEbill = new MenuNavigation();
        menuContractEbill.setImageLogo(R.mipmap.ic_nav_menu_bill);
        menuContractEbill.setTitle(getString(R.string.title_home_tab_item_ebill));
        menuContractEbill.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_EBILL);

        MenuNavigation menuContractReport = new MenuNavigation();
        menuContractReport.setImageLogo(R.mipmap.ic_nav_menu_report);
        menuContractReport.setTitle(getString(R.string.title_home_tab_item_annual_report));
        menuContractReport.setKeyTransfer(Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT);

        List<MenuNavigation> listContractChild = new ArrayList<>();
        listContractChild.add(menuContractGeneralInfor);
        listContractChild.add(menuContractCustomerInfor);
        listContractChild.add(menuContractValueAccount);
        listContractChild.add(menuContractPaymentProcess);
        if (Constant.TYPE_ACCOUNT_TALISMAN.equals(typeAccount)) {
            listContractChild.add(menuContractFee);
        }
        listContractChild.add(menuContractBenefit);
        listContractChild.add(menuContractEbill);
        listContractChild.add(menuContractReport);

        //menu contract
        MenuNavigation menuContract = new MenuNavigation();
        menuContract.setImageLogo(R.mipmap.ic_nav_menu_contract);
        menuContract.setTitle(getString(R.string.title_home_tab_contract));
        menuContract.setListMenuChild(listContractChild);

        //menu infor account
        MenuNavigation menuInforAccount = new MenuNavigation();
        menuInforAccount.setImageLogo(R.mipmap.ic_nav_menu_account_infor);
        menuInforAccount.setTitle(getString(R.string.title_home_tab_item_infor_account));
        menuInforAccount.setKeyTransfer(Constant.NAV_INFOR_ACCOUNT);
        //menu update account infor
        MenuNavigation menuUpdateAccount = new MenuNavigation();
        menuUpdateAccount.setImageLogo(R.mipmap.ic_nav_menu_update_account);
        menuUpdateAccount.setTitle(getString(R.string.title_home_tab_item_update_infor));
        menuUpdateAccount.setKeyTransfer(Constant.NAV_UPDATE_ACCOUNT);
        //menu trade online
        MenuNavigation menuTradeOnline = new MenuNavigation();
        menuTradeOnline.setImageLogo(R.mipmap.ic_nav_menu_trade);
        menuTradeOnline.setTitle(getString(R.string.title_home_tab_trade_online));
        menuTradeOnline.setKeyTransfer(Constant.TRANSFER_TO_TRADE_ONLINE);

        //menu child product
        MenuNavigation menuProductChild = new MenuNavigation();
        menuProductChild.setImageLogo(R.mipmap.ic_nav_menu_product_child);
        menuProductChild.setTitle(getString(R.string.title_home_tab_item_product));
        menuProductChild.setKeyTransfer(Constant.TRANSFER_TO_PRODUCT);

        MenuNavigation menuProductService = new MenuNavigation();
        menuProductService.setImageLogo(R.mipmap.ic_nav_menu_service_child);
        menuProductService.setTitle(getString(R.string.title_home_tab_item_service));
        menuProductService.setKeyTransfer(Constant.TRANSFER_TO_SERVICE);

        List<MenuNavigation> listProductChild = new ArrayList<>();
        listProductChild.add(menuProductChild);
        listProductChild.add(menuProductService);

        // menu product
        MenuNavigation menuProduct = new MenuNavigation();
        menuProduct.setImageLogo(R.mipmap.ic_nav_menu_product);
        menuProduct.setTitle(getString(R.string.title_home_tab_product));
        menuProduct.setListMenuChild(listProductChild);

        // with mobile

        MenuNavigation menuContactSecurity = new MenuNavigation();
        menuContactSecurity.setImageLogo(R.mipmap.ic_nav_menu_security);
        menuContactSecurity.setTitle(getString(R.string.title_home_tab_item_security));
        menuContactSecurity.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_SECURITY);

        MenuNavigation menuContactContact = new MenuNavigation();
        menuContactContact.setImageLogo(R.mipmap.ic_nav_menu_contact);
        menuContactContact.setTitle(getString(R.string.title_home_tab_item_contact));
        menuContactContact.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_CONTACT);

        MenuNavigation menuContactTerm = new MenuNavigation();
        menuContactTerm.setImageLogo(R.mipmap.ic_nav_menu_terms);
        menuContactTerm.setTitle(getString(R.string.title_home_tab_item_terms));
        menuContactTerm.setKeyTransfer(Constant.TRANSFER_LOGIN_TO_TERMS);

        MenuNavigation menuContactChat = new MenuNavigation();
        menuContactChat.setImageLogo(R.mipmap.ic_nav_menu_chat);
        menuContactChat.setTitle(getString(R.string.title_home_tab_item_chat_online));
        menuContactChat.setKeyTransfer(Constant.TRANSFER_TO_CHAT);

        MenuNavigation menuContactHotline = new MenuNavigation();
        menuContactHotline.setImageLogo(R.mipmap.ic_nav_menu_hotline);
        menuContactHotline.setTitle(getString(R.string.title_home_tab_item_hotline));
        menuContactHotline.setKeyTransfer(Constant.TRANSFER_TO_HOTLINE);

        List<MenuNavigation> listContactChild = new ArrayList<>();
        listContactChild.add(menuContactSecurity);
        listContactChild.add(menuContactContact);
        listContactChild.add(menuContactTerm);
        listContactChild.add(menuContactChat);
        listContactChild.add(menuContactHotline);

        // contact
        MenuNavigation menuContact = new MenuNavigation();
        menuContact.setImageLogo(R.mipmap.ic_nav_menu_contact_group);
        menuContact.setTitle(getString(R.string.title_home_tab_contact));
        menuContact.setListMenuChild(listContactChild);


        listMenu.add(menuPayment);
        if (Constant.ROLE_USER.equals(roleAccount)) {
            listMenu.add(menuContract);
        }
        listMenu.add(menuInforAccount);
        listMenu.add(menuUpdateAccount);
        listMenu.add(menuTradeOnline);
        listMenu.add(menuProduct);
        if (Constant.TYPE_DEVICE_PHONE.equals(tvMainTypeDevice.getText())) {
            listMenu.add(menuContact);
        }
        return listMenu;
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {

        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_LOGOUT:
                    SaveSharedPreference.setUser(this, Constant.EMPTY, Constant.EMPTY);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Add the bundle to the intent
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
}
