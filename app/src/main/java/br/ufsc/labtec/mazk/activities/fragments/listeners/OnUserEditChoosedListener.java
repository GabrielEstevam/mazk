package br.ufsc.labtec.mazk.activities.fragments.listeners;

import br.ufsc.labtec.mazk.beans.Usuario;

/**
 * Created by Mihael Zamin on 25/09/2015.
 */
public interface OnUserEditChoosedListener {
    void userEditChoosed(Usuario usuario);

    Usuario getUserToEdit();

    void userEditFinished();
}
