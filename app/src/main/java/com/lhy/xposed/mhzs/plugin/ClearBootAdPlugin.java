package com.lhy.xposed.mhzs.plugin;

import com.lhy.xposed.mhzs.helper.LogUtil;
import com.lhy.xposed.mhzs.helper.XPrefUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 清理启动页的广告，5s启动
 *
 * @author lhy
 * @time 2019年2月16日13:12:33
 */
public class ClearBootAdPlugin implements IPlugin {

    private final String constantsClassName = "com.mh.movie.core.mvp.ui.b";
    private final String loginResponseClassName = "com.mh.movie.core.mvp.model.entity.response.LoginResponse";

    @Override
    public void run(final ClassLoader classLoader) throws Throwable {
        /**
         * 禁止加载广告历史记录
         *
         * 此变量名版本更新可能会改变
         */
        XposedHelpers.setStaticObjectField(classLoader.loadClass(constantsClassName), "w", "splash_no_ad_list");
        /**
         * 将AdList设置为空
         */
        // TODO: 2019/2/22 0022 Exposed/VXP ISSUE--TOO SHORT METHOD!!!
        XposedHelpers.findAndHookMethod(loginResponseClassName, classLoader, "getAdsList", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                LogUtil.e("hook getAdsList null!");
                param.setResult(null);
            }
        });

    }

    @Override
    public boolean isOpen() {
        return XPrefUtils.getPref().getBoolean("ad_boot", true);
    }
}
