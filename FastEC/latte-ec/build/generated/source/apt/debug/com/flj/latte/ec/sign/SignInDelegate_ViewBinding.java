// Generated code from Butter Knife. Do not modify!
package com.flj.latte.ec.sign;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.diabin.latte.ec.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignInDelegate_ViewBinding implements Unbinder {
  private SignInDelegate target;

  private View view2131492906;

  private View view2131492983;

  private View view2131493169;

  @UiThread
  public SignInDelegate_ViewBinding(final SignInDelegate target, View source) {
    this.target = target;

    View view;
    target.mEmail = Utils.findRequiredViewAsType(source, R.id.edit_sign_in_email, "field 'mEmail'", TextInputEditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.edit_sign_in_password, "field 'mPassword'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_sign_in, "method 'onClickSignIn'");
    view2131492906 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickSignIn();
      }
    });
    view = Utils.findRequiredView(source, R.id.icon_sign_in_wechat, "method 'onClickWeChat'");
    view2131492983 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickWeChat();
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_link_sign_up, "method 'onClickLink'");
    view2131493169 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickLink();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SignInDelegate target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEmail = null;
    target.mPassword = null;

    view2131492906.setOnClickListener(null);
    view2131492906 = null;
    view2131492983.setOnClickListener(null);
    view2131492983 = null;
    view2131493169.setOnClickListener(null);
    view2131493169 = null;
  }
}
