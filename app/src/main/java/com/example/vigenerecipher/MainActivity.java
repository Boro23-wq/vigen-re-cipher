package com.example.vigenerecipher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public TextView YourTextView, KeyPhraseView, ResultView, ResultMsgView, CreditView, ResultMsgContent;
    public EditText YourTextContent, KeyPhraseContent;
    public RadioGroup radioGroup;
    public RadioButton EncryptionBtn, DecryptionBtn;
    public Button Run;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        YourTextView = findViewById(R.id.textHereView);
        KeyPhraseView = findViewById(R.id.keyPhraseView);
        ResultView = findViewById(R.id.resultView);
        ResultMsgView = findViewById(R.id.resultMsgView);
        CreditView = findViewById(R.id.creditAuthor);
        YourTextContent = findViewById(R.id.textContent);
        KeyPhraseContent = findViewById(R.id.keyPhraseContent);
        ResultMsgContent = findViewById(R.id.resultMsgContent);
        radioGroup = findViewById(R.id.radioGroup);
        EncryptionBtn = findViewById(R.id.encryptBtn);
        DecryptionBtn = findViewById(R.id.decryptBtn);
        Run = findViewById(R.id.runBtn);
    }

    public void encryptOrDecryptUsingKeyphraseOnClick(View view) {

        // Local variables.
        String shiftedString;

        if (view.getId() == R.id.runBtn) {

            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            shiftedString = null;
            String textFromCipher = this.YourTextContent.getText().toString();
            String keyphraseFromCipher = this.KeyPhraseContent.getText().toString();

            if (!checkForEmptyInvalidInput(textFromCipher, keyphraseFromCipher)) {
                // Input parameters are all correct.
                if (EncryptionBtn.isChecked()) {
                    // Encrypt option.
                    shiftedString = this.encryptAlgorithm(textFromCipher, keyphraseFromCipher);
                }
                if (DecryptionBtn.isChecked()) {
                    // Decrypt option.
                    shiftedString = this.decryptAlgorithm(textFromCipher, keyphraseFromCipher);
                }
                this.ResultMsgContent.setText(shiftedString.toString());
            }
        }
    } // end of encryptOrDecryptUsingKeyphrase method.


    private String encryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char)((upper + keyphrase.charAt(j) - 130) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char)((upper + keyphrase.charAt(j) - 130) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    } // end of encryptAlgorithm method.


    private String decryptAlgorithm(String text, String keyphrase) {

        keyphrase = keyphrase.toUpperCase();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0, j = 0; i < text.length(); i++) {

            char upper = text.toUpperCase().charAt(i);
            char orig = text.charAt(i);

            if (Character.isAlphabetic(orig)) {
                if (Character.isUpperCase(orig)) {
                    sb.append((char)((upper - keyphrase.charAt(j) + 26) % 26 + 65));
                    ++j;
                    j %= keyphrase.length();
                } else {
                    sb.append(Character.toLowerCase((char)((upper - keyphrase.charAt(j) + 26) % 26 + 65)));
                    ++j;
                    j %= keyphrase.length();
                }
            } else {
                sb.append(orig);
            }
        }
        return sb.toString();
    } // end of decryptAlgorithm method.



    private boolean checkForEmptyInvalidInput(String text, String keyphrase) {

        boolean isError = false;

        // Current text has no alphabetical characters. Text must at least one alphabetical character.
        if (!text.matches(".*[a-zA-Z]+.*")) {
            this.YourTextContent.setError("Nothing to encode/decode!");
            isError = true;
        }

        // Current keyphrase is either null or empty.
        if (null == keyphrase || keyphrase.isEmpty()) {
            this.KeyPhraseContent.setError("Keyphrase Required!");
            isError = true;
        }

        // Non-alphabetical character(s) in keyphrase. Keyphrase must only contain alphabetical characters.
        boolean valid = this.checkIfKeyphraseValid(keyphrase);
        if (!valid) {
            // Non-alphabetical character(s) in keyphrase.
            this.KeyPhraseContent.setError("Non-alphabetical character(s) in keyphrase!");
            isError = true;
        }
        return isError;
    } // end of checkForEmptyInvalidInput.


    private boolean checkIfKeyphraseValid(String keyphrase) {

        boolean valid = true;

        for(int z = 0; z < keyphrase.length(); ++z) {
            char c = keyphrase.charAt(z);
            if (!Character.isAlphabetic(c)) {
                valid = false;
                break;
            }
        }
        return valid;
    } // end of checkIfKeyphraseValid.
}

