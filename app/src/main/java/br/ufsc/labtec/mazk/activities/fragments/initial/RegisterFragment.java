package br.ufsc.labtec.mazk.activities.fragments.initial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.activities.fragments.listeners.OnUserIdentifiedListener;
import br.ufsc.labtec.mazk.beans.Tipo;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.PublicResource;
import br.ufsc.labtec.mazk.services.util.PublicService;
import br.ufsc.labtec.mazk.view.custom.DateDisplayPicker;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterFragment extends Fragment implements Callback<Usuario> {
    // TODO: Rename and change types of parameters
    private AutoCompleteTextView txtEmail;
    private EditText txtSenha[];
    private Button btnRegistrar;
    private EditText txtNome;
    private View registerForm;
    private View progress;
    private PublicResource pr;
    private DateDisplayPicker dataDeNascimento;

    private OnUserIdentifiedListener mListener;
    private boolean emailValid, senhaValid, senhaMatch;


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        emailValid = senhaValid = senhaMatch = false;
        View v =  inflater.inflate(R.layout.fragment_register, container, false);
        pr = new PublicService().createService(getString(R.string.server_url));
        dataDeNascimento = (DateDisplayPicker)v.findViewById(R.id.dataDeNascimento);
        txtSenha = new EditText[2];
        txtSenha[0] = (EditText)v.findViewById(R.id.password);
        txtSenha[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus)
                {
                    if(txtSenha[0].getText().toString().length() < 4) {
                        txtSenha[0].setError("Senha muito curta");
                        senhaValid = false;
                    } else senhaValid = true;
                }
            }
        });
        txtSenha[1]=(EditText)v.findViewById(R.id.confirmPassword);
        txtSenha[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.equals(s.toString(), txtSenha[0].getText().toString()))
                {
                    senhaMatch = false;
                    txtSenha[1].setError("As senhas não batem.");
                } else senhaMatch = true;

            }
        });

        txtEmail = (AutoCompleteTextView) v.findViewById(R.id.email);
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if(s.toString().contains("@")) {
                        pr.isExistentEmail(s.toString(), new Callback<Boolean>() {
                            @Override
                            public void success(Boolean aBoolean, Response response) {
                                if (aBoolean) {
                                    txtEmail.setError("Email já cadastrado");
                                    emailValid = false;
                                } else emailValid = true;
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    } else
                    {
                        emailValid = false;
                        txtEmail.setError("Email inválido");
                    }

                } else{
                    emailValid = false;
                    txtEmail.setError("Campo não preenchido");
                }

            }
        });/*
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (!TextUtils.isEmpty(txtEmail.getText().toString())) {
                        if(txtEmail.getText().toString().contains("@")) {
                            pr.isExistentEmail(txtEmail.getText().toString(), new Callback<Boolean>() {
                                @Override
                                public void success(Boolean aBoolean, Response response) {
                                    if (aBoolean) {
                                        txtEmail.setError("Email já cadastrado");
                                        emailValid = false;
                                    } else emailValid = true;
                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            });
                        } else
                        {
                            emailValid = false;
                            txtEmail.setError("Email inválido");
                        }

                    } else{
                        emailValid = false;
                        txtEmail.setError("Campo não preenchido");
                    }
                }
            }
        });*/
        txtNome = (EditText)v.findViewById(R.id.name);

        btnRegistrar = (Button)v.findViewById(R.id.registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
        registerForm = v.findViewById(R.id.registerForm);
        progress = v.findViewById(R.id.progressBar);
       return v;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnUserIdentifiedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnUserIdentifiedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void success(Usuario usuario, Response response) {
        showProgress(false);
        Toast.makeText(this.getActivity(), "Registro concluído com sucesso", Toast.LENGTH_LONG).show();
        mListener.userIdentified(usuario);
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
    }
    private void attemptRegister()
    {
        if(validateFields()) {
            showProgress(true);
            String nome = txtNome.getText().toString();
            String email = txtEmail.getText().toString();
            String senha = txtSenha[0].getText().toString();
            String confirmaSenha = txtSenha[1].getText().toString();
            Usuario u = new Usuario(nome, email, senha, dataDeNascimento.getDate());

            pr.cadastro(u, this);

        }
    }
    private boolean validateFields()
    {

        String nome = txtNome.getText().toString();
        String senha = txtSenha[0].getText().toString();
        String confirmaSenha = txtSenha[1].getText().toString();

        boolean cancel = false;
        if(TextUtils.isEmpty(nome) )
        {
            txtNome.setError("Preencha o campo nome");
            cancel = true;
        }
        if(TextUtils.isEmpty(senha)|| TextUtils.isEmpty(confirmaSenha) || !senhaValid)
        {
            txtSenha[0].setError("Digite uma senha válida com mais de 4 caracteres");
            cancel = true;
        }else if(!TextUtils.equals(senha, confirmaSenha) || !senhaMatch)
        {
            cancel = true;
            txtSenha[1].setError("A senha não confere");
        }
        if(TextUtils.isEmpty(dataDeNascimento.getText().toString()))
        {
            dataDeNascimento.setError("Entre com sua data de nascimento");
            cancel = true;
        }
        if(!emailValid)
        {
            cancel = true;
        }

        return !cancel;

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerForm.setVisibility(show ? View.GONE : View.VISIBLE);
            registerForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            registerForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
