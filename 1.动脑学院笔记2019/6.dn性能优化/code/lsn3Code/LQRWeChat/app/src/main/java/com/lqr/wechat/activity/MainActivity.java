package com.lqr.wechat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lqr.wechat.AppConst;
import com.lqr.wechat.R;
import com.lqr.wechat.adapter.MainPagerAdapter;
import com.lqr.wechat.broadcast.AuthBroadcastReceiver;
import com.lqr.wechat.factory.PopupWindowFactory;
import com.lqr.wechat.fragment.BaseFragment;
import com.lqr.wechat.fragment.ContactsFragment;
import com.lqr.wechat.fragment.DiscoveryFragment;
import com.lqr.wechat.fragment.MeFragment;
import com.lqr.wechat.fragment.MessageFragment;
import com.lqr.wechat.nimsdk.NimAccountSDK;
import com.lqr.wechat.nimsdk.NimFriendSDK;
import com.lqr.wechat.nimsdk.NimSystemSDK;
import com.lqr.wechat.nimsdk.NimTeamSDK;
import com.lqr.wechat.nimsdk.NimUserInfoSDK;
import com.lqr.wechat.nimsdk.custom.CustomAttachParser;
import com.lqr.wechat.utils.LogUtils;
import com.lqr.wechat.utils.StringUtils;
import com.lqr.wechat.utils.UIUtils;
import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.friend.model.FriendChangedNotify;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @????????? CSDN_LQR
 * @?????? ???????????????
 */
public class MainActivity extends BaseActivity {

    public static final int REQ_CLEAR_UNREAD = 100;

    private int exit = 0;
    private MessageFragment mMessageFragment;
    private ContactsFragment mContactsFragment;
    private DiscoveryFragment mDiscoveryFragment;
    private MeFragment mMeFragment;
    private List<BaseFragment> mFragments;

    private PopupWindow mPopupWindow;

    private List<SystemMessage> items = new ArrayList<>();//????????????
    private static final boolean MERGE_ADD_FRIEND_VERIFY = true; // ???????????????????????????????????????????????????????????????????????????????????????????????????
    private Set<String> addFriendVerifyRequestAccounts = new HashSet<>(); // ?????????????????????????????????????????????????????????

    private AuthBroadcastReceiver mAuthBroadcastReceiver;
    private Observer<StatusCode> mOnlineStatusObserver;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.vpContent)
    ViewPager mVpContent;

    // ??????
    @InjectView(R.id.llButtom)
    LinearLayout mLlBottom;

    @InjectView(R.id.tvMessageNormal)
    TextView mTvMessageNormal;
    @InjectView(R.id.tvMessagePress)
    TextView mTvMessagePress;
    @InjectView(R.id.tvMessageTextNormal)
    TextView mTvMessageTextNormal;
    @InjectView(R.id.tvMessageTextPress)
    TextView mTvMessageTextPress;
    @InjectView(R.id.tvMessageCount)
    public TextView mTvMessageCount;

    @InjectView(R.id.tvContactsNormal)
    TextView mTvContactsNormal;
    @InjectView(R.id.tvContactsPress)
    TextView mTvContactsPress;
    @InjectView(R.id.tvContactsTextNormal)
    TextView mTvContactsTextNormal;
    @InjectView(R.id.tvContactsTextPress)
    TextView mTvContactsTextPress;
    @InjectView(R.id.tvContactCount)
    public TextView mTvContactCount;

    @InjectView(R.id.tvDiscoveryNormal)
    TextView mTvDiscoveryNormal;
    @InjectView(R.id.tvDiscoveryPress)
    TextView mTvDiscoveryPress;
    @InjectView(R.id.tvDiscoveryTextNormal)
    TextView mTvDiscoveryTextNormal;
    @InjectView(R.id.tvDiscoveryTextPress)
    TextView mTvDiscoveryTextPress;
    @InjectView(R.id.tvDiscoveryCount)
    public TextView mTvDiscoveryCount;

    @InjectView(R.id.tvMeNormal)
    TextView mTvMeNormal;
    @InjectView(R.id.tvMePress)
    TextView mTvMePress;
    @InjectView(R.id.tvMeTextNormal)
    TextView mTvMeTextNormal;
    @InjectView(R.id.tvMeTextPress)
    TextView mTvMeTextPress;
    @InjectView(R.id.tvMeCount)
    public TextView mTvMeCount;




    @OnClick({R.id.llMessage, R.id.llContacts, R.id.llDiscovery, R.id.llMe})
    public void click(View view) {
        setTransparency();
        switch (view.getId()) {
            case R.id.llMessage:
                mVpContent.setCurrentItem(0, false);
                mTvMessagePress.getBackground().setAlpha(255);
                mTvMessageTextPress.setTextColor(Color.argb(255, 69, 192, 26));
                break;
            case R.id.llContacts:
                mVpContent.setCurrentItem(1, false);
                mTvContactsPress.getBackground().setAlpha(255);
                mTvContactsTextPress.setTextColor(Color.argb(255, 69, 192, 26));
                break;
            case R.id.llDiscovery:
                mVpContent.setCurrentItem(2, false);
                mTvDiscoveryPress.getBackground().setAlpha(255);
                mTvDiscoveryTextPress.setTextColor(Color.argb(255, 69, 192, 26));
                break;
            case R.id.llMe:
                mVpContent.setCurrentItem(3, false);
                mTvMePress.getBackground().setAlpha(255);
                mTvMeTextPress.setTextColor(Color.argb(255, 69, 192, 26));
                break;
        }
    }


    @Override
    public void init() {
        //?????????????????????????????????
        registerBroadcastReceiver();
        //??????????????????
        observerLineStatus();
        //????????????????????????
        observeUserInfoUpdate();
        //???????????????????????????
        observeFriendChangedNotify();
        //???????????????????????????
        observeTeamChangedNotify();
        //????????????????????????
        observeReceiveSystemMsg();
        // ?????????????????????????????????
        NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initToolbar();

        //?????????????????????
        setTransparency();
        mTvMessagePress.getBackground().setAlpha(255);
        mTvMessageTextPress.setTextColor(Color.argb(255, 69, 192, 26));

        //??????ViewPager?????????????????????
        mVpContent.setOffscreenPageLimit(3);

    }

    @Override
    public void initData() {
        //??????4????????????Fragment
        mFragments = new ArrayList<>();
        mMessageFragment = new MessageFragment();
        mContactsFragment = new ContactsFragment();
        mDiscoveryFragment = new DiscoveryFragment();
        mMeFragment = new MeFragment();
        mFragments.add(mMessageFragment);
        mFragments.add(mContactsFragment);
        mFragments.add(mDiscoveryFragment);
        mFragments.add(mMeFragment);

        //??????????????????vp?????????
        mVpContent.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments));
        mVpContent.setCurrentItem(0);

        //???????????????????????????????????????????????????
        updateContactCount();
    }

    @Override
    public void initListener() {
        //??????vp????????????????????????????????????????????????
        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //??????ViewPager???????????????????????????
                int diaphaneity_one = (int) (255 * positionOffset);
                int diaphaneity_two = (int) (255 * (1 - positionOffset));
                switch (position) {
                    case 0:
                        mTvMessageNormal.getBackground().setAlpha(diaphaneity_one);
                        mTvMessagePress.getBackground().setAlpha(diaphaneity_two);
                        mTvContactsNormal.getBackground().setAlpha(diaphaneity_two);
                        mTvContactsPress.getBackground().setAlpha(diaphaneity_one);
                        mTvMessageTextNormal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                        mTvMessageTextPress.setTextColor(Color.argb(diaphaneity_two, 69, 192, 26));
                        mTvContactsTextNormal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
                        mTvContactsTextPress.setTextColor(Color.argb(diaphaneity_one, 69, 192, 26));
                        break;
                    case 1:
                        mTvContactsNormal.getBackground().setAlpha(diaphaneity_one);
                        mTvContactsPress.getBackground().setAlpha(diaphaneity_two);
                        mTvDiscoveryNormal.getBackground().setAlpha(diaphaneity_two);
                        mTvDiscoveryPress.getBackground().setAlpha(diaphaneity_one);
                        mTvContactsTextNormal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                        mTvContactsTextPress.setTextColor(Color.argb(diaphaneity_two, 69, 192, 26));
                        mTvDiscoveryTextNormal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
                        mTvDiscoveryTextPress.setTextColor(Color.argb(diaphaneity_one, 69, 192, 26));
                        break;
                    case 2:
                        mTvDiscoveryNormal.getBackground().setAlpha(diaphaneity_one);
                        mTvDiscoveryPress.getBackground().setAlpha(diaphaneity_two);
                        mTvMeNormal.getBackground().setAlpha(diaphaneity_two);
                        mTvMePress.getBackground().setAlpha(diaphaneity_one);
                        mTvDiscoveryTextNormal.setTextColor(Color.argb(diaphaneity_one, 153, 153, 153));
                        mTvDiscoveryTextPress.setTextColor(Color.argb(diaphaneity_two, 69, 192, 26));
                        mTvMeTextNormal.setTextColor(Color.argb(diaphaneity_two, 153, 153, 153));
                        mTvMeTextPress.setTextColor(Color.argb(diaphaneity_one, 69, 192, 26));
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {
                //???????????????????????????????????????????????????????????????
                if (position == 1) {
                    mContactsFragment.showQuickIndexBar(true);
                } else {
                    mContactsFragment.showQuickIndexBar(false);
                }

                //??????position????????????Fragment?????????
                mFragments.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    //????????????????????????????????????
                    mContactsFragment.showQuickIndexBar(false);
                } else {
                    mContactsFragment.showQuickIndexBar(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSearch:
                Intent intent = new Intent(this, SearchUserActivity.class);
                intent.putExtra(SearchUserActivity.SEARCH_TYPE, SearchUserActivity.SEARCH_USER_REMOTE);
                startActivity(intent);
                break;
            case R.id.itemMore:
                showMenu();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CLEAR_UNREAD) {
            updateContactCount();
        }
    }

    @Override
    protected void onDestroy() {
        unRegisterBroadcastReceiver();
        super.onDestroy();
        NimAccountSDK.onlineStatusListen(
                mOnlineStatusObserver, false);
        NimUserInfoSDK.observeUserInfoUpdate(userInfoobserver,false);
        NimFriendSDK.observeFriendChangedNotify(changedNotifyObserver,false);
        NimTeamSDK.observeTeamRemove(teamobserver,false);
        NimSystemSDK.observeReceiveSystemMsg(systemMessageObserver,false);

        method("mServedView");
        method("mNextServedView");
    }

    /**
     * ?????????????????????????????????
     */
    public void method(String attr){
        InputMethodManager im=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            Field mCurRootViewField = InputMethodManager.class.getDeclaredField(attr);
            mCurRootViewField.setAccessible(true);
            //?????????
            Object mCurRootView=mCurRootViewField.get(im);

            if (null!=mCurRootView) {
                Context context=((View)mCurRootView).getContext();
                if(context==this){
                    //??????GC???
                    mCurRootViewField.set(im,null);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }







    private void initToolbar() {
        //??????ToolBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("??????");
        mToolbar.setTitleTextColor(UIUtils.getColor(R.color.white));
    }

    private void showMenu() {
        View menuView = View.inflate(this, R.layout.popup_menu_main, null);
        //????????????
        menuView.findViewById(R.id.itemCreateGroupCheat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TeamCheatCreateActvitiy.class));
                mPopupWindow.dismiss();
            }
        });
        //????????????
        menuView.findViewById(R.id.itemAddFriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, NewFriendActivity.class), MainActivity.REQ_CLEAR_UNREAD);
                mPopupWindow.dismiss();
            }
        });
        //?????????
        menuView.findViewById(R.id.itemScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                mPopupWindow.dismiss();
            }
        });
        //???????????????
        menuView.findViewById(R.id.itemHelpAndFeedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", AppConst.Url.HELP_FEEDBACK);
                startActivity(intent);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = PopupWindowFactory.getPopupWindowAtLocation(menuView, mVpContent, Gravity.RIGHT | Gravity.TOP, UIUtils.dip2Px(12), mToolbar.getHeight() + getStatusBarHeight());
    }

    /**
     * ???press???????????????????????????(???????????????)
     */
    private void setTransparency() {
        mTvMessageNormal.getBackground().setAlpha(255);
        mTvContactsNormal.getBackground().setAlpha(255);
        mTvDiscoveryNormal.getBackground().setAlpha(255);
        mTvMeNormal.getBackground().setAlpha(255);
        mTvMessagePress.getBackground().setAlpha(1);
        mTvContactsPress.getBackground().setAlpha(1);
        mTvDiscoveryPress.getBackground().setAlpha(1);
        mTvMePress.getBackground().setAlpha(1);
        mTvMessageTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
        mTvContactsTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
        mTvDiscoveryTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
        mTvMeTextNormal.setTextColor(Color.argb(255, 153, 153, 153));
        mTvMessageTextPress.setTextColor(Color.argb(0, 69, 192, 26));
        mTvContactsTextPress.setTextColor(Color.argb(0, 69, 192, 26));
        mTvDiscoveryTextPress.setTextColor(Color.argb(0, 69, 192, 26));
        mTvMeTextPress.setTextColor(Color.argb(0, 69, 192, 26));
    }

    /**
     * ???????????????????????????????????????????????????
     */
    public void updateContactCount() {
        //??????????????????????????? ???????????????
        List<SystemMessageType> types = new ArrayList<>();
        types.add(SystemMessageType.AddFriend);
        types.add(SystemMessageType.TeamInvite);
        int unreadCount = NimSystemSDK.querySystemMessageUnreadCountByType(types);
        if (unreadCount > 0) {
            mTvContactCount.setVisibility(View.VISIBLE);
            mTvContactCount.setText(String.valueOf(unreadCount));
            return;
        } else {
            mTvContactCount.setVisibility(View.GONE);
        }
    }

    /**
     * ??????????????????????????????2????????????2???????????????
     */
//    @Override
//    public void onBackPressed() {
//        if (exit++ == 1) {
//            App.exit();
//        } else {
//            UIUtils.showToast("??????????????????");
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    exit = 0;
//                }
//            }, 2000);
//        }
//    }

    /**
     * ?????????????????????
     */
    private void registerBroadcastReceiver() {
        //???????????????????????????
        mAuthBroadcastReceiver = new AuthBroadcastReceiver();
        registerReceiver(mAuthBroadcastReceiver, new IntentFilter(AuthBroadcastReceiver.ACTION));
    }

    /**
     * ????????????????????????
     */
    private void unRegisterBroadcastReceiver() {
        if (mAuthBroadcastReceiver != null) {
            unregisterReceiver(mAuthBroadcastReceiver);
            mAuthBroadcastReceiver = null;
        }
    }

    /**
     * ??????????????????
     */
    private void observerLineStatus() {
        mOnlineStatusObserver = new Observer<StatusCode>() {

            public void onEvent(StatusCode status) {
                LogUtils.sf("User status changed to: " + status);
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (status.wontAutoLogin()) {
                    //???????????????
                    Intent intent = new Intent();
                    intent.setAction(AuthBroadcastReceiver.ACTION);
                    intent.putExtra("status", status.getValue());
                    sendBroadcast(intent);
                }
            }
        };
        NimAccountSDK.onlineStatusListen(
                mOnlineStatusObserver, true);
    }
    Observer<List<NimUserInfo>> userInfoobserver;
    /**
     * ????????????????????????
     */
    private void observeUserInfoUpdate() {
        userInfoobserver = new Observer<List<NimUserInfo>>() {
            @Override
            public void onEvent(List<NimUserInfo> nimUserInfos) {
                mMeFragment.initData();
            }
        };
        NimUserInfoSDK.observeUserInfoUpdate(userInfoobserver, true);
    }

    Observer<FriendChangedNotify> changedNotifyObserver;
    /**
     * ???????????????????????????
     */
    private void observeFriendChangedNotify() {
        changedNotifyObserver = new Observer<FriendChangedNotify>() {
            @Override
            public void onEvent(FriendChangedNotify friendChangedNotify) {
//                List<Friend> addedOrUpdatedFriends = friendChangedNotify.getAddedOrUpdatedFriends(); // ???????????????
//                List<String> deletedFriendAccounts = friendChangedNotify.getDeletedFriends(); // ?????????????????????????????????

                //?????????????????????
                mContactsFragment.initData();
            }
        };
        NimFriendSDK.observeFriendChangedNotify(changedNotifyObserver, true);
    }
    Observer<Team> teamobserver;
    /**
     * ???????????????????????????
     */
    private void observeTeamChangedNotify() {
        teamobserver = new Observer<Team>() {
            @Override
            public void onEvent(Team team) {
                mMessageFragment.initData();
            }
        };
        NimTeamSDK.observeTeamRemove(teamobserver, true);
//        NimTeamSDK.observeTeamUpdate(new Observer<List<Team>>() {
//            @Override
//            public void onEvent(List<Team> teams) {
//                mMessageFragment.initData();
//            }
//        }, true);
    }

    Observer<SystemMessage> systemMessageObserver;
    /**
     * ????????????????????????
     */
    private void observeReceiveSystemMsg() {
        systemMessageObserver = new Observer<SystemMessage>() {
            @Override
            public void onEvent(final SystemMessage systemMessage) {

                items.clear();
                List<SystemMessageType> types = new ArrayList<>();
                types.add(SystemMessageType.AddFriend);
                types.add(SystemMessageType.TeamInvite);
                InvocationFuture<List<SystemMessage>> listInvocationFuture = NimSystemSDK.querySystemMessageByType(types, 0, 100);
                listInvocationFuture.setCallback(new RequestCallback<List<SystemMessage>>() {
                    @Override
                    public void onSuccess(List<SystemMessage> param) {
                        if (!StringUtils.isEmpty(param)) {
                            items.addAll(param);

                            //TODO:????????????????????????????????????????????????
                            SystemMessage del = null;
                            for (SystemMessage m : items) {
                                if (m.getMessageId() != systemMessage.getMessageId() &&
                                        m.getFromAccount().equals(systemMessage.getFromAccount()) && m.getType() == SystemMessageType.AddFriend) {
                                    del = m;
                                    break;
                                }
                            }
                            if (del != null) {
                                items.remove(del);
                                //???????????????????????????????????????
                                NimSystemSDK.deleteSystemMessage(del);
                            }

                            //?????????????????????????????????
                            updateContactCount();
                            mContactsFragment.updateHeaderViewUnreadCount();


                            //????????????????????????????????????
                            if (systemMessage.getType() == SystemMessageType.AddFriend) {
                                NimUserInfoSDK.getUserInfoFromServer(systemMessage.getFromAccount(), null);
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                    }

                    @Override
                    public void onException(Throwable exception) {
                    }
                });
            }
        };
        NimSystemSDK.observeReceiveSystemMsg(systemMessageObserver, true);
    }
}
