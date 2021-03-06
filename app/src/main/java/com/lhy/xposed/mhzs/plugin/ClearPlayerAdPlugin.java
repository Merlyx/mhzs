package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.Constant;
import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清理播放界面广告
 * 包括：“推荐”，“电影”，“电视剧”
 *
 * @author lhy
 * @time 2019年2月16日15:12:33
 */
public class ClearPlayerAdPlugin implements IPlugin {

    @Override
    public void run(ClassLoader classLoader) throws Throwable {
        /**
         * 在com.mh.movie.core.mvp.presenter.player.PlayerPresenter中
         * 替换g()方法，去除播放页面的广告 -BootadsListBean-
         *
         * 此变量名版本更新可能会改变
         */
        XposedHelpers.findAndHookMethod(Constant.prst.$PlayerPresenter, classLoader, "g", int.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                LogUtil.e("hook g method!");
                return null;
            }
        });
    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("ad_player", true);
    }
}
