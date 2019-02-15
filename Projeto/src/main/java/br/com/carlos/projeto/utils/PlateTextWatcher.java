package br.com.carlos.projeto.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import br.com.carlos.projeto.R;

public class PlateTextWatcher implements TextWatcher {

    private Integer oldLength;
    private EditText editText;
    private Activity activity;
    private Boolean newFormat;
    private Drawable btnClear;

    public PlateTextWatcher(EditText editText, Activity activity, Boolean newFormat){
        this.editText = editText;
        this.activity = activity;
        this.newFormat = newFormat;
        this.btnClear = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_clear, null);

        //Clear text
        initClear(editText);

        //All caps
        allCaps();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        oldLength = s.length();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(newFormat){
            newFormat(s);
        } else {
            format(s);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void format(Editable s){
        if(s.length() == 0 || oldLength == 0) {
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 3 && oldLength < s.length()) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 2 && oldLength > s.length()) {
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 7) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
            hideKeyboard();

        }
    }

    private void newFormat(Editable s){
        if(s.length() == 0 || oldLength == 0) {
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 3 && oldLength < s.length()) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 2 && oldLength > s.length()) {
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 4 && oldLength < s.length()) {
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 3 && oldLength > s.length()) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 5 && oldLength < s.length()) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
        } else if(s.length() == 4 && oldLength > s.length()) {
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            editText.setSelection(editText.getText().length());
        }else if(s.length() == 7) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
            hideKeyboard();

        }
    }

    private void initClear(final EditText editText) {

        btnClear.setBounds(0, 0, btnClear.getIntrinsicWidth(), btnClear.getIntrinsicHeight());

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > editText.getWidth() - editText.getPaddingRight() - btnClear.getIntrinsicWidth()) {
                    editText.setText("");
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
                return false;
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!"".equals(editText.getText().toString())){
                        editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], btnClear, editText.getCompoundDrawables()[3]);
                    }
                }else {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().equals("")) {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
                else {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], btnClear, editText.getCompoundDrawables()[3]);
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    private void allCaps(){
        //All text caps lock
        InputFilter[] editFilters = editText.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        editText.setFilters(newFilters);
    }

}
