package Dados;

//Se erro acontece quando as credenciais de login (nome, senha, etc.) estão erradas.

public class CredenciaisException extends Exception {
    public CredenciaisException(String mensagem) {
        super(mensagem);
    }
}