package com.hl.base_module.util.edittext;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

/**
 * TextInputEditText watcher，处理TextInputLayout错误恢复
 */
public class TextInputEditTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;

    public TextInputEditTextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (null != textInputLayout)
            textInputLayout.setErrorEnabled(false);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
