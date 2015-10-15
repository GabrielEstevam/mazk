package br.ufsc.labtec.mazk.activities.fragments.initial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnRegisterRequestedListener;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserIdentifiedListener;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.PublicResource;
import br.ufsc.labtec.mazk.services.util.PublicService;
import br.ufsc.labtec.mazk.services.util.UsuarioService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.beardedhen.androidbootstrap.*;
/**
 * A login screen that offers login via email/password.
 */
public class LoginFragment extends Fragment implements Callback<Usuario> {


    // UI references.
    private BootstrapEditText mEmailView;
    private BootstrapEditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private PublicResource pr;
    private UsuarioService us;
    private TextView txtRegister;
    private OnUserIdentifiedListener mListener;
    private OnRegisterRequestedListener rrListener;


    private String serverUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity faActivity = super.getActivity();

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        serverUrl = getString(R.string.server_url);
        txtRegister = (TextView) v.findViewById(R.id.registrar);
        // Set up the login form.
        mEmailView = (BootstrapEditText) v.findViewById(R.id.email);


        pr = new PublicService().createService(serverUrl);
        us = new UsuarioService();
        mPasswordView = (BootstrapEditText) v.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        txtRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rrListener != null)
                    rrListener.registerRequested();
            }
        });

        BootstrapButton mEmailSignInButton = (BootstrapButton) v.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnUserIdentifiedListener) activity;
            rrListener = (OnRegisterRequestedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            us.createService(serverUrl, email, password).login(this);

        }
    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void success(Usuario usuario, Response response) {
        showProgress(false);
        Log.i("Login", (usuario.getTipo() != null) ? "Possui tipo " + usuario.getTipo().getNome() : "Não possui tipo");
        mListener.userIdentified(usuario);
    }

    @Override
    public void failure(RetrofitError error) {
        showProgress(false);
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
        Toast.makeText(this.getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        Log.e("RETROFIT", error.getMessage());
    }

}



