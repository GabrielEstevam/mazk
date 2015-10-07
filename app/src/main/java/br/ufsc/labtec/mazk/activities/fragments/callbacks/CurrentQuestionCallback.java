package br.ufsc.labtec.mazk.activities.fragments.callbacks;

import br.ufsc.labtec.mazk.beans.Pergunta;

/**
 * Created by Mihael Zamin on 15/04/2015.
 * Callback para obter a pergunta que está em foco.
 */
public interface CurrentQuestionCallback {
    public Pergunta getCurrentQuestion();
}
