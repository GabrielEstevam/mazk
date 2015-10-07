package br.ufsc.labtec.mazk.activities.fragments.callbacks;

import br.ufsc.labtec.mazk.beans.Usuario;

/**
 * Created by Mihael Zamin on 15/04/2015.
 * Callback para obter o usuário logado.
 */
public interface CurrentUserCallback {
    public Usuario getCurrentUser();
}
