package com.east.connotationjokes.activity;

import com.east.baselibrary.headerbar.CommonHeaderBar;
import com.east.baselibrary.ioc.annotation.BindClick;
import com.east.connotationjokes.FragmentManagerHelper;
import com.east.connotationjokes.R;
import com.east.connotationjokes.fragment.FindFragment;
import com.east.connotationjokes.fragment.HomeFragment;
import com.east.connotationjokes.fragment.MessageFragment;
import com.east.connotationjokes.fragment.NewFragment;
import com.east.framelibrary.BaseSkinActivity;

public class HomeActivity extends BaseSkinActivity {

    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;

    private FragmentManagerHelper mFragmentHelper;

    @Override
    protected void initTitle() {
        new CommonHeaderBar.Builder(this)
                .bindLayoutId(R.layout.title_bar)
                .setText(R.id.title,"首页")
                .setOnClickListener(R.id.back, v -> finish())
                .create();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        mFragmentHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.main_tab_fl);
        mHomeFragment = new HomeFragment();
        mFragmentHelper.add(mHomeFragment);
    }

    @Override
    protected void initView() {

    }

    @BindClick(R.id.home_rb)
    private void homeRbClick() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        mFragmentHelper.switchFragment(mHomeFragment);
    }

    @BindClick(R.id.find_rb)
    private void findRbClick() {
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
        }
        mFragmentHelper.switchFragment(mFindFragment);
    }

    @BindClick(R.id.new_rb)
    private void newRbClick() {
        if (mNewFragment == null) {
            mNewFragment = new NewFragment();
        }
        mFragmentHelper.switchFragment(mNewFragment);
    }

    @BindClick(R.id.message_rb)
    private void messageRbClick() {
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        mFragmentHelper.switchFragment(mMessageFragment);
    }
}
